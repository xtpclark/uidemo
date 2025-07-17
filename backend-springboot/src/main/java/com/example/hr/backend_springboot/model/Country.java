package com.example.hr.backend_springboot.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;

@Entity
@Table(name = "countries")
public class Country {
    @Id
    @Column(name = "country_id")
    private String countryId;

    @Column(name = "country_name")
    @NotBlank
    private String countryName;

    @ManyToOne
    @JoinColumn(name = "region_id")
    private Region region;

    // Getters and setters omitted for brevity
} 