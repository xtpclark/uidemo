package com.example.hr.backend_springboot.repository;

import com.example.hr.backend_springboot.model.Department;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DepartmentRepository extends JpaRepository<Department, Long> {
} 