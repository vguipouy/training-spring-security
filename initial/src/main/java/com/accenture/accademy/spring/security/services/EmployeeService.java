package com.accenture.accademy.spring.security.services;

import com.accenture.accademy.spring.security.model.Employee;
import com.accenture.accademy.spring.security.model.Project;
import com.accenture.accademy.spring.security.repositories.EmployeeRepository;
import com.accenture.accademy.spring.security.repositories.ProjectRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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

    /**
     * Constructor.
     *
     * @param repository        Injected employee repository
     * @param projectRepository Injected project repository
     */
    @Autowired
    public EmployeeService(EmployeeRepository repository, ProjectRepository projectRepository) {
        this.repository = repository;
        this.projectRepository = projectRepository;
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
    public Employee save(Employee employee) {
        LOGGER.info("Saving employee {}", employee.toString());
        return repository.save(employee);
    }

    /**
     * Delete an existing employee.
     *
     * @param employee Employee to delete.
     */
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
}
