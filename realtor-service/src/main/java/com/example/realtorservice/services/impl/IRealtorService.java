package com.example.realtorservice.services.impl;

import java.util.List;

import com.example.realtorservice.services.RealtorService;
import com.example.realtorservice.services.models.Realtor;
import com.example.realtorservice.services.repos.RealtorRepo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class IRealtorService implements RealtorService {

    private final RealtorRepo realtorRepo;

    @Autowired
    IRealtorService(RealtorRepo realtorRepo) {
        this.realtorRepo = realtorRepo;
    }

    @Override
    public Realtor engageRealtor(String name, String surname) {
        Realtor realtor = new Realtor(name, surname);
        realtorRepo.save(realtor);
        return realtor;
    }

    @Override
    public Realtor getRealtorById(String id) {
        return realtorRepo.findById(id).get();
    }

    @Override
    public void deleteRealtorById(String id) {
        realtorRepo.delete(getRealtorById(id));
    }

    @Override
    public List<Realtor> getRealtors() {
        List<Realtor> realtors = (List<Realtor>) realtorRepo.findAll();
        return realtors;
    }

    @Override
    public String realtorReview() {
        List<Realtor> realtors = (List<Realtor>) realtorRepo.findAll();

        System.out.println("Realtors:");
        String review = "Realtors list:\n";

        for (Realtor realtor : realtors) {
            System.out.println(realtor);
            review += realtor + "\n";
        }

        return review;
    }

    @Override
    public void createRealtor(Realtor realtor) {

    }

}