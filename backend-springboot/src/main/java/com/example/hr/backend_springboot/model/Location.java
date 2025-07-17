package com.example.hr.backend_springboot.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "locations")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Location {
    @Id
    @Column(name = "location_id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "locations_seq")
    @SequenceGenerator(name = "locations_seq", sequenceName = "LOCATIONS_SEQ", allocationSize = 1)
    private Long locationId;

    @Column(name = "street_address", length = 40)
    private String streetAddress;

    @Column(name = "postal_code", length = 12)
    private String postalCode;

    @Column(name = "city", length = 30)
    @NotBlank
    private String city;

    @Column(name = "state_province", length = 25)
    private String stateProvince;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "country_id")
    private Country country;

    // Default constructor
    public Location() {}

    // Getters and setters
    public Long getLocationId() {
        return locationId;
    }

    public void setLocationId(Long locationId) {
        this.locationId = locationId;
    }

    public String getStreetAddress() {
        return streetAddress;
    }

    public void setStreetAddress(String streetAddress) {
        this.streetAddress = streetAddress;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getStateProvince() {
        return stateProvince;
    }

    public void setStateProvince(String stateProvince) {
        this.stateProvince = stateProvince;
    }

    public Country getCountry() {
        return country;
    }

    public void setCountry(Country country) {
        this.country = country;
    }

    @Override
    public String toString() {
        return "Location{" +
                "locationId=" + locationId +
                ", city='" + city + '\'' +
                ", stateProvince='" + stateProvince + '\'' +
                '}';
    }
}
