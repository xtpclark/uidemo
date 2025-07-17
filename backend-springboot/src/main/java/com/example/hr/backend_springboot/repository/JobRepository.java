package com.example.hr.backend_springboot.repository;

import com.example.hr.backend_springboot.model.Job;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JobRepository extends JpaRepository<Job, String> {
} 