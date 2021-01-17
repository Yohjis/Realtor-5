package com.example.realtorservice.api.controllers;

import com.example.realtorservice.services.RealtorService;
import com.example.realtorservice.services.models.Realtor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/realtors")
public class RealtorController {
    private final RealtorService paymentService;

    @Autowired
    public RealtorController(RealtorService paymentService) {
        this.paymentService = paymentService;
    }

    @PostMapping
    public ResponseEntity<Realtor> create(@RequestParam String name, @RequestParam String surname) {
        return ResponseEntity.ok(paymentService.engageRealtor(name, surname));
    }

    @DeleteMapping
    public ResponseEntity<Void> delete(@RequestParam String id) {
        paymentService.deleteRealtorById(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<String> show() {
        return ResponseEntity.ok(paymentService.realtorReview());
    }
}