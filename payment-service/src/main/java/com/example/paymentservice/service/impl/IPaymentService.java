package com.example.paymentservice.service.impl;

import java.util.List;

import com.example.paymentservice.service.PaymentService;
import com.example.paymentservice.service.models.Payment;
import com.example.paymentservice.service.repos.PaymentRepo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class IPaymentService implements PaymentService {

    private final PaymentRepo paymentRepo;

    @Autowired
    IPaymentService(PaymentRepo paymentRepo) {
        this.paymentRepo = paymentRepo;
    }

    @Override
    public Payment createPayment(int desk, int hookahBalance) {
        Payment payment = new Payment(desk, hookahBalance);
        paymentRepo.save(payment);
        return payment;
    }

    @Override
    public Payment getPayment(Payment payment) {
        return payment;
    }

    @Override
    public int getHookahBalance(Payment payment) {
        return payment.getHookahBalance();
    }

    @Override
    public void replenishBalance(String id, int hookahBalance) {
        Payment payment = paymentRepo.findById(id).get();
        payment.setHookahBalance(hookahBalance + payment.getHookahBalance());
        paymentRepo.save(payment);
    }

    @Override
    public Payment getPaymentById(String id) {
        return paymentRepo.findById(id).get();
    }

    @Override
    public void deletePaymentById(String id) {
        paymentRepo.delete(getPaymentById(id));
    }

    @Override
    public List<Payment> getPayments() {
        List<Payment> payments = (List<Payment>) paymentRepo.findAll();
        return payments;
    }

    @Override
    public String paymentsReview() {
        List<Payment> payments = (List<Payment>) paymentRepo.findAll();
        String review = "Payment desks (registers) list:\n";
        for (Payment payment : payments) {
            System.out.println(payment);
            review += payment + "\n";
        }

        return review;
    }

    @Override
    public void createPayment(Payment payment) {

    }

}
