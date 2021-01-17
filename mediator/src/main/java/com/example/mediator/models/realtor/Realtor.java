package com.example.mediator.models.realtor;

import java.util.UUID;

public final class Realtor {
    private String id;
    private String name;
    private String surname;

    public Realtor() {
    }

    public Realtor(String name, String surname) {
        this.name = name;
        this.surname = surname;
        this.id = UUID.randomUUID().toString();
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public String getId() {
        return id;
    }

    public void WorkingStart() {
        final String open = "Greetings! I'm " + name + surname
                + ".\n My job is to sell hookah components. So I will get to my desk, open Hookah place balance and start to work .";
        System.out.println(open);
    }

    public void WorkingFinish() {
        final String closed = "Employee " + name + surname + "has finished to work today.";
        System.out.println(closed);
    }

    @Override
    public String toString() {
        return "Employee " + name + " " + surname + " with personal Id " + id;
    }
}
