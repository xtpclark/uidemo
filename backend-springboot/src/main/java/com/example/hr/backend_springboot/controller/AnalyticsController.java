package com.example.hr.backend_springboot.controller;

import com.example.hr.backend_springboot.dto.*;
import com.example.hr.backend_springboot.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Controller
public class AnalyticsController {
    @Autowired
    private EmployeeRepository employeeRepository;

    @GetMapping("/analytics/top-earners")
    public String topEarners(Model model) {
        List<Object[]> rawResults = employeeRepository.findTopEarnersByDepartmentRaw();
        List<TopEarnerDTO> topEarners = new ArrayList<>();
        for (Object[] row : rawResults) {
            topEarners.add(new TopEarnerDTO(
                (String) row[0],
                (String) row[1],
                (BigDecimal) row[2],
                (BigDecimal) row[3],
                (BigDecimal) row[4]
            ));
        }
        model.addAttribute("topEarners", topEarners);
        return "analytics/top-earners";
    }

    @GetMapping("/analytics/employee-hierarchy")
    public String employeeHierarchy(Model model) {
        List<Object[]> rawResults = employeeRepository.findEmployeeHierarchyRaw();
        List<EmployeeHierarchyDTO> hierarchy = new ArrayList<>();
        for (Object[] row : rawResults) {
            hierarchy.add(new EmployeeHierarchyDTO(
                ((Number) row[0]).intValue(),
                (String) row[1],
                (String) row[2],
                (String) row[3],
                (BigDecimal) row[4]
            ));
        }
        model.addAttribute("hierarchy", hierarchy);
        return "analytics/employee-hierarchy";
    }

    @GetMapping("/analytics/department-stats")
    public String departmentStats(Model model) {
        List<Object[]> rawResults = employeeRepository.findDepartmentStatsRaw();
        List<DepartmentStatsDTO> stats = new ArrayList<>();
        for (Object[] row : rawResults) {
            stats.add(new DepartmentStatsDTO(
                (String) row[0],
                ((Number) row[1]).longValue(),
                (BigDecimal) row[2],
                (BigDecimal) row[3],
                (BigDecimal) row[4],
                (BigDecimal) row[5],
                (BigDecimal) row[6]
            ));
        }
        model.addAttribute("stats", stats);
        return "analytics/department-stats";
    }

    @GetMapping("/analytics/region-country")
    public String employeesByRegionAndCountry(Model model) {
        List<Object[]> rawResults = employeeRepository.findEmployeesByRegionAndCountryRaw();
        model.addAttribute("regionData", rawResults);
        return "analytics/region-country";
    }

    @GetMapping("/analytics/job-mobility")
    public String employeeJobMobility(Model model) {
        List<Object[]> rawResults = employeeRepository.findEmployeeJobMobilityRaw();
        model.addAttribute("mobilityData", rawResults);
        return "analytics/job-mobility";
    }

    @GetMapping("/analytics/manager-salary-impact")
    public String managerSalaryImpact(Model model) {
        List<Object[]> rawResults = employeeRepository.findManagerSalaryImpactRaw();
        model.addAttribute("managerData", rawResults);
        return "analytics/manager-salary-impact";
    }

    @GetMapping("/analytics/long-tenure-low-salary")
    public String longTenureLowSalary(Model model) {
        List<Object[]> rawResults = employeeRepository.findLongTenureLowSalaryRaw();
        model.addAttribute("tenureData", rawResults);
        return "analytics/long-tenure-low-salary";
    }

    @GetMapping("/analytics/promotion-patterns")
    public String promotionPatterns(Model model) {
        List<Object[]> rawResults = employeeRepository.findPromotionPatternsRaw();
        model.addAttribute("promotionData", rawResults);
        return "analytics/promotion-patterns";
    }

    @GetMapping("/analytics/overlapping-jobs")
    public String overlappingJobAssignments(Model model) {
        List<Object[]> rawResults = employeeRepository.findOverlappingJobAssignmentsRaw();
        model.addAttribute("overlapData", rawResults);
        return "analytics/overlapping-jobs";
    }

    @GetMapping("/analytics/job-roles-by-region")
    public String uniqueJobRolesByRegion(Model model) {
        List<Object[]> rawResults = employeeRepository.findUniqueJobRolesByRegionRaw();
        model.addAttribute("roleData", rawResults);
        return "analytics/job-roles-by-region";
    }

