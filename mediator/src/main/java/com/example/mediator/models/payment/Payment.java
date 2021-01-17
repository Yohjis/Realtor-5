package com.example.mediator.models.payment;

import java.util.UUID;

public final class Payment {

    private String id;
    private int desk;
    private int hookahBalance;

    public Payment(int desk, int hookahBalance) {
        this.id = UUID.randomUUID().toString();
        this.desk = desk;
        this.hookahBalance = hookahBalance;
    }

    public Payment() {

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

    public void setHookahBalance(int hookahBalance) {
        this.hookahBalance = hookahBalance;
        System.out.println(
                "Hookah place balance: " + hookahBalance + "\nPayment id: " + id + "\nPayment desk number: " + desk);
    }

    @Override
    public String toString() {
        return "Desk number: " + desk + ". Money balance: " + hookahBalance + ". Desk id: " + id;
    }

}