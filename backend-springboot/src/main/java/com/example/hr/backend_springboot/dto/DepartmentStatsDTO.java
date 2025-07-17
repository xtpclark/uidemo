package com.example.hr.backend_springboot.dto;

import java.math.BigDecimal;

public class DepartmentStatsDTO {
    private String departmentName;
    private Long employeeCount;
    private BigDecimal avgSalary;
    private BigDecimal medianSalary;
    private BigDecimal minSalary;
    private BigDecimal maxSalary;
    private BigDecimal stddevSalary;

    public DepartmentStatsDTO(String departmentName, Long employeeCount, BigDecimal avgSalary, 
                            BigDecimal medianSalary, BigDecimal minSalary, BigDecimal maxSalary, BigDecimal stddevSalary) {
        this.departmentName = departmentName;
        this.employeeCount = employeeCount;
        this.avgSalary = avgSalary;
        this.medianSalary = medianSalary;
        this.minSalary = minSalary;
        this.maxSalary = maxSalary;
        this.stddevSalary = stddevSalary;
    }

    // Getters and Setters
    public String getDepartmentName() { return departmentName; }
    public void setDepartmentName(String departmentName) { this.departmentName = departmentName; }
    
    public Long getEmployeeCount() { return employeeCount; }
    public void setEmployeeCount(Long employeeCount) { this.employeeCount = employeeCount; }
    
    public BigDecimal getAvgSalary() { return avgSalary; }
    public void setAvgSalary(BigDecimal avgSalary) { this.avgSalary = avgSalary; }
    
    public BigDecimal getMedianSalary() { return medianSalary; }
    public void setMedianSalary(BigDecimal medianSalary) { this.medianSalary = medianSalary; }
    
    public BigDecimal getMinSalary() { return minSalary; }
    public void setMinSalary(BigDecimal minSalary) { this.minSalary = minSalary; }
    
    public BigDecimal getMaxSalary() { return maxSalary; }
    public void setMaxSalary(BigDecimal maxSalary) { this.maxSalary = maxSalary; }
    
    public BigDecimal getStddevSalary() { return stddevSalary; }
    public void setStddevSalary(BigDecimal stddevSalary) { this.stddevSalary = stddevSalary; }
} 