package com.accenture.accademy.spring.security.repositories;

import com.accenture.accademy.spring.security.model.Employee;
import com.accenture.accademy.spring.security.model.Project;
import org.springframework.data.repository.CrudRepository;

public interface EmployeeRepository extends CrudRepository<Employee, String> {
    
}