package com.example.hr.backend_springboot.controller;

import com.example.hr.backend_springboot.model.Employee;
import com.example.hr.backend_springboot.model.Department;
import com.example.hr.backend_springboot.model.Job;
import com.example.hr.backend_springboot.repository.EmployeeRepository;
import com.example.hr.backend_springboot.repository.DepartmentRepository;
import com.example.hr.backend_springboot.repository.JobRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.validation.annotation.Validated;
import jakarta.validation.Valid;

@Controller
@RequestMapping("/employees")
public class EmployeeController {
    @Autowired
    private EmployeeRepository employeeRepository;
    @Autowired
    private DepartmentRepository departmentRepository;
    @Autowired
    private JobRepository jobRepository;

    @GetMapping
    public String listEmployees(Model model, Pageable pageable) {
        Page<Employee> employees = employeeRepository.findAll(pageable);
        model.addAttribute("employees", employees);
        return "employee/list";
    }

    @GetMapping("/view/{id}")
    public String viewEmployee(@PathVariable Long id, Model model) {
        Optional<Employee> employee = employeeRepository.findById(id);
        model.addAttribute("employee", employee.orElse(null));
        return "employee/view";
    }

    @GetMapping("/add")
    public String addEmployeeForm(Model model) {
        model.addAttribute("employee", new Employee());
        model.addAttribute("departments", departmentRepository.findAll());
        model.addAttribute("jobs", jobRepository.findAll());
        model.addAttribute("managers", employeeRepository.findAll());
        return "employee/add";
    }

    @PostMapping("/add")
    public String addEmployee(@ModelAttribute @Valid Employee employee, @RequestParam Long department, @RequestParam String job, @RequestParam(required = false) Long manager) {
        employee.setDepartment(departmentRepository.findById(department).orElse(null));
        employee.setJob(jobRepository.findById(job).orElse(null));
        if (manager != null) {
            employee.setManager(employeeRepository.findById(manager).orElse(null));
        } else {
            employee.setManager(null);
        }
        employeeRepository.save(employee);
        return "redirect:/employees";
    }

    @GetMapping("/edit/{id}")
    public String editEmployeeForm(@PathVariable Long id, Model model) {
        Optional<Employee> employee = employeeRepository.findById(id);
        model.addAttribute("employee", employee.orElse(null));
        model.addAttribute("departments", departmentRepository.findAll());
        model.addAttribute("jobs", jobRepository.findAll());
        model.addAttribute("managers", employeeRepository.findAll());
        return "employee/edit";
    }

    @PostMapping("/edit/{id}")
    public String editEmployee(@PathVariable Long id, @ModelAttribute @Valid Employee employee, @RequestParam Long department, @RequestParam String job, @RequestParam(required = false) Long manager) {
        employee.setEmployeeId(id);
        employee.setDepartment(departmentRepository.findById(department).orElse(null));
        employee.setJob(jobRepository.findById(job).orElse(null));
        if (manager != null) {
            employee.setManager(employeeRepository.findById(manager).orElse(null));
        } else {
            employee.setManager(null);
        }
        employeeRepository.save(employee);
        return "redirect:/employees";
    }

    @GetMapping("/delete/{id}")
    public String deleteEmployee(@PathVariable Long id) {
        employeeRepository.deleteById(id);
        return "redirect:/employees";
    }
} 