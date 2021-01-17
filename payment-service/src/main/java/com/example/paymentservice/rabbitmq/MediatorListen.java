package com.example.paymentservice.rabbitmq;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.paymentservice.dto.BalanceUpdateRequest;
import com.example.paymentservice.service.PaymentService;
import com.example.paymentservice.service.models.Payment;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;

@Component
public class MediatorListen {
	private final static String ADDING = "payment_adding";
	private final static String DELETING = "payment_deleting";
	private final static String UPDATING = "payment_updating";

	private final PaymentService paymentService;

	@Autowired
	public MediatorListen(PaymentService paymentService) {
		this.paymentService = paymentService;
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
			Payment payment = new ObjectMapper().readValue(message, Payment.class);

			System.out.println(" [x] Received message to create '" + payment + "'");
			paymentService.createPayment(payment);
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
			System.out.println(" [x] Received message to delete payment with id '" + id + "'");
			paymentService.deletePaymentById(id);
		};
		channel.basicConsume(DELETING, true, deliverCallback, consumerTag -> {
		});
	}

	@PostConstruct
	public void consumeUpdateMessageFromQueue() throws Exception {
		ConnectionFactory factory = new ConnectionFactory();
		factory.setHost("localhost");
		Connection connection = factory.newConnection();

		Channel channel = connection.createChannel();

		channel.queueDeclare(UPDATING, true, false, false, null);
		System.out.println(" [*] Waiting for messages...");

		DeliverCallback deliverCallback = (consumerTag, delivery) -> {
			String message = new String(delivery.getBody(), "UTF-8");
			BalanceUpdateRequest balanceUpdateRequest = new ObjectMapper().readValue(message,
					BalanceUpdateRequest.class);
			System.out.println(" [x] Received message to '" + balanceUpdateRequest + "'");
			paymentService.replenishBalance(balanceUpdateRequest.getPaymentId(), balanceUpdateRequest.getPrice());
		};
		channel.basicConsume(UPDATING, true, deliverCallback, consumerTag -> {
		});
	}

}
