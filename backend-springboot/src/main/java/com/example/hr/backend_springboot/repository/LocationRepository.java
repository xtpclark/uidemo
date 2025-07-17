package com.example.hr.backend_springboot.repository;

import com.example.hr.backend_springboot.model.Location;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LocationRepository extends JpaRepository<Location, Long> {
} 