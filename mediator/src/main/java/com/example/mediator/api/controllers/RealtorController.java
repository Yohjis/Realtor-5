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
@RequestMapping(value = "/realtors")
public class RealtorController {
    private final RestTemplate template = new RestTemplate();
    private final String address = "http://localhost:8082/employees";

    @PostMapping
    public ResponseEntity<String> create(@RequestParam String name, @RequestParam String surname) {
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(address).queryParam("name", name)
                .queryParam("surname", surname);
        HttpEntity<String> response = template.exchange(builder.toUriString(), HttpMethod.POST, null, String.class);
        return ResponseEntity.ok(response.getBody());
    }

    @GetMapping
    public ResponseEntity<String> show() {
        HttpEntity<String> answer = template.exchange(address, HttpMethod.GET, null, String.class);
        return ResponseEntity.ok(answer.getBody());
    }

    @DeleteMapping
    public ResponseEntity<Object> delete(@RequestParam String id) {
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(address).queryParam("id", id);

        HttpEntity<Object> response = template.exchange(builder.toUriString(), HttpMethod.DELETE, null, Object.class);
        return ResponseEntity.ok(response.getBody());
    }

}
