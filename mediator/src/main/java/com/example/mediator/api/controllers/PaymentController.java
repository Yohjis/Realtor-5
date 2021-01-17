package com.example.mediator.api.controllers;

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
@RequestMapping(value = "/payments")
public class PaymentController {
    private final RestTemplate template = new RestTemplate();
    private final String address = "http://localhost:8083/payments/";

    @PostMapping
    public ResponseEntity<String> create(@RequestParam int desk, @RequestParam int estateBalance) {
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(address).queryParam("desk", desk)
                .queryParam("estateBalance", estateBalance);

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
    public ResponseEntity<Object> delete(@RequestParam String id) {
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(address).queryParam("id", id);

        HttpEntity<Object> response = template.exchange(builder.toUriString(), HttpMethod.DELETE, null, Object.class);
        return ResponseEntity.ok(response.getBody());
    }

}
