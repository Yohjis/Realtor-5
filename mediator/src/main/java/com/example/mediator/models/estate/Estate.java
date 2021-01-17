package com.example.mediator.models.estate;

import java.util.UUID;

public final class Estate {
    private String estateId;
    private EstateDistrict district;
    private EstateAddress address;
    private EstateBuilding building;
    private EstateRooms rooms;
    private int price;

    public Estate(EstateDistrict district, EstateAddress e_address, EstateBuilding building, EstateRooms rooms, int price) {
        this.estateId = UUID.randomUUID().toString();
        this.district = district;
        this.address = e_address;
        this.building = building;
        this.rooms = rooms;
        this.price = price;
    }

    public Estate() {

    }


    public String getEstateId() {
        return estateId;
    }

    public EstateDistrict getDistrict() {
        return district;
    }

    public EstateAddress getAddress() {
        return address;
    }

    public EstateBuilding getBuilding() {
        return building;
    }

    public EstateRooms getRooms() {
        return rooms;
    }

    @Override
    public String toString() {
        return district + " " + address + " estate " + building + ". Price: " + price + "For" + rooms + ". Product Id: " + estateId;
    }

}
