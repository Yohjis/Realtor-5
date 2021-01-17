package com.example.realtorservice.services.repos;

import com.example.realtorservice.services.models.Realtor;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RealtorRepo extends CrudRepository<Realtor, String> {
}