package com.example.hr.backend_springboot.repository;

import com.example.hr.backend_springboot.model.JobHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JobHistoryRepository extends JpaRepository<JobHistory, JobHistory.JobHistoryId> {
} 