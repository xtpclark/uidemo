package com.example.hr.backend_springboot.controller;

import com.example.hr.backend_springboot.model.Department;
import com.example.hr.backend_springboot.repository.DepartmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.Optional;

@Controller
@RequestMapping("/departments")
public class DepartmentController {
    @Autowired
    private DepartmentRepository departmentRepository;

    @GetMapping
    public String listDepartments(Model model) {
        model.addAttribute("departments", departmentRepository.findAll());
        return "department/list";
    }

    @GetMapping("/view/{id}")
    public String viewDepartment(@PathVariable Long id, Model model) {
        Optional<Department> department = departmentRepository.findById(id);
        model.addAttribute("department", department.orElse(null));
        return "department/view";
    }
} 