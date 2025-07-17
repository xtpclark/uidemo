package com.example.hr.backend_springboot.model;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.validation.constraints.*;

@Entity
@Table(name = "employees")
public class Employee {
    @Id
    @Column(name = "employee_id")
    private Long employeeId;

    @Column(name = "first_name")
    @NotBlank
    private String firstName;

    @Column(name = "last_name")
    @NotBlank
    private String lastName;

    @Column(name = "email")
    @Email
    @NotBlank
    private String email;

    @Column(name = "phone_number")
    @NotBlank
    private String phoneNumber;

    @Column(name = "hire_date")
    @NotNull
    private LocalDate hireDate;

    @ManyToOne
    @JoinColumn(name = "job_id")
    private Job job;

    @Column(name = "salary")
    @NotNull
    @DecimalMin("0.0")
    private BigDecimal salary;

    @Column(name = "commission_pct")
    private BigDecimal commissionPct;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "manager_id")
    @JsonBackReference
    private Employee manager;

    @OneToMany(mappedBy = "manager", fetch = FetchType.LAZY)
    @JsonManagedReference
    private List<Employee> subordinates;

    @ManyToOne
    @JoinColumn(name = "department_id")
    private Department department;

    public Long getEmployeeId() {
        return employeeId;
    }
    public void setEmployeeId(Long employeeId) {
        this.employeeId = employeeId;
    }

    public String getFirstName() {
        return firstName;
    }
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    public String getLastName() {
        return lastName;
    }
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getPhoneNumber() {
        return phoneNumber;
    }
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
    public LocalDate getHireDate() {
        return hireDate;
    }
    public void setHireDate(LocalDate hireDate) {
        this.hireDate = hireDate;
    }
    public Job getJob() {
        return job;
    }
    public void setJob(Job job) {
        this.job = job;
    }
    public BigDecimal getSalary() {
        return salary;
    }
    public void setSalary(BigDecimal salary) {
        this.salary = salary;
    }
    public BigDecimal getCommissionPct() {
        return commissionPct;
    }
    public void setCommissionPct(BigDecimal commissionPct) {
        this.commissionPct = commissionPct;
    }
    public Employee getManager() {
        return manager;
    }
    public void setManager(Employee manager) {
        this.manager = manager;
    }
    public List<Employee> getSubordinates() {
        return subordinates;
    }
    public void setSubordinates(List<Employee> subordinates) {
        this.subordinates = subordinates;
    }
    public Department getDepartment() {
        return department;
    }
    public void setDepartment(Department department) {
        this.department = department;
    }
} 