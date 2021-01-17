package com.example.estateservice.service;

import com.example.estateservice.service.models.Estate;
import com.example.estateservice.service.models.EstateDistrict;
import com.example.estateservice.service.models.EstateAddress;
import com.example.estateservice.service.models.EstateBuilding;
import com.example.estateservice.service.models.EstateRooms;

import java.util.List;

public interface EstateService {

    void sellEstate(String id);

    int getEstatePrice(String id);

    Estate deliverToShop(EstateDistrict district, EstateAddress address, EstateBuilding building, EstateRooms rooms, int price);

    Estate getEstateById(String id);

    List<Estate> getEstates();

    String estateRemainder();

    void createEstate(Estate estate);
}