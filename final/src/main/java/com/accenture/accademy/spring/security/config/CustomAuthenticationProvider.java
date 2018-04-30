package com.accenture.accademy.spring.security.config;

import com.accenture.accademy.spring.security.model.Employee;
import com.accenture.accademy.spring.security.services.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.Optional;

//@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {
    private EmployeeService employeeService;
    private PasswordEncoder encoder;

    @Autowired
    public CustomAuthenticationProvider(EmployeeService employeeService, PasswordEncoder encoder) {
        this.employeeService = employeeService;
        this.encoder = encoder;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {

        String id = authentication.getName();
        String password = authentication.getCredentials().toString();

        Optional<Employee> found = employeeService.get(id);
        if (found.isPresent()) {
            Employee employee = found.get();
            String encodedPassword = employee.getPassword();
            if (encoder.matches(password, encodedPassword)) {
                return new UsernamePasswordAuthenticationToken(
                        id, encodedPassword, new ArrayList<>());
            }
        }

        throw new BadCredentialsException("Invalid credentials");
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(
                UsernamePasswordAuthenticationToken.class);
    }
}