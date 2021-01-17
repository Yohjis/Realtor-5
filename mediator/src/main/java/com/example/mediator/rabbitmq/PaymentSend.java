package com.example.mediator.rabbitmq;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import com.example.mediator.models.payment.Payment;

@RestController
@RequestMapping(value = "/rabbitmq_payments")
public class PaymentSend {
    private final static String ADDING = "payment_adding";
    private final static String DELETING = "payment_deleting";

    @PostMapping
    public ResponseEntity<String> create(@RequestParam int desk, @RequestParam int estateBalance) throws Exception {

        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        try (Connection connection = factory.newConnection()) {
            Channel channel = connection.createChannel();
            channel.queueDeclare(ADDING, true, false, false, null);
            Payment payment = new Payment(desk, estateBalance);
            System.out.println(payment);
            channel.basicPublish("", ADDING, null, new ObjectMapper().writeValueAsBytes(payment));
            System.out.println(" [x] Sent " + payment);
        }
        return ResponseEntity.ok().build();
    }

    @DeleteMapping
    public ResponseEntity<String> delete(@RequestParam String id) throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        try (Connection connection = factory.newConnection()) {
            Channel channel = connection.createChannel();
            channel.queueDeclare(DELETING, true, false, false, null);
            channel.basicPublish("", DELETING, null, id.getBytes());
            System.out.println(" [x] Sent 'delete payment with id " + id);
        }
        return ResponseEntity.ok().build();
    }
}
