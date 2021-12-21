package com.example.mediator.models.payment;

import java.util.UUID;

public final class Payment {

    private String id;
    private int desk;
    private int realtorBalance;

    public Payment(int desk, int hookahBalance) {
        this.id = UUID.randomUUID().toString();
        this.desk = desk;
        this.realtorBalance = realtorBalance;
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

    public void setRealtorhBalance(int RealtorBalance) {
        this.realtorBalance = realtorBalance;
        System.out.println(
                "Realtor place balance: " + realtorBalance + "\nPayment id: " + id + "\nPayment desk number: " + desk);
    }

    @Override
    public String toString() {
        return "Desk number: " + desk + ". Money balance: " + realtorBalance + ". Desk id: " + id;
    }

}
