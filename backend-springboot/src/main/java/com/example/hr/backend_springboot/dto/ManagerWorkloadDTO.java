package com.example.hr.backend_springboot.dto;

import java.math.BigDecimal;

public class ManagerWorkloadDTO {
    private String managerName;
    private String departmentName;
    private Long directReports;
    private BigDecimal totalTeamSalary;

    public ManagerWorkloadDTO(String managerName, String departmentName, Long directReports, BigDecimal totalTeamSalary) {
        this.managerName = managerName;
        this.departmentName = departmentName;
        this.directReports = directReports;
        this.totalTeamSalary = totalTeamSalary;
    }

    // Getters and Setters
    public String getManagerName() { return managerName; }
    public void setManagerName(String managerName) { this.managerName = managerName; }
    
    public String getDepartmentName() { return departmentName; }
    public void setDepartmentName(String departmentName) { this.departmentName = departmentName; }
    
    public Long getDirectReports() { return directReports; }
    public void setDirectReports(Long directReports) { this.directReports = directReports; }
    
    public BigDecimal getTotalTeamSalary() { return totalTeamSalary; }
    public void setTotalTeamSalary(BigDecimal totalTeamSalary) { this.totalTeamSalary = totalTeamSalary; }
} 