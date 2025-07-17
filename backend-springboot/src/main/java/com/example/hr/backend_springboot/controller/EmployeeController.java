package com.example.hr.backend_springboot.controller;

import com.example.hr.backend_springboot.model.Employee;
import com.example.hr.backend_springboot.repository.DepartmentRepository;
import com.example.hr.backend_springboot.repository.EmployeeRepository;
import com.example.hr.backend_springboot.repository.JobRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Optional;

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
    public String addEmployee(@ModelAttribute("employee") @Valid Employee employeeFormData,
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
            // --- CORRECTED LOGIC ---
            // Create a new Employee entity to ensure it's a new record for insertion.
            Employee newEmployee = new Employee();

            // Manually set properties from the form-bound object.
            newEmployee.setFirstName(employeeFormData.getFirstName());
            newEmployee.setLastName(employeeFormData.getLastName());
            newEmployee.setEmail(employeeFormData.getEmail());
            newEmployee.setPhoneNumber(employeeFormData.getPhoneNumber());
            newEmployee.setHireDate(employeeFormData.getHireDate());
            newEmployee.setSalary(employeeFormData.getSalary());
            newEmployee.setCommissionPct(employeeFormData.getCommissionPct());

            // Set relationships from the request parameters.
            if (department != null) {
                newEmployee.setDepartment(departmentRepository.findById(department).orElse(null));
            }
            if (job != null) {
                newEmployee.setJob(jobRepository.findById(job).orElse(null));
            }
            if (manager != null) {
                newEmployee.setManager(employeeRepository.findById(manager).orElse(null));
            }

            // Save the new entity, forcing an INSERT.
            employeeRepository.save(newEmployee);

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
            // --- CORRECTED LOGIC ---
            // First, fetch the existing employee from the database.
            Optional<Employee> existingEmployee = employeeRepository.findById(id);
            if (existingEmployee.isPresent()) {
                Employee empToUpdate = existingEmployee.get();

                // Update only the fields from the form.
                empToUpdate.setFirstName(employee.getFirstName());
                empToUpdate.setLastName(employee.getLastName());
                empToUpdate.setEmail(employee.getEmail());
                empToUpdate.setPhoneNumber(employee.getPhoneNumber());
                empToUpdate.setHireDate(employee.getHireDate());
                empToUpdate.setSalary(employee.getSalary());
                empToUpdate.setCommissionPct(employee.getCommissionPct());

                if (department != null) {
                    empToUpdate.setDepartment(departmentRepository.findById(department).orElse(null));
                } else {
                    empToUpdate.setDepartment(null);
                }
                if (job != null) {
                    empToUpdate.setJob(jobRepository.findById(job).orElse(null));
                } else {
                    empToUpdate.setJob(null);
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
                redirectAttributes.addFlashAttribute("errorMessage", "Employee not found.");
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
