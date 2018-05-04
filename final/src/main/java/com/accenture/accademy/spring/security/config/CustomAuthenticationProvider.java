package com.accenture.accademy.spring.security.config;

import com.accenture.accademy.spring.security.model.Employee;
import com.accenture.accademy.spring.security.model.Project;
import com.accenture.accademy.spring.security.repositories.ProjectRepository;
import com.accenture.accademy.spring.security.services.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Define a custom AuthenticationProvider using EmployeeService.
 */
@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {
    private final EmployeeService employeeService;
    private final ProjectRepository projectRepository;
    private final PasswordEncoder encoder;

    /**
     * Constructor injection of service and password encoder.
     *
     * @param employeeService Service providing employees
     * @param encoder         Password encoder
     */
    @Autowired
    public CustomAuthenticationProvider(EmployeeService employeeService, ProjectRepository projectRepository, PasswordEncoder encoder) {
        this.employeeService = employeeService;
        this.projectRepository = projectRepository;
        this.encoder = encoder;
    }

    /**
     * Method performing the authentication.
     *
     * @param authentication Received authentication including username and password from the form.
     * @return Validated UsernamePasswordAuthenticationToken extending Authentication if authentication is successfull
     * @throws AuthenticationException when authentication is failed
     */
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {

        String id = authentication.getName();
        String password = authentication.getCredentials().toString();

        Optional<Employee> found = employeeService.get(id);
        if (found.isPresent()) {
            Employee employee = found.get();

            // Get employee's roles
            List authorities = getAuthorities(employee);

            // Check password
            String encodedPassword = employee.getPassword();
            if (encoder.matches(password, encodedPassword)) {
                // Return the valid authentication that will be stored in SecurityContext
                return new UsernamePasswordAuthenticationToken(
                        id, encodedPassword, authorities);
            }
        }

        throw new BadCredentialsException("Invalid credentials");
    }

    /**
     * Get the roles of the employee.
     * If the employee leads a project, he will get USER and ADMIN role.
     * otherwise, he will get only USER role
     *
     * @param employee
     * @return Granted authorities (roles)
     */
    private List<GrantedAuthority> getAuthorities(Employee employee) {
        List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
        boolean isLead = false;

        List<Project> leadProjects = projectRepository.findByLead(employee);
        isLead = !leadProjects.isEmpty();

        grantedAuthorities.add(new SimpleGrantedAuthority("ROLE_USER"));
        if (isLead) {
            grantedAuthorities.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
        }

        return grantedAuthorities;
    }

    /**
     * Support method enabling authentication manager to pick the right AuthenticationProvider depending of the Authentication class to process.
     *
     * @param authentication Authentication subclass class
     * @return true if the provider can handle the authentication type, false otherwise
     */
    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(
                UsernamePasswordAuthenticationToken.class);
    }
}