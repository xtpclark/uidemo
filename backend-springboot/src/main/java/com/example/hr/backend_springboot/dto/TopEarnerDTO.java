package com.example.hr.backend_springboot.dto;

import java.math.BigDecimal;

public class TopEarnerDTO {
    private String departmentName;
    private String employeeName;
    private BigDecimal salary;
    private BigDecimal commissionPct;
    private BigDecimal totalCompensation;

    public TopEarnerDTO(String departmentName, String employeeName, BigDecimal salary, BigDecimal commissionPct, BigDecimal totalCompensation) {
        this.departmentName = departmentName;
        this.employeeName = employeeName;
        this.salary = salary;
        this.commissionPct = commissionPct;
        this.totalCompensation = totalCompensation;
    }

    public String getDepartmentName() { return departmentName; }
    public String getEmployeeName() { return employeeName; }
    public BigDecimal getSalary() { return salary; }
    public BigDecimal getCommissionPct() { return commissionPct; }
    public BigDecimal getTotalCompensation() { return totalCompensation; }
} 