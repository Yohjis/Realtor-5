package com.example.estateservice.service.models;


import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.Id;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;

@Entity
@EnableAutoConfiguration

public final class Estate {
    @Id
    private String estateId;
    private EstateDistrict district;
    private EstateAddress address;
    private EstateBuilding building;
    private EstateRooms rooms;
    private int price;

    public Estate(EstateDistrict district,EstateAddress address, EstateBuilding building, EstateRooms rooms, int price) {
        this.district = district;
        this.address = address;
        this.building = building;
        this.rooms = rooms;
        this.price = price;
        this.estateId = UUID.randomUUID().toString();
    }

    public Estate() {

    }

    public String getEstateId() {
        return estateId;
    }

    public String getDistrict() {
        return district.toString();
    }

    public String getAddress() {
        return address.toString();
    }

    public String getBuilding() {
        return building.toString();
    }

    public String getRooms(){
        return rooms.toString();
    }
    
    public int getPrice() {
        return price;
    }

    @Override
    public String toString() {
        return district + " " + address + " with " + rooms + " rooms. Price: " + price + ". Estate Id: " + estateId;
    }

}