    @GetMapping("/analytics/employee-turnover")
    public String employeeTurnoverByYear(Model model) {
        List<Object[]> rawResults = employeeRepository.findEmployeeTurnoverByYearRaw();
        model.addAttribute("turnoverData", rawResults);
        return "analytics/employee-turnover";
    }

    @GetMapping("/analytics/hiring-trends")
    public String departmentalHiringTrends(Model model) {
        List<Object[]> rawResults = employeeRepository.findDepartmentalHiringTrendsRaw();
        model.addAttribute("hiringData", rawResults);
        return "analytics/hiring-trends";
    }

    @GetMapping("/analytics/salary-percentiles")
    public String salaryPercentileAnalysis(Model model) {
        List<Object[]> rawResults = employeeRepository.findSalaryPercentileAnalysisRaw();
        List<EmployeeSalaryPercentileDTO> percentiles = new ArrayList<>();
        for (Object[] row : rawResults) {
            percentiles.add(new EmployeeSalaryPercentileDTO(
                (String) row[0],
                (String) row[1],
                (BigDecimal) row[2],
                (BigDecimal) row[3]
            ));
        }
        model.addAttribute("percentiles", percentiles);
        return "analytics/salary-percentiles";
    }

    @GetMapping("/analytics/no-recent-changes")
    public String employeesWithoutRecentRoleChanges(Model model) {
        List<Object[]> rawResults = employeeRepository.findEmployeesWithoutRecentRoleChangesRaw();
        model.addAttribute("stagnantData", rawResults);
        return "analytics/no-recent-changes";
    }

    @GetMapping("/analytics/salary-budget")
    public String departmentalSalaryBudgetAllocation(Model model) {
        List<Object[]> rawResults = employeeRepository.findDepartmentalSalaryBudgetAllocationRaw();
        model.addAttribute("budgetData", rawResults);
        return "analytics/salary-budget";
    }

    @GetMapping("/analytics/department-path")
    public String recursiveDepartmentPath(Model model) {
        List<Object[]> rawResults = employeeRepository.findRecursiveDepartmentPathRaw();
        model.addAttribute("pathData", rawResults);
        return "analytics/department-path";
    }

    @GetMapping("/analytics/high-commission")
    public String highCommissionImpact(Model model) {
        List<Object[]> rawResults = employeeRepository.findHighCommissionImpactRaw();
        model.addAttribute("commissionData", rawResults);
        return "analytics/high-commission";
    }

    @GetMapping("/analytics/job-transitions")
    public String jobRoleTransitionMatrix(Model model) {
        List<Object[]> rawResults = employeeRepository.findJobRoleTransitionMatrixRaw();
        model.addAttribute("transitionData", rawResults);
        return "analytics/job-transitions";
    }

    @GetMapping("/analytics/diverse-job-roles")
    public String departmentsWithDiverseJobRoles(Model model) {
        List<Object[]> rawResults = employeeRepository.findDepartmentsWithDiverseJobRolesRaw();
        model.addAttribute("diverseData", rawResults);
        return "analytics/diverse-job-roles";
    }

    @GetMapping("/analytics/manager-workload")
    public String managerWorkload(Model model) {
        List<Object[]> rawResults = employeeRepository.findManagerWorkloadRaw();
        List<ManagerWorkloadDTO> workloads = new ArrayList<>();
        for (Object[] row : rawResults) {
            workloads.add(new ManagerWorkloadDTO(
                (String) row[0],
                (String) row[1],
                ((Number) row[2]).longValue(),
                (BigDecimal) row[3]
            ));
        }
        model.addAttribute("workloads", workloads);
        return "analytics/manager-workload";
    }

    @GetMapping("/analytics/dashboard")
    public String analyticsDashboard(Model model) {
        // Get summary data for dashboard
        List<Object[]> topEarners = employeeRepository.findTopEarnersByDepartmentRaw();
        List<Object[]> deptStats = employeeRepository.findDepartmentStatsRaw();
        List<Object[]> managerWorkload = employeeRepository.findManagerWorkloadRaw();
        
        model.addAttribute("topEarners", topEarners.subList(0, Math.min(5, topEarners.size())));
        model.addAttribute("deptStats", deptStats);
        model.addAttribute("managerWorkload", managerWorkload.subList(0, Math.min(3, managerWorkload.size())));
        
        return "analytics/dashboard";
    }
} 