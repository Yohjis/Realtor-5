package com.example.mediator.api.controllers;

import com.example.mediator.models.estate.EstateAddress;
import com.example.mediator.models.estate.EstateBuilding;
import com.example.mediator.models.estate.EstateDistrict;
import com.example.mediator.models.estate.EstateRooms;
import org.springframework.http.HttpEntity;
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
@RequestMapping(value = "estate")
public class EstateController {
    private final RestTemplate template = new RestTemplate();
    private final String address = "http://localhost:8084/estates/";

    @PostMapping
    public ResponseEntity<String> create(@RequestParam EstateDistrict district, @RequestParam EstateAddress e_address,
                                         @RequestParam EstateBuilding building, @RequestParam EstateRooms rooms, @RequestParam int price) {

        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(address).queryParam("Solomianski", district)
                .queryParam("Metalistiv", e_address).queryParam("new_building", building).queryParam("one_room", rooms).queryParam("price", price);
        HttpEntity<String> response = template.exchange(builder.toUriString(), HttpMethod.POST, null, String.class);
        return ResponseEntity.ok(response.getBody());
    }

    @GetMapping
    public ResponseEntity<String> show() {
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(address);

        HttpEntity<String> response = template.exchange(builder.toUriString(), HttpMethod.GET, null, String.class);
        return ResponseEntity.ok(response.getBody());
    }

    @DeleteMapping
    public void delete(@RequestParam String paymentId, @RequestParam String estateId) {
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(address).queryParam("estateId", estateId)
                .queryParam("paymentId", paymentId);

        template.exchange(builder.toUriString(), HttpMethod.DELETE, null, Object.class);
    }

}