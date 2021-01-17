package com.example.estateservice.rabbitmq;

import java.util.concurrent.TimeoutException;

import javax.annotation.PostConstruct;

import com.example.estateservice.service.EstateService;
import com.example.estateservice.service.models.Estate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.estateservice.dto.EstateSellRequest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;

@Component
public class MediatorListen {
    private final static String ADDING = "estate_adding";
    private final static String DELETING = "estate_deleting";
    private final EstateService estateService;
    private final PaymentSend paymentSend;

    @Autowired
    public MediatorListen(EstateService estateService, PaymentSend paymentSend) {
        this.estateService = estateService;
        this.paymentSend = paymentSend;
    }

    @PostConstruct
    public void consumeCreateMessageFromQueue() throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

        channel.queueDeclare(ADDING, true, false, false, null);
        System.out.println(" [*] Waiting for messages...");

        DeliverCallback deliverCallback = (consumerTag, delivery) -> {
            String message = new String(delivery.getBody(), "UTF-8");
            Estate estate = new ObjectMapper().readValue(message, Estate.class);

            System.out.println(" [x] Received message to create '" + estate + "'");
            estateService.createEstate(estate);

        };
        channel.basicConsume(ADDING, true, deliverCallback, consumerTag -> {
        });
    }

    @PostConstruct
    public void consumeDeleteMessageFromQueue() throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        Connection connection = factory.newConnection();

        Channel channel = connection.createChannel();

        channel.queueDeclare(DELETING, true, false, false, null);
        System.out.println(" [*] Waiting for messages...");

        DeliverCallback deliverCallback = (consumerTag, delivery) -> {
            String message = new String(delivery.getBody(), "UTF-8");
            EstateSellRequest estateSellRequest = new ObjectMapper().readValue(message, EstateSellRequest.class);

            int estatePrice = estateService.getEstatePrice(estateSellRequest.getEstateId());
            estateService.sellEstate(estateSellRequest.getEstateId());

            try {
                paymentSend.updatePaymentBalance(estateSellRequest.getPaymentId(), estatePrice);
            } catch (TimeoutException e) {
                e.printStackTrace();
            }

            System.out
                    .println(" [x] Received message to sell estate with id '" + estateSellRequest.getEstateId() + "'");
        };
        channel.basicConsume(DELETING, true, deliverCallback, consumerTag -> {
        });
    }
}