package com.accenture.accademy.spring.security;

import com.accenture.accademy.spring.security.model.Employee;
import com.accenture.accademy.spring.security.model.Project;
import com.accenture.accademy.spring.security.repositories.EmployeeRepository;
import com.accenture.accademy.spring.security.repositories.ProjectRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Bean
    public CommandLineRunner initialize(EmployeeRepository employeeRepository, ProjectRepository projectRepository) {
        return (args) -> {
            // Initialize database if empty
            if (employeeRepository.count() == 0) {
                Project project1 = new Project("catscorp", "Cats corporation");
                project1 = projectRepository.save(project1);

                Project project2 = new Project("parrotscompany", "Parrots company");
                project2 = projectRepository.save(project2);

                Employee employee1 = new Employee("john.doe", "John Doe", project1.getId());
                employee1 = employeeRepository.save(employee1);
                Employee employee2 = new Employee("jahn.dae", "Jahn Dae", project1.getId());
                employee2 = employeeRepository.save(employee2);
                Employee employee3 = new Employee("jehn.dee", "Jehn Dee", project1.getId());
                employee3 = employeeRepository.save(employee3);

                Employee employee4 = new Employee("jihn.die", "Jihn Die", project2.getId());
                employee4 = employeeRepository.save(employee4);
                Employee employee5 = new Employee("juhn.due", "Juhn Due", project2.getId());
                employee5 = employeeRepository.save(employee5);
                Employee employee6 = new Employee("jyhn.dye", "Jyhn Dye", project2.getId());
                employee6 = employeeRepository.save(employee6);

                project1.setLead(employee1);
                project1 = projectRepository.save(project1);

                project2.setLead(employee4);
                project2 = projectRepository.save(project2);
            }

        };
    }
}
