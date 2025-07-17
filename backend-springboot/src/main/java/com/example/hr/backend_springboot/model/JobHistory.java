package com.example.hr.backend_springboot.model;

import jakarta.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;

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

    public Employee getEmployee() {
        return employee;
    }

    // Composite key class
    public static class JobHistoryId implements Serializable {
        private Long employee;
        private LocalDate startDate;

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            JobHistoryId that = (JobHistoryId) o;
            return java.util.Objects.equals(employee, that.employee) &&
                   java.util.Objects.equals(startDate, that.startDate);
        }

        @Override
        public int hashCode() {
            return java.util.Objects.hash(employee, startDate);
        }
    }
    // Getters and setters omitted for brevity
} 