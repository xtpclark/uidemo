package com.example.hr.backend_springboot.controller;

import com.example.hr.backend_springboot.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.ResponseEntity;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/diagnostic")
public class DiagnosticController {
    
    @Autowired
    private EmployeeRepository employeeRepository;
    
    @Autowired
    private DepartmentRepository departmentRepository;
    
    @Autowired
    private JobRepository jobRepository;
    
    @GetMapping("/health")
    public ResponseEntity<Map<String, Object>> checkHealth() {
        Map<String, Object> status = new HashMap<>();
        
        try {
            status.put("status", "UP");
            status.put("employeeCount", employeeRepository.count());
            status.put("departmentCount", departmentRepository.count());
            status.put("jobCount", jobRepository.count());
            
            // Test a simple query
            status.put("firstEmployee", employeeRepository.findAll().stream().findFirst().orElse(null));
            
            // Test native query
            try {
                employeeRepository.findTopEarnersByDepartmentRaw();
                status.put("nativeQueryStatus", "WORKING");
            } catch (Exception e) {
                status.put("nativeQueryStatus", "FAILED");
                status.put("nativeQueryError", e.getMessage());
            }
            
        } catch (Exception e) {
            status.put("status", "DOWN");
            status.put("error", e.getMessage());
            status.put("errorClass", e.getClass().getName());
        }
        
        return ResponseEntity.ok(status);
    }
    
    @GetMapping("/test-queries")
    public ResponseEntity<Map<String, Object>> testQueries() {
        Map<String, Object> results = new HashMap<>();
        
        // Test each analytics query
        Map<String, String> queryTests = new HashMap<>();
        
        try {
            employeeRepository.findTopEarnersByDepartmentRaw();
            queryTests.put("topEarners", "SUCCESS");
        } catch (Exception e) {
            queryTests.put("topEarners", "FAILED: " + e.getMessage());
        }
        
        try {
            employeeRepository.findEmployeeHierarchyRaw();
            queryTests.put("hierarchy", "SUCCESS");
        } catch (Exception e) {
            queryTests.put("hierarchy", "FAILED: " + e.getMessage());
        }
        
        try {
            employeeRepository.findDepartmentStatsRaw();
            queryTests.put("departmentStats", "SUCCESS");
        } catch (Exception e) {
            queryTests.put("departmentStats", "FAILED: " + e.getMessage());
        }
        
        results.put("queryTests", queryTests);
        return ResponseEntity.ok(results);
    }
    
    @GetMapping("/tables")
    public ResponseEntity<Map<String, Object>> checkTables() {
        Map<String, Object> results = new HashMap<>();
        
        try {
            // Check if we can query basic tables
            results.put("employees", employeeRepository.findAll().size());
            results.put("departments", departmentRepository.findAll().size());
            results.put("jobs", jobRepository.findAll().size());
            
            // Get sample data
            results.put("sampleEmployee", employeeRepository.findAll().stream().findFirst().orElse(null));
            results.put("sampleDepartment", departmentRepository.findAll().stream().findFirst().orElse(null));
            
        } catch (Exception e) {
            results.put("error", e.getMessage());
            results.put("stackTrace", e.getStackTrace());
        }
        
        return ResponseEntity.ok(results);
    }
}
