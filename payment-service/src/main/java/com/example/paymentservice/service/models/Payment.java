package com.example.paymentservice.service.models;

import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.Id;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;

@Entity
@EnableAutoConfiguration

public final class Payment {
    @Id
    private String id;
    private int desk;
    private int hookahBalance;

    public Payment() {

    }

    public Payment(int desk, int hookahBalance) {
        this.id = UUID.randomUUID().toString();
        this.desk = desk;
        this.hookahBalance = hookahBalance;
    }

    public String getId() {
        return id;
    }

    public int getHookahBalance() {
        return hookahBalance;
    }

    public int getDesk() {
        return desk;
    }

    public void setHookahBalance(int balance) {
        this.hookahBalance = balance;
        System.out
                .println("Hookah place balance: " + balance + "\nPayment id: " + id + "\nPayment desk number: " + desk);
    }

    @Override
    public String toString() {
        return "Desk number: " + desk + ". Money balance: " + hookahBalance + ". Desk id: " + id;
    }

}