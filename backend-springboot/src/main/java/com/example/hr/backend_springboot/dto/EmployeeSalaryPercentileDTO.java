package com.example.hr.backend_springboot.dto;

import java.math.BigDecimal;

public class EmployeeSalaryPercentileDTO {
    private String departmentName;
    private String employeeName;
    private BigDecimal salary;
    private BigDecimal salaryPercentile;

    public EmployeeSalaryPercentileDTO(String departmentName, String employeeName, BigDecimal salary, BigDecimal salaryPercentile) {
        this.departmentName = departmentName;
        this.employeeName = employeeName;
        this.salary = salary;
        this.salaryPercentile = salaryPercentile;
    }

    // Getters and Setters
    public String getDepartmentName() { return departmentName; }
    public void setDepartmentName(String departmentName) { this.departmentName = departmentName; }
    
    public String getEmployeeName() { return employeeName; }
    public void setEmployeeName(String employeeName) { this.employeeName = employeeName; }
    
    public BigDecimal getSalary() { return salary; }
    public void setSalary(BigDecimal salary) { this.salary = salary; }
    
    public BigDecimal getSalaryPercentile() { return salaryPercentile; }
    public void setSalaryPercentile(BigDecimal salaryPercentile) { this.salaryPercentile = salaryPercentile; }
} 