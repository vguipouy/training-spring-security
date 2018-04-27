package com.accenture.accademy.spring.security.controllers;

import com.accenture.accademy.spring.security.model.Employee;
import com.accenture.accademy.spring.security.model.Project;
import com.accenture.accademy.spring.security.repositories.ProjectRepository;
import com.accenture.accademy.spring.security.services.EmployeeService;
import org.h2.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/employee")
public class EmployeeController {
    private final EmployeeService employeeService;
    private final ProjectRepository projectRepository;

    @Autowired
    public EmployeeController(EmployeeService employeeService, ProjectRepository projectRepository) {
        this.employeeService = employeeService;
        this.projectRepository = projectRepository;
    }

    @GetMapping("/create")
    public String create(Model model) {
        return display(model, null, "edit");
    }

    @GetMapping("/read/{id}")
    public String read(Model model, @PathVariable String id) {
        return display(model, id, "read");
    }

    @GetMapping("/update/{id}")
    public String update(Model model, @PathVariable String id) {
        return display(model, id, "edit");
    }

    @RequestMapping("/delete/{id}")
    public String delete(Model model, @PathVariable String id) {
        Optional<Employee> employee = employeeService.get(id);
        if (employee.isPresent()) {
            employeeService.delete(employee.get());
        }

        return "redirect:/";
    }

    @PostMapping("/save")
    public String update(Model model, @Valid @ModelAttribute("employee") Employee employee, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "employee";
        }

        Employee savedEmployee = employeeService.save(employee);
        model.addAttribute("employee", savedEmployee);

        return "redirect:/employee/read/" + savedEmployee.getId();
    }

    private String display(Model model, String id, String mode) {
        if (mode != null && mode.equals("edit")) {
            model.addAttribute("mode", "edit");
        } else {
            model.addAttribute("mode", "read");
        }

        Iterable<Project> projects = projectRepository.findAll();
        model.addAttribute("projects", projects);

        Employee employee = null;
        if (StringUtils.isNullOrEmpty(id)) {
            employee = new Employee();
        } else {
            Optional<Employee> foundEmployee = employeeService.get(id);
            if (foundEmployee.isPresent()) {
                employee = foundEmployee.get();
            }
        }

        if (employee != null) {
            model.addAttribute("employee", employee);
            return "employee";
        } else {
            return "redirect:/";
        }
    }
}
