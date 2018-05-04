package com.accenture.accademy.spring.security.controllers;

import com.accenture.accademy.spring.security.model.Employee;
import com.accenture.accademy.spring.security.services.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Home page controller providing the main view.
 */
// Require the user to have the role USER for any call of a method of the controller
@Controller
public class HomeController {
    private final EmployeeService employeeService;

    /**
     * Constructor injection of employee service.
     *
     * @param employeeService Employee service providing the list of employees
     */
    @Autowired
    public HomeController(EmployeeService employeeService){
        this.employeeService = employeeService;
    }

    @GetMapping("/")
    public String home(Model model) {
        // Add list of employees as a model variable to enable to print them on the page
        Iterable<Employee> employees = employeeService.findAll();
        model.addAttribute("employees", employees);

        // Render home.html template
        return "home";
    }
}
