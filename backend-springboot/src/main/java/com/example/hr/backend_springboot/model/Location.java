package com.example.hr.backend_springboot.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;

@Entity
@Table(name = "locations")
public class Location {
    @Id
    @Column(name = "location_id")
    private Long locationId;

    @Column(name = "street_address")
    @NotBlank
    private String streetAddress;

    @Column(name = "postal_code")
    @NotBlank
    private String postalCode;

    @Column(name = "city")
    @NotBlank
    private String city;

    @Column(name = "state_province")
    private String stateProvince;

    @ManyToOne
    @JoinColumn(name = "country_id")
    private Country country;

    // Getters and setters omitted for brevity
} 