package com.example.hr.backend_springboot.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;

@Entity
@Table(name = "regions")
public class Region {
    @Id
    @Column(name = "region_id")
    private Long regionId;

    @Column(name = "region_name")
    @NotBlank
    private String regionName;

    // Getters and setters omitted for brevity
} 