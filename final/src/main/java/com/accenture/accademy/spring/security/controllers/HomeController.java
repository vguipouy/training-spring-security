package com.accenture.accademy.spring.security.controllers;

import com.accenture.accademy.spring.security.model.Employee;
import com.accenture.accademy.spring.security.services.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class HomeController {
    private final EmployeeService employeeService;

    @Autowired
    public HomeController(EmployeeService employeeService){
        this.employeeService = employeeService;
    }

    @GetMapping("/")
    public String home(Model model) {
        Iterable<Employee> employees = employeeService.findAll();
        model.addAttribute("employees", employees);

        return "home";
    }
}
