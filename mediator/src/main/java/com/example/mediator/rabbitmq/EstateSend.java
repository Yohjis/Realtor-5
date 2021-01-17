package com.example.mediator.rabbitmq;

import com.example.mediator.dto.EstateSellRequest;
import com.example.mediator.models.estate.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

@RestController
@RequestMapping(value = "/rabbitmq_estates")
public class EstateSend {
    private final static String ADDING = "estate_adding";
    private final static String DELETING = "estate_deleting";

    @PostMapping
    public ResponseEntity<String> create(@RequestParam EstateDistrict district, @RequestParam EstateAddress e_address,
                                         @RequestParam EstateBuilding building, @RequestParam EstateRooms rooms, @RequestParam int price) throws Exception {

        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        try (Connection connection = factory.newConnection()) {
            Channel channel = connection.createChannel();
            channel.queueDeclare(ADDING, true, false, false, null);
            Estate estate = new Estate(district, e_address, building, rooms, price);
            channel.basicPublish("", ADDING, null, new ObjectMapper().writeValueAsBytes(estate));
            System.out.println(" [x] Sent 'create " + estate + "'");
        }
        return ResponseEntity.ok().build();
    }

    @DeleteMapping
    public ResponseEntity<String> delete(@RequestBody EstateSellRequest estateSellRequest) throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        try (Connection connection = factory.newConnection()) {
            Channel channel = connection.createChannel();
            channel.queueDeclare(DELETING, true, false, false, null);
            channel.basicPublish("", DELETING, null, new ObjectMapper().writeValueAsBytes(estateSellRequest));
            System.out.println(" [x] Sent 'sell estate " + estateSellRequest);
        }
        return ResponseEntity.ok().build();
    }
}
