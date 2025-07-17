package com.example.hr.backend_springboot.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;

@Entity
@Table(name = "departments")
public class Department {
    @Id
    @Column(name = "department_id")
    private Long departmentId;

    @Column(name = "department_name")
    @NotBlank
    private String departmentName;

    @ManyToOne
    @JoinColumn(name = "manager_id")
    private Employee manager;

    @ManyToOne
    @JoinColumn(name = "location_id")
    private Location location;

    // Getters and setters
    public Long getDepartmentId() {
        return departmentId;
    }
    public void setDepartmentId(Long departmentId) {
        this.departmentId = departmentId;
    }
    public String getDepartmentName() {
        return departmentName;
    }
    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }
    public Employee getManager() {
        return manager;
    }
    public void setManager(Employee manager) {
        this.manager = manager;
    }
    public Location getLocation() {
        return location;
    }
    public void setLocation(Location location) {
        this.location = location;
    }
} 