package com.example.hr.backend_springboot.dto;

import java.math.BigDecimal;

public class EmployeeHierarchyDTO {
    private Integer hierarchyLevel;
    private String employeeName;
    private String jobTitle;
    private String departmentName;
    private BigDecimal salary;

    public EmployeeHierarchyDTO(Integer hierarchyLevel, String employeeName, String jobTitle, String departmentName, BigDecimal salary) {
        this.hierarchyLevel = hierarchyLevel;
        this.employeeName = employeeName;
        this.jobTitle = jobTitle;
        this.departmentName = departmentName;
        this.salary = salary;
    }

    // Getters and Setters
    public Integer getHierarchyLevel() { return hierarchyLevel; }
    public void setHierarchyLevel(Integer hierarchyLevel) { this.hierarchyLevel = hierarchyLevel; }
    
    public String getEmployeeName() { return employeeName; }
    public void setEmployeeName(String employeeName) { this.employeeName = employeeName; }
    
    public String getJobTitle() { return jobTitle; }
    public void setJobTitle(String jobTitle) { this.jobTitle = jobTitle; }
    
    public String getDepartmentName() { return departmentName; }
    public void setDepartmentName(String departmentName) { this.departmentName = departmentName; }
    
    public BigDecimal getSalary() { return salary; }
    public void setSalary(BigDecimal salary) { this.salary = salary; }
} 