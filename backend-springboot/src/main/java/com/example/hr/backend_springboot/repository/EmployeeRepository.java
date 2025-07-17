package com.example.hr.backend_springboot.repository;

import com.example.hr.backend_springboot.model.Employee;
import com.example.hr.backend_springboot.dto.TopEarnerDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    
    // Query 1: Top Earners by Department with Commission Impact
    @Query(value = "WITH EmployeeCompensation AS (\n" +
            "    SELECT \n" +
            "        e.employee_id,\n" +
            "        e.first_name || ' ' || e.last_name AS employee_name,\n" +
            "        d.department_name,\n" +
            "        e.salary,\n" +
            "        COALESCE(e.commission_pct, 0) AS commission_pct,\n" +
            "        e.salary * (1 + COALESCE(e.commission_pct, 0)) AS total_compensation,\n" +
            "        ROW_NUMBER() OVER (PARTITION BY e.department_id ORDER BY e.salary * (1 + COALESCE(e.commission_pct, 0)) DESC) AS rank\n" +
            "    FROM employees e\n" +
            "    JOIN departments d ON e.department_id = d.department_id\n" +
            "    WHERE e.department_id IS NOT NULL\n" +
            ")\n" +
            "SELECT \n" +
            "    department_name,\n" +
            "    employee_name,\n" +
            "    salary,\n" +
            "    commission_pct,\n" +
            "    total_compensation\n" +
            "FROM EmployeeCompensation\n" +
            "WHERE rank <= 3\n" +
            "ORDER BY department_name, total_compensation DESC", 
            nativeQuery = true)
    List<Object[]> findTopEarnersByDepartmentRaw();

    // Query 2: Manager-Employee Reporting Structure
    @Query(value = "SELECT \n" +
            "    LEVEL AS hierarchy_level,\n" +
            "    LPAD(' ', 2 * (LEVEL - 1)) || first_name || ' ' || last_name AS employee_name,\n" +
            "    job_title,\n" +
            "    department_name,\n" +
            "    salary\n" +
            "FROM employees e\n" +
            "JOIN jobs j ON e.job_id = j.job_id\n" +
            "JOIN departments d ON e.department_id = d.department_id\n" +
            "START WITH e.manager_id IS NULL\n" +
            "CONNECT BY PRIOR e.employee_id = e.manager_id\n" +
            "ORDER SIBLINGS BY e.last_name, e.first_name",
            nativeQuery = true)
    List<Object[]> findEmployeeHierarchyRaw();

    // Query 3: Departmental Salary Statistics
    @Query(value = "WITH DeptStats AS (\n" +
            "    SELECT \n" +
            "        d.department_name,\n" +
            "        COUNT(e.employee_id) AS employee_count,\n" +
            "        AVG(e.salary) AS avg_salary,\n" +
            "        MEDIAN(e.salary) AS median_salary,\n" +
            "        MIN(e.salary) AS min_salary,\n" +
            "        MAX(e.salary) AS max_salary,\n" +
            "        STDDEV(e.salary) AS stddev_salary\n" +
            "    FROM employees e\n" +
            "    JOIN departments d ON e.department_id = d.department_id\n" +
            "    GROUP BY d.department_name\n" +
            "    HAVING COUNT(e.employee_id) >= 5\n" +
            ")\n" +
            "SELECT \n" +
            "    department_name,\n" +
            "    employee_count,\n" +
            "    ROUND(avg_salary, 2) AS avg_salary,\n" +
            "    ROUND(median_salary, 2) AS median_salary,\n" +
            "    min_salary,\n" +
            "    max_salary,\n" +
            "    ROUND(stddev_salary, 2) AS stddev_salary\n" +
            "FROM DeptStats\n" +
            "ORDER BY avg_salary DESC",
            nativeQuery = true)
    List<Object[]> findDepartmentStatsRaw();

    // Query 4: Employees by Region and Country
    @Query(value = "SELECT \n" +
            "    r.region_name,\n" +
            "    c.country_name,\n" +
            "    COUNT(e.employee_id) AS employee_count,\n" +
            "    ROUND(SUM(e.salary), 2) AS total_salary,\n" +
            "    ROUND(AVG(e.salary), 2) AS avg_salary_per_employee\n" +
            "FROM employees e\n" +
            "JOIN departments d ON e.department_id = d.department_id\n" +
            "JOIN locations l ON d.location_id = l.location_id\n" +
            "JOIN countries c ON l.country_id = c.country_id\n" +
            "JOIN regions r ON c.region_id = r.region_id\n" +
            "GROUP BY ROLLUP(r.region_name, c.country_name)\n" +
            "ORDER BY r.region_name, c.country_name",
            nativeQuery = true)
    List<Object[]> findEmployeesByRegionAndCountryRaw();

    // Query 5: Employee Job Mobility
    @Query(value = "WITH JobTenure AS (\n" +
            "    SELECT \n" +
            "        e.employee_id,\n" +
            "        e.first_name || ' ' || e.last_name AS employee_name,\n" +
            "        COUNT(jh.job_id) AS role_count,\n" +
            "        AVG(MONTHS_BETWEEN(jh.end_date, jh.start_date)) AS avg_months_per_role\n" +
            "    FROM employees e\n" +
            "    JOIN job_history jh ON e.employee_id = jh.employee_id\n" +
            "    GROUP BY e.employee_id, e.first_name, e.last_name\n" +
            "    HAVING COUNT(jh.job_id) > 1\n" +
            ")\n" +
            "SELECT \n" +
            "    employee_name,\n" +
            "    role_count,\n" +
            "    ROUND(avg_months_per_role, 2) AS avg_months_per_role,\n" +
            "    LISTAGG(j.job_title, ', ') WITHIN GROUP (ORDER BY jh.start_date) AS job_titles\n" +
            "FROM JobTenure jt\n" +
            "JOIN job_history jh ON jt.employee_id = jh.employee_id\n" +
            "JOIN jobs j ON jh.job_id = j.job_id\n" +
            "GROUP BY employee_name, role_count, avg_months_per_role\n" +
            "ORDER BY role_count DESC, avg_months_per_role",
            nativeQuery = true)
    List<Object[]> findEmployeeJobMobilityRaw();

    // Query 6: Employee Salary Growth Under Managers
    @Query(value = "WITH ManagerStats AS (\n" +
            "    SELECT \n" +
            "        m.employee_id AS manager_id,\n" +
            "        m.first_name || ' ' || m.last_name AS manager_name,\n" +
            "        d.department_name,\n" +
            "        AVG(e.salary) AS avg_team_salary,\n" +
            "        (SELECT AVG(salary) FROM employees WHERE department_id = d.department_id) AS dept_avg_salary\n" +
            "    FROM employees e\n" +
            "    JOIN employees m ON e.manager_id = m.employee_id\n" +
            "    JOIN departments d ON e.department_id = d.department_id\n" +
            "    GROUP BY m.employee_id, m.first_name, m.last_name, d.department_name, d.department_id\n" +
            ")\n" +
            "SELECT \n" +
            "    manager_name,\n" +
            "    department_name,\n" +
            "    ROUND(avg_team_salary, 2) AS avg_team_salary,\n" +
            "    ROUND(dept_avg_salary, 2) AS dept_avg_salary,\n" +
            "    ROUND(((avg_team_salary - dept_avg_salary) / dept_avg_salary * 100), 2) AS pct_diff\n" +
            "FROM ManagerStats\n" +
            "WHERE avg_team_salary IS NOT NULL\n" +
            "ORDER BY department_name, pct_diff DESC",
            nativeQuery = true)
    List<Object[]> findManagerSalaryImpactRaw();

    // Query 7: Employees with Long Tenure and Low Salary
    @Query(value = "WITH JobMedian AS (\n" +
            "    SELECT \n" +
            "        job_id,\n" +
            "        MEDIAN(salary) AS median_salary\n" +
            "    FROM employees\n" +
            "    GROUP BY job_id\n" +
            ")\n" +
            "SELECT \n" +
            "    e.employee_id,\n" +
            "    e.first_name || ' ' || e.last_name AS employee_name,\n" +
            "    j.job_title,\n" +
            "    e.salary,\n" +
            "    jm.median_salary,\n" +
            "    ROUND(MONTHS_BETWEEN(SYSDATE, e.hire_date) / 12, 2) AS years_of_service\n" +
            "FROM employees e\n" +
            "JOIN jobs j ON e.job_id = j.job_id\n" +
            "JOIN JobMedian jm ON e.job_id = jm.job_id\n" +
            "WHERE MONTHS_BETWEEN(SYSDATE, e.hire_date) / 12 > 5\n" +
            "AND e.salary <= jm.median_salary\n" +
            "ORDER BY years_of_service DESC, salary",
            nativeQuery = true)
    List<Object[]> findLongTenureLowSalaryRaw();

    // Query 8: Employee Promotion Patterns
    @Query(value = "WITH JobChanges AS (\n" +
            "    SELECT \n" +
            "        jh.employee_id,\n" +
            "        e.first_name || ' ' || e.last_name AS employee_name,\n" +
            "        jh.job_id AS new_job_id,\n" +
            "        j.job_title AS new_job_title,\n" +
            "        LAG(jh.job_id) OVER (PARTITION BY jh.employee_id ORDER BY jh.start_date) AS prev_job_id,\n" +
            "        LAG(j.job_title) OVER (PARTITION BY jh.employee_id ORDER BY jh.start_date) AS prev_job_title,\n" +
            "        j.max_salary AS new_max_salary,\n" +
            "        LAG(j.max_salary) OVER (PARTITION BY jh.employee_id ORDER BY jh.start_date) AS prev_max_salary\n" +
            "    FROM job_history jh\n" +
            "    JOIN employees e ON jh.employee_id = e.employee_id\n" +
            "    JOIN jobs j ON jh.job_id = j.job_id\n" +
            ")\n" +
            "SELECT \n" +
            "    employee_name,\n" +
            "    prev_job_title,\n" +
            "    new_job_title,\n" +
            "    prev_max_salary,\n" +
            "    new_max_salary,\n" +
            "    ROUND(((new_max_salary - prev_max_salary) / prev_max_salary * 100), 2) AS salary_increase_pct\n" +
            "FROM JobChanges\n" +
            "WHERE prev_job_id IS NOT NULL\n" +
            "AND new_max_salary > prev_max_salary\n" +
            "ORDER BY salary_increase_pct DESC",
            nativeQuery = true)
    List<Object[]> findPromotionPatternsRaw();

    // Query 9: Overlapping Job Assignments
    @Query(value = "SELECT \n" +
            "    e.employee_id,\n" +
            "    e.first_name || ' ' || e.last_name AS employee_name,\n" +
            "    jh1.job_id AS job1_id,\n" +
            "    j1.job_title AS job1_title,\n" +
            "    jh1.start_date AS job1_start,\n" +
            "    jh1.end_date AS job1_end,\n" +
            "    jh2.job_id AS job2_id,\n" +
            "    j2.job_title AS job2_title,\n" +
            "    jh2.start_date AS job2_start,\n" +
            "    jh2.end_date AS job2_end\n" +
            "FROM job_history jh1\n" +
            "JOIN job_history jh2 ON jh1.employee_id = jh2.employee_id\n" +
            "JOIN employees e ON jh1.employee_id = e.employee_id\n" +
            "JOIN jobs j1 ON jh1.job_id = j1.job_id\n" +
            "JOIN jobs j2 ON jh2.job_id = j2.job_id\n" +
            "WHERE jh1.start_date < jh2.start_date\n" +
            "AND jh1.end_date >= jh2.start_date\n" +
            "AND jh1.start_date != jh2.start_date",
            nativeQuery = true)
    List<Object[]> findOverlappingJobAssignmentsRaw();

    // Query 10: Unique Job Roles Across Regions
    @Query(value = "WITH CurrentJobs AS (\n" +
            "    SELECT DISTINCT \n" +
            "        r.region_name,\n" +
            "        j.job_title\n" +
            "    FROM employees e\n" +
            "    JOIN departments d ON e.department_id = d.department_id\n" +
            "    JOIN locations l ON d.location_id = l.location_id\n" +
            "    JOIN countries c ON l.country_id = c.country_id\n" +
            "    JOIN regions r ON c.region_id = r.region_id\n" +
            "    JOIN jobs j ON e.job_id = j.job_id\n" +
            "    WHERE e.department_id IS NOT NULL\n" +
            "),\n" +
            "HistoricalJobs AS (\n" +
            "    SELECT DISTINCT \n" +
            "        r.region_name,\n" +
            "        j.job_title\n" +
            "    FROM job_history jh\n" +
            "    JOIN employees e ON jh.employee_id = e.employee_id\n" +
            "    JOIN departments d ON jh.department_id = d.department_id\n" +
            "    JOIN locations l ON d.location_id = l.location_id\n" +
            "    JOIN countries c ON l.country_id = c.country_id\n" +
            "    JOIN regions r ON c.region_id = r.region_id\n" +
            "    JOIN jobs j ON jh.job_id = j.job_id\n" +
            ")\n" +
            "SELECT region_name, job_title\n" +
            "FROM CurrentJobs\n" +
            "UNION\n" +
            "SELECT region_name, job_title\n" +
            "FROM HistoricalJobs\n" +
            "ORDER BY region_name, job_title",
            nativeQuery = true)
    List<Object[]> findUniqueJobRolesByRegionRaw();

    // Query 11: Employee Turnover by Year
    @Query(value = "SELECT \n" +
            "    EXTRACT(YEAR FROM end_date) AS turnover_year,\n" +
            "    COUNT(*) AS turnover_count\n" +
            "FROM job_history\n" +
            "GROUP BY EXTRACT(YEAR FROM end_date)\n" +
            "ORDER BY turnover_year",
            nativeQuery = true)
    List<Object[]> findEmployeeTurnoverByYearRaw();

    // Query 12: Departmental Hiring Trends
    @Query(value = "SELECT \n" +
            "    d.department_name,\n" +
            "    EXTRACT(YEAR FROM e.hire_date) AS hire_year,\n" +
            "    COUNT(e.employee_id) AS hire_count\n" +
            "FROM employees e\n" +
            "JOIN departments d ON e.department_id = d.department_id\n" +
            "GROUP BY d.department_name, EXTRACT(YEAR FROM e.hire_date)\n" +
            "ORDER BY d.department_name, hire_year",
            nativeQuery = true)
    List<Object[]> findDepartmentalHiringTrendsRaw();

    // Query 13: Salary Percentile Analysis
    @Query(value = "SELECT \n" +
            "    d.department_name,\n" +
            "    e.first_name || ' ' || e.last_name AS employee_name,\n" +
            "    e.salary,\n" +
            "    ROUND(PERCENT_RANK() OVER (PARTITION BY e.department_id ORDER BY e.salary) * 100, 2) AS salary_percentile\n" +
            "FROM employees e\n" +
            "JOIN departments d ON e.department_id = d.department_id\n" +
            "WHERE e.department_id IS NOT NULL\n" +
            "ORDER BY d.department_name, salary_percentile DESC",
            nativeQuery = true)
    List<Object[]> findSalaryPercentileAnalysisRaw();

    // Query 14: Employees Without Recent Role Changes
    @Query(value = "SELECT \n" +
            "    e.employee_id,\n" +
            "    e.first_name || ' ' || e.last_name AS employee_name,\n" +
            "    j.job_title,\n" +
            "    e.hire_date,\n" +
            "    ROUND(MONTHS_BETWEEN(SYSDATE, e.hire_date) / 12, 2) AS years_of_service\n" +
            "FROM employees e\n" +
            "JOIN jobs j ON e.job_id = j.job_id\n" +
            "LEFT JOIN job_history jh ON e.employee_id = jh.employee_id\n" +
            "WHERE jh.employee_id IS NULL\n" +
            "AND MONTHS_BETWEEN(SYSDATE, e.hire_date) / 12 > 5\n" +
            "ORDER BY years_of_service DESC",
            nativeQuery = true)
    List<Object[]> findEmployeesWithoutRecentRoleChangesRaw();

    // Query 15: Departmental Salary Budget Allocation
    @Query(value = "SELECT *\n" +
            "FROM (\n" +
            "    SELECT \n" +
            "        r.region_name,\n" +
            "        d.department_name,\n" +
            "        SUM(e.salary) AS total_salary\n" +
            "    FROM employees e\n" +
            "    JOIN departments d ON e.department_id = d.department_id\n" +
            "    JOIN locations l ON d.location_id = l.location_id\n" +
            "    JOIN countries c ON l.country_id = c.country_id\n" +
            "    JOIN regions r ON c.region_id = r.region_id\n" +
            "    GROUP BY r.region_name, d.department_name\n" +
            ")\n" +
            "PIVOT (\n" +
            "    SUM(total_salary)\n" +
            "    FOR department_name IN (\n" +
            "        'Executive' AS Executive,\n" +
            "        'Sales' AS Sales,\n" +
            "        'Finance' AS Finance\n" +
            "    )\n" +
            ")\n" +
            "ORDER BY region_name",
            nativeQuery = true)
    List<Object[]> findDepartmentalSalaryBudgetAllocationRaw();

    // Query 16: Recursive Department Path
    @Query(value = "WITH LocationPath (department_id, department_name, location_path, location_id, country_id, region_id, recursion_level) AS (\n" +
            "    SELECT \n" +
            "        d.department_id,\n" +
            "        d.department_name,\n" +
            "        l.city AS location_path,\n" +
            "        l.location_id,\n" +
            "        l.country_id,\n" +
            "        c.region_id,\n" +
            "        1 AS recursion_level\n" +
            "    FROM departments d\n" +
            "    JOIN locations l ON d.location_id = l.location_id\n" +
            "    JOIN countries c ON l.country_id = c.country_id\n" +
            "    UNION ALL\n" +
            "    SELECT \n" +
            "        lp.department_id,\n" +
            "        lp.department_name,\n" +
            "        CASE \n" +
            "            WHEN lp.recursion_level = 1 THEN c.country_name || ' → ' || lp.location_path\n" +
            "            WHEN lp.recursion_level = 2 THEN r.region_name || ' → ' || lp.location_path\n" +
            "        END AS location_path,\n" +
            "        lp.location_id,\n" +
            "        lp.country_id,\n" +
            "        lp.region_id,\n" +
            "        lp.recursion_level + 1\n" +
            "    FROM LocationPath lp\n" +
            "    LEFT JOIN countries c ON lp.country_id = c.country_id AND lp.recursion_level = 1\n" +
            "    LEFT JOIN regions r ON lp.region_id = r.region_id AND lp.recursion_level = 2\n" +
            "    WHERE lp.recursion_level <= 2\n" +
            ")\n" +
            "SELECT \n" +
            "    department_name,\n" +
            "    location_path\n" +
            "FROM LocationPath\n" +
            "WHERE recursion_level = 3\n" +
            "  AND (location_path LIKE 'Americas%' OR location_path LIKE 'Europe%')\n" +
            "ORDER BY department_name, location_path",
            nativeQuery = true)
    List<Object[]> findRecursiveDepartmentPathRaw();

    // Query 17: Employees with High Commission Impact
    @Query(value = "SELECT \n" +
            "    e.first_name || ' ' || e.last_name AS employee_name,\n" +
            "    j.job_title,\n" +
            "    e.salary,\n" +
            "    e.commission_pct,\n" +
            "    e.salary * e.commission_pct AS commission_amount,\n" +
            "    ROUND((e.salary * e.commission_pct / e.salary * 100), 2) AS commission_percentage\n" +
            "FROM employees e\n" +
            "JOIN jobs j ON e.job_id = j.job_id\n" +
            "WHERE e.commission_pct IS NOT NULL\n" +
            "AND e.salary * e.commission_pct >= 0.3 * e.salary\n" +
            "ORDER BY commission_percentage DESC",
            nativeQuery = true)
    List<Object[]> findHighCommissionImpactRaw();

    // Query 18: Job Role Transition Matrix
    @Query(value = "WITH JobTransitions AS (\n" +
            "    SELECT \n" +
            "        jh.employee_id,\n" +
            "        LAG(jh.job_id) OVER (PARTITION BY jh.employee_id ORDER BY jh.start_date) AS from_job_id,\n" +
            "        jh.job_id AS to_job_id\n" +
            "    FROM job_history jh\n" +
            ")\n" +
            "SELECT \n" +
            "    j1.job_title AS from_job,\n" +
            "    j2.job_title AS to_job,\n" +
            "    COUNT(*) AS transition_count\n" +
            "FROM JobTransitions jt\n" +
            "JOIN jobs j1 ON jt.from_job_id = j1.job_id\n" +
            "JOIN jobs j2 ON jt.to_job_id = j2.job_id\n" +
            "WHERE jt.from_job_id IS NOT NULL\n" +
            "GROUP BY j1.job_title, j2.job_title\n" +
            "ORDER BY transition_count DESC",
            nativeQuery = true)
    List<Object[]> findJobRoleTransitionMatrixRaw();

    // Query 19: Departments with Diverse Job Roles
    @Query(value = "SELECT \n" +
            "    d.department_name,\n" +
            "    COUNT(DISTINCT e.job_id) AS unique_job_count,\n" +
            "    LISTAGG(j.job_title, ', ') WITHIN GROUP (ORDER BY j.job_title) AS job_titles\n" +
            "FROM employees e\n" +
            "JOIN departments d ON e.department_id = d.department_id\n" +
            "JOIN jobs j ON e.job_id = j.job_id\n" +
            "GROUP BY d.department_name\n" +
            "HAVING COUNT(DISTINCT e.job_id) > 1\n" +
            "ORDER BY unique_job_count DESC",
            nativeQuery = true)
    List<Object[]> findDepartmentsWithDiverseJobRolesRaw();

    // Query 20: Employee Workload by Manager
    @Query(value = "SELECT \n" +
            "    m.first_name || ' ' || m.last_name AS manager_name,\n" +
            "    d.department_name,\n" +
            "    (SELECT COUNT(*) \n" +
            "     FROM employees e \n" +
            "     WHERE e.manager_id = m.employee_id) AS direct_reports,\n" +
            "    (SELECT SUM(salary) \n" +
            "     FROM employees e \n" +
            "     WHERE e.manager_id = m.employee_id) AS total_team_salary\n" +
            "FROM employees m\n" +
            "JOIN departments d ON m.department_id = d.department_id\n" +
            "WHERE EXISTS (\n" +
            "    SELECT 1 \n" +
            "    FROM employees e \n" +
            "    WHERE e.manager_id = m.employee_id\n" +
            ")\n" +
            "ORDER BY direct_reports DESC, total_team_salary DESC",
            nativeQuery = true)
    List<Object[]> findManagerWorkloadRaw();
} 