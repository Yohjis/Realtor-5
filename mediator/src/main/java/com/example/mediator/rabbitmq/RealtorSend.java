package com.example.mediator.rabbitmq;

import com.example.mediator.models.realtor.Realtor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Connection;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rabbitmq.client.Channel;



@RestController
@RequestMapping(value = "/rabbitmq_realtors")
public class RealtorSend {
    private final static String ADDING = "realtor_adding";
    private final static String DELETING = "realtor_deleting";

    @PostMapping
    public ResponseEntity<String> create(@RequestParam String name, @RequestParam String surname) throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        // factory.setPort(10001);
        try (Connection connection = factory.newConnection()) {
            Channel channel = connection.createChannel();
            channel.queueDeclare(ADDING, true, false, false, null);
            Realtor realtor = new Realtor(name, surname);
            channel.basicPublish("", ADDING, null, new ObjectMapper().writeValueAsBytes(realtor));
            System.out.println(" [x] Sent " + realtor);
        }
        return ResponseEntity.ok().build();
    }

    @DeleteMapping
    public ResponseEntity<String> delete(@RequestParam String id) throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        // factory.setPort(10002);
        try (Connection connection = factory.newConnection()) {
            Channel channel = connection.createChannel();
            channel.queueDeclare(DELETING, true, false, false, null);
            channel.basicPublish("", DELETING, null, id.getBytes());
            System.out.println(" [x] Sent 'delete realtor with id " + id);
        }
        return ResponseEntity.ok().build();
    }
}
