package com.example.hr.backend_springboot.model;

import jakarta.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

@Entity
@Table(name = "job_history")
@IdClass(JobHistory.JobHistoryId.class)
public class JobHistory {
    @Id
    @ManyToOne
    @JoinColumn(name = "employee_id")
    private Employee employee;

    @Id
    @Column(name = "start_date")
    private LocalDate startDate;

    @Column(name = "end_date")
    private LocalDate endDate;

    @ManyToOne
    @JoinColumn(name = "job_id")
    private Job job;

    @ManyToOne
    @JoinColumn(name = "department_id")
    private Department department;

    // Default constructor
    public JobHistory() {}

    // Getters and setters
    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public Job getJob() {
        return job;
    }

    public void setJob(Job job) {
        this.job = job;
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    // Composite key class
    public static class JobHistoryId implements Serializable {
        private Long employee;
        private LocalDate startDate;

        // Default constructor
        public JobHistoryId() {}

        // Constructor
        public JobHistoryId(Long employee, LocalDate startDate) {
            this.employee = employee;
            this.startDate = startDate;
        }

        // Getters and setters
        public Long getEmployee() {
            return employee;
        }

        public void setEmployee(Long employee) {
            this.employee = employee;
        }

        public LocalDate getStartDate() {
            return startDate;
        }

        public void setStartDate(LocalDate startDate) {
            this.startDate = startDate;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            JobHistoryId that = (JobHistoryId) o;
            return Objects.equals(employee, that.employee) &&
                   Objects.equals(startDate, that.startDate);
        }

        @Override
        public int hashCode() {
            return Objects.hash(employee, startDate);
        }
    }
}
