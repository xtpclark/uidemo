package com.example.hr.backend_springboot.controller;

import com.example.hr.backend_springboot.model.Job;
import com.example.hr.backend_springboot.repository.JobRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.Optional;

@Controller
@RequestMapping("/jobs")
public class JobController {
    @Autowired
    private JobRepository jobRepository;

    @GetMapping
    public String listJobs(Model model) {
        model.addAttribute("jobs", jobRepository.findAll());
        return "job/list";
    }

    @GetMapping("/view/{id}")
    public String viewJob(@PathVariable String id, Model model) {
        Optional<Job> job = jobRepository.findById(id);
        model.addAttribute("job", job.orElse(null));
        return "job/view";
    }
} 