package com.example.realtorservice.services.models;

import java.util.UUID;
import javax.persistence.Entity;
import javax.persistence.Id;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;


@Entity
@EnableAutoConfiguration

public final class Realtor {
    @Id
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
                + ".\n My job is to sell estates.";
        System.out.println(open);
    }

    public void WorkingFinish() {
        final String closed = "Realtor " + name + surname + "has finished to work today.";
        System.out.println(closed);
    }

    @Override
    public String toString() {
        return "Realtor " + name + " " + surname + " with personal Id " + id;
    }
}