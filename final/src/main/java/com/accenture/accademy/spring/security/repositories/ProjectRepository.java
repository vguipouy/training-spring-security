package com.accenture.accademy.spring.security.repositories;

import com.accenture.accademy.spring.security.model.Employee;
import com.accenture.accademy.spring.security.model.Project;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ProjectRepository extends CrudRepository<Project, String> {
    /**
     * Find all projects having a given lead.
     */
    List<Project> findByLead(Employee employee);
}