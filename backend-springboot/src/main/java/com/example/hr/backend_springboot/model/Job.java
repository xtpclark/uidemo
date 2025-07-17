package com.example.hr.backend_springboot.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.math.BigDecimal;

@Entity
@Table(name = "jobs")
public class Job {
    @Id
    @Column(name = "job_id")
    private String jobId;

    @Column(name = "job_title")
    @NotBlank
    private String jobTitle;

    @Column(name = "min_salary")
    @NotNull
    @DecimalMin("0.0")
    private BigDecimal minSalary;

    @Column(name = "max_salary")
    @NotNull
    @DecimalMin("0.0")
    private BigDecimal maxSalary;

    // Getters and setters omitted for brevity
} 