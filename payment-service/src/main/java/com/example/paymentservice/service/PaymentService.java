package com.example.paymentservice.service;

import com.example.paymentservice.service.models.Payment;

import java.util.List;

public interface PaymentService {

    Payment createPayment(int desk, int hookahBalance);

    Payment getPaymentById(String id);

    Payment getPayment(Payment payment);

    int getHookahBalance(Payment payment);

    void replenishBalance(String id, int hookahBalance);

    void deletePaymentById(String id);

    List<Payment> getPayments();

    String paymentsReview();

    void createPayment(Payment payment);
}
