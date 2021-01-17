package com.example.paymentservice.service.repos;

import com.example.paymentservice.service.models.Payment;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentRepo extends CrudRepository<Payment, String> {
}