package com.example.hr.backend_springboot.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "regions")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Region {
    @Id
    @Column(name = "region_id")
    private Long regionId;

    @Column(name = "region_name", length = 25)
    private String regionName;

    // Default constructor
    public Region() {}

    // Getters and setters
    public Long getRegionId() {
        return regionId;
    }

    public void setRegionId(Long regionId) {
        this.regionId = regionId;
    }

    public String getRegionName() {
        return regionName;
    }

    public void setRegionName(String regionName) {
        this.regionName = regionName;
    }

    @Override
    public String toString() {
        return "Region{" +
                "regionId=" + regionId +
                ", regionName='" + regionName + '\'' +
                '}';
    }
}
