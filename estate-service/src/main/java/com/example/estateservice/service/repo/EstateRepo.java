package com.example.estateservice.service.repo;

import com.example.estateservice.service.models.Estate;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EstateRepo extends CrudRepository<Estate, String> {
}