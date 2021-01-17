package com.example.paymentservice.api.controllers;

import com.example.paymentservice.service.PaymentService;
import com.example.paymentservice.service.models.Payment;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/payments")
public class PaymentController {
    private final PaymentService paymentService;

    @Autowired
    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @PostMapping
    public ResponseEntity<Payment> create(@RequestParam int desk, @RequestParam int hookahBalance) {
        return ResponseEntity.ok(paymentService.createPayment(desk, hookahBalance));
    }

    @DeleteMapping
    public ResponseEntity<Void> delete(@RequestParam String id) {
        paymentService.deletePaymentById(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping
    public void update(@RequestParam String paymentId, @RequestParam int price) {
        paymentService.replenishBalance(paymentId, price);
        ;
    }

    @GetMapping
    public ResponseEntity<String> show() {
        return ResponseEntity.ok(paymentService.paymentsReview());
    }
}
