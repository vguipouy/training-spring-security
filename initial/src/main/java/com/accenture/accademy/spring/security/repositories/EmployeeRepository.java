package com.accenture.accademy.spring.security.repositories;

import com.accenture.accademy.spring.security.model.Employee;
import org.springframework.data.repository.CrudRepository;

/**
 * Repository managing Employees persistence with Spring Data.
 * @see CrudRepository
 */
public interface EmployeeRepository extends CrudRepository<Employee, String> {
    
}