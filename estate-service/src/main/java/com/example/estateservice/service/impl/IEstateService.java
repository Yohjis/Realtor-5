package com.example.estateservice.service.impl;

import java.util.List;


import com.example.estateservice.service.EstateService;
import com.example.estateservice.service.models.*;
import com.example.estateservice.service.repo.EstateRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class IEstateService implements EstateService {

    private final EstateRepo estateRepo;

    @Autowired
    IEstateService(EstateRepo estateRepo) {
        this.estateRepo = estateRepo;
    }

    @Override
    public int getEstatePrice(String id) {
        Estate estate = estateRepo.findById(id).get();
        return estate.getPrice();
    }

    @Override
    public void sellEstate(String id) {
        estateRepo.delete(getEstateById(id));
    }

    @Override
    public Estate getEstateById(String id) {
        return estateRepo.findById(id).get();
    }

    @Override
    public Estate deliverToShop(EstateDistrict district, EstateAddress address, EstateBuilding building, EstateRooms rooms, int price) {
        Estate estate = new Estate(district, address, building, rooms, price);
        estateRepo.save(estate);
        return estate;
    }

    @Override
    public List<Estate> getEstates() {
        List<Estate> allEstates = (List<Estate>) estateRepo.findAll();
        return allEstates;
    }

    @Override
    public String estateRemainder() {
        List<Estate> allEstates = (List<Estate>) estateRepo.findAll();

        String remainder = "All estates left on stock:\n";

        for (Estate estate : allEstates) {
            System.out.println(estate);
            remainder += estate + "\n";
        }

        return remainder;
    }

    @Override
    public void createEstate(Estate estate) {

    }

}