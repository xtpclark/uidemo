package com.example.hr.backend_springboot.repository;

import com.example.hr.backend_springboot.model.Region;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RegionRepository extends JpaRepository<Region, Long> {
} 