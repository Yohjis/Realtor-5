package com.example.realtorservice.services;

import com.example.realtorservice.services.models.Realtor;

import java.util.List;

public interface RealtorService {

    Realtor engageRealtor(String name, String surname);

    Realtor getRealtorById(String id);

    void deleteRealtorById(String id);

    List<Realtor> getRealtors();

    String realtorReview();

    void createRealtor(Realtor realtor);

}
