package com.accenture.accademy.spring.security.services;

import com.accenture.accademy.spring.security.model.Employee;
import com.accenture.accademy.spring.security.model.Project;
import com.accenture.accademy.spring.security.repositories.EmployeeRepository;
import com.accenture.accademy.spring.security.repositories.ProjectRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Service managing employees.
 */
@Service
public class EmployeeService {
    private static Logger LOGGER = LoggerFactory.getLogger(EmployeeService.class);

    private final EmployeeRepository repository;
    private final ProjectRepository projectRepository;
    private final JdbcTemplate jdbcTemplate;


    /**
     * Constructor.
     *
     * @param repository        Injected employee repository
     * @param projectRepository Injected project repository
     * @param jdbcTemplate      JDBC template to perform SQL queries
     */
    @Autowired
    public EmployeeService(EmployeeRepository repository, ProjectRepository projectRepository, JdbcTemplate jdbcTemplate) {
        this.repository = repository;
        this.projectRepository = projectRepository;
        this.jdbcTemplate = jdbcTemplate;
    }

    /**
     * Fetch an employee by its identifier.
     *
     * @param id Identifier to search
     * @return Optional employee
     */
    public Optional<Employee> get(String id) {
        LOGGER.info("Fetching employee {}", id);
        return repository.findById(id);
    }

    /**
     * Fetch all employees.
     *
     * @return All existing employees.
     */
    public Iterable<Employee> findAll() {
        LOGGER.info("Fetching all employees");
        return repository.findAll();
    }

    /**
     * Set password for an employee identified by its id.
     *
     * @param id       Identifier of the employee whose password has to be changed
     * @param password Password to set (unencoded)
     */
    public void setPassword(String id, String password) {
        LOGGER.info("Setting password for employee {}", id);

        Optional<Employee> fetchedEmployee = get(id);
        if (fetchedEmployee.isPresent()) {
            Employee employee = fetchedEmployee.get();

            employee.setPassword(password);
            repository.save(employee);

            LOGGER.info("Password changed for employee {}", id);
        }
    }

    /**
     * Save an existing employee.
     *
     * @param employee Employee to save.
     */
    // Exemple of usage of @Secured
    @Secured("ROLE_ADMIN")
    public Employee save(Employee employee) {
        LOGGER.info("Saving employee {}", employee.toString());
        return repository.save(employee);
    }

    /**
     * Delete an existing employee.
     *
     * @param employee Employee to delete.
     */
    // Exemple of usage of @PreAuthorize  equivalent to @Secured("ROLE_ADMIN")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public void delete(Employee employee) {
        LOGGER.info("Deleting employee {}", employee.toString());

        List<Project> leadProjects = projectRepository.findByLead(employee);
        leadProjects.forEach(p -> {
                    p.setLead(null);
                    projectRepository.save(p);
                }
        );

        repository.delete(employee);
    }

    /**
     * Count employees having a specific name.
     * Demonstration of SQL injection.
     * Try with name :
     * toto' or 1=1
     *
     * @return Number of employees
     */
    public Integer countByName(String name) {
        StringBuilder queryBuilder = new StringBuilder();
        queryBuilder.append("SELECT count(*) from EMPLOYEE WHERE name = '");
        queryBuilder.append(name);
        queryBuilder.append("'");

        return jdbcTemplate.queryForObject(queryBuilder.toString(), Integer.class);
    }
}
