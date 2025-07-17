package com.example.hr.backend_springboot.controller;

import com.example.hr.backend_springboot.model.JobHistory;
import com.example.hr.backend_springboot.repository.JobHistoryRepository;
import com.example.hr.backend_springboot.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@Controller
@RequestMapping("/jobhistory")
public class JobHistoryController {
    @Autowired
    private JobHistoryRepository jobHistoryRepository;
    @Autowired
    private EmployeeRepository employeeRepository;

    @GetMapping("/employee/{employeeId}")
    public String listJobHistoryByEmployee(@PathVariable Long employeeId, Model model) {
        List<JobHistory> jobHistory = jobHistoryRepository.findAll().stream()
            .filter(jh -> jh.getEmployee().getEmployeeId().equals(employeeId))
            .toList();
        model.addAttribute("jobHistory", jobHistory);
        model.addAttribute("employee", employeeRepository.findById(employeeId).orElse(null));
        return "jobhistory/list";
    }
} 