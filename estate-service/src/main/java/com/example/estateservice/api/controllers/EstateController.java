package com.example.estateservice.api.controllers;

import com.example.estateservice.service.EstateService;
import com.example.estateservice.service.models.*;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping(value = "estates")
public class EstateController {
    private final EstateService estateService;

    @Autowired
    public EstateController(EstateService estateService) {
        this.estateService = estateService;
    }

    @PostMapping
    public ResponseEntity<Estate> create(@RequestParam EstateDistrict district, @RequestParam EstateAddress address,
                                         @RequestParam EstateBuilding building, @RequestParam EstateRooms rooms, @RequestParam int price) {

        return ResponseEntity.ok(estateService.deliverToShop(district, address, building, rooms, price));
    }

    @DeleteMapping
    public void delete(@RequestParam String estateId, @RequestParam String paymentId) {
        int estatePrice = estateService.getEstatePrice(estateId);
        estateService.sellEstate(estateId);

        String address = "http://payment-service:8083/payments/";
        RestTemplate restTemplate = new RestTemplate();

        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(address).queryParam("paymentId", paymentId)
                .queryParam("price", estatePrice);
        restTemplate.exchange(builder.toUriString(), HttpMethod.PUT, null, String.class);
    }

    @GetMapping
    public ResponseEntity<String> show() {
        return ResponseEntity.ok(estateService.estateRemainder());
    }

}