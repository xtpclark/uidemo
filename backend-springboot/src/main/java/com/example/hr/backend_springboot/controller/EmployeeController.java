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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.web.PageableDefault;
import org.springframework.validation.BindingResult;
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
    public String listEmployees(Model model, @PageableDefault(size = 20) Pageable pageable) {
        Page<Employee> employees = employeeRepository.findAll(pageable);
        model.addAttribute("employees", employees);
        return "employee/list";
    }

    @GetMapping("/view/{id}")
    public String viewEmployee(@PathVariable Long id, Model model) {
        Optional<Employee> employee = employeeRepository.findById(id);
        if (employee.isPresent()) {
            model.addAttribute("employee", employee.get());
            return "employee/view";
        } else {
            return "redirect:/employees";
        }
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
    public String addEmployee(@ModelAttribute @Valid Employee employee, 
                            BindingResult result,
                            @RequestParam(required = false) Long department, 
                            @RequestParam(required = false) String job, 
                            @RequestParam(required = false) Long manager,
                            Model model,
                            RedirectAttributes redirectAttributes) {
        
        if (result.hasErrors()) {
            model.addAttribute("departments", departmentRepository.findAll());
            model.addAttribute("jobs", jobRepository.findAll());
            model.addAttribute("managers", employeeRepository.findAll());
            return "employee/add";
        }
        
        try {
            if (department != null) {
                employee.setDepartment(departmentRepository.findById(department).orElse(null));
            }
            if (job != null) {
                employee.setJob(jobRepository.findById(job).orElse(null));
            }
            if (manager != null) {
                employee.setManager(employeeRepository.findById(manager).orElse(null));
            }
            
            employeeRepository.save(employee);
            redirectAttributes.addFlashAttribute("successMessage", "Employee added successfully!");
            return "redirect:/employees";
        } catch (Exception e) {
            model.addAttribute("errorMessage", "Error saving employee: " + e.getMessage());
            model.addAttribute("departments", departmentRepository.findAll());
            model.addAttribute("jobs", jobRepository.findAll());
            model.addAttribute("managers", employeeRepository.findAll());
            return "employee/add";
        }
    }

    @GetMapping("/edit/{id}")
    public String editEmployeeForm(@PathVariable Long id, Model model) {
        Optional<Employee> employee = employeeRepository.findById(id);
        if (employee.isPresent()) {
            model.addAttribute("employee", employee.get());
            model.addAttribute("departments", departmentRepository.findAll());
            model.addAttribute("jobs", jobRepository.findAll());
            model.addAttribute("managers", employeeRepository.findAll());
            return "employee/edit";
        } else {
            return "redirect:/employees";
        }
    }

    @PostMapping("/edit/{id}")
    public String editEmployee(@PathVariable Long id, 
                             @ModelAttribute @Valid Employee employee, 
                             BindingResult result,
                             @RequestParam(required = false) Long department, 
                             @RequestParam(required = false) String job, 
                             @RequestParam(required = false) Long manager,
                             Model model,
                             RedirectAttributes redirectAttributes) {
        
        if (result.hasErrors()) {
            model.addAttribute("departments", departmentRepository.findAll());
            model.addAttribute("jobs", jobRepository.findAll());
            model.addAttribute("managers", employeeRepository.findAll());
            return "employee/edit";
        }
        
        try {
            // Ensure we're updating the existing employee
            Optional<Employee> existingEmployee = employeeRepository.findById(id);
            if (existingEmployee.isPresent()) {
                Employee empToUpdate = existingEmployee.get();
                
                // Update fields
                empToUpdate.setFirstName(employee.getFirstName());
                empToUpdate.setLastName(employee.getLastName());
                empToUpdate.setEmail(employee.getEmail());
                empToUpdate.setPhoneNumber(employee.getPhoneNumber());
                empToUpdate.setHireDate(employee.getHireDate());
                empToUpdate.setSalary(employee.getSalary());
                empToUpdate.setCommissionPct(employee.getCommissionPct());
                
                if (department != null) {
                    empToUpdate.setDepartment(departmentRepository.findById(department).orElse(null));
                }
                if (job != null) {
                    empToUpdate.setJob(jobRepository.findById(job).orElse(null));
                }
                if (manager != null) {
                    empToUpdate.setManager(employeeRepository.findById(manager).orElse(null));
                } else {
                    empToUpdate.setManager(null);
                }
                
                employeeRepository.save(empToUpdate);
                redirectAttributes.addFlashAttribute("successMessage", "Employee updated successfully!");
                return "redirect:/employees";
            } else {
                return "redirect:/employees";
            }
        } catch (Exception e) {
            model.addAttribute("errorMessage", "Error updating employee: " + e.getMessage());
            model.addAttribute("departments", departmentRepository.findAll());
            model.addAttribute("jobs", jobRepository.findAll());
            model.addAttribute("managers", employeeRepository.findAll());
            return "employee/edit";
        }
    }

    @GetMapping("/delete/{id}")
    public String deleteEmployee(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            employeeRepository.deleteById(id);
            redirectAttributes.addFlashAttribute("successMessage", "Employee deleted successfully!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Error deleting employee: " + e.getMessage());
        }
        return "redirect:/employees";
    }
}
