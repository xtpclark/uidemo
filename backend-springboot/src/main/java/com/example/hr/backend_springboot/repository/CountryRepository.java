package com.example.hr.backend_springboot.repository;

import com.example.hr.backend_springboot.model.Country;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CountryRepository extends JpaRepository<Country, String> {
} 