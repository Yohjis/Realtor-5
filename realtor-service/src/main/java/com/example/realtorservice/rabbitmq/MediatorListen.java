package com.example.realtorservice.rabbitmq;

import javax.annotation.PostConstruct;

import com.example.realtorservice.services.RealtorService;
import com.example.realtorservice.services.models.Realtor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;

@Component
public class MediatorListen {
	private final static String ADDING = "realtor_adding";
	private final static String DELETING = "realtor_deleting";
	private final RealtorService realtorService;

	@Autowired
	public MediatorListen(RealtorService realtorService) {
		this.realtorService = realtorService;
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
			Realtor realtor = new ObjectMapper().readValue(message, Realtor.class);

			System.out.println(" [x] Received message to create'" + realtor + "'");
			realtorService.createRealtor(realtor);
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
			String id = new String(delivery.getBody(), "UTF-8");
			System.out.println(" [x] Received message to delete realtor with id '" + id + "'");
			realtorService.deleteRealtorById(id);
		};
		channel.basicConsume(DELETING, true, deliverCallback, consumerTag -> {
		});
	}

	// Creating consumer with @RabbitListener
	/*
	 * @RabbitListener(queues = ADDING) public void
	 * consumeCreateMessageFromQueue(String message) throws JsonMappingException,
	 * JsonProcessingException { Realtor realtor = new
	 * ObjectMapper().readValue(message, Realtor.class);
	 * System.out.println(" [x] Received create '" + realtor + "'");
	 * //realtorService.createRealtor(realtor); }
	 * 
	 * @RabbitListener(queues = DELETING) public void
	 * consumeDeleteMessageFromQueue(String message) throws JsonMappingException,
	 * JsonProcessingException { String id = message;
	 * System.out.println(" [x] Received request ' delete realtor with id " + id +
	 * "'"); //realtorService.deleteRealtorById(id); }
	 */

}
