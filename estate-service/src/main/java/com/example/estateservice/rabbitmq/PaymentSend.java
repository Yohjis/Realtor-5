package com.example.estateservice.rabbitmq;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import org.springframework.stereotype.Component;

import com.example.estateservice.dto.BalanceUpdateRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

@Component
public class PaymentSend {

    private final static String UPDATING = "payment_updating";

    public void updatePaymentBalance(String paymentId, int price) throws IOException, TimeoutException {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        try (Connection connection = factory.newConnection()) {
            Channel channel = connection.createChannel();
            channel.queueDeclare(UPDATING, true, false, false, null);
            BalanceUpdateRequest balanceUpdateRequest = new BalanceUpdateRequest(paymentId, price);
            channel.basicPublish("", UPDATING, null, new ObjectMapper().writeValueAsBytes(balanceUpdateRequest));
            System.out.println(" [x] Sent updating" + balanceUpdateRequest);
        }

    }

}
