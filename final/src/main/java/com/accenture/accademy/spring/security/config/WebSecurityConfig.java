package com.accenture.accademy.spring.security.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

// Enam
@EnableWebSecurity
// Enable annotation based access control
// Allow the usage of @PreAuthorize, @PostAuthorize, @PreFilter, @PostFilter, @Secured
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    /**
     * Global http security configuration.
     * @param http HTTP security configuration
     * @throws Exception thrown when a configuration error occurs
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
            .authorizeRequests()
            .antMatchers("/employee").hasRole("USER")
            .anyRequest().authenticated()
            .and()
            .formLogin()
            .loginPage("/login")
            .permitAll()
            .and()
            .logout()
            .logoutUrl("/logout")
            .logoutSuccessUrl("/login")
            .permitAll();
    }


    /**
     * In memory authentication configuration using AuthenticationManagerBuilder
     */
    /**@Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication().withUser("user").password("password").roles("USER");
        auth.inMemoryAuthentication().withUser("admin").password("admin").roles("ADMIN");
        auth.inMemoryAuthentication().withUser("reader").password("reader").roles("READER");
    }*/

    /**
     * Database authentication configuration using AuthenticationManagerBuilder
     */
   /* @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth, DataSource dataSource) throws Exception {
        String userQuery = "select id, password, 1 from employee where id=?";
        String authoritiesQuery ="select id, 'ROLE_ADMIN' from employee where id=?";

        auth.jdbcAuthentication().dataSource(dataSource)
                .usersByUsernameQuery(userQuery)
                .authoritiesByUsernameQuery(authoritiesQuery)
                .passwordEncoder(NoOpPasswordEncoder.getInstance());
    }*/

    /**
     * Encoder bean definition.
     * @return Encoder bean instance
     */
    @Bean
    public PasswordEncoder encoder(){
        // Use a PasswordEncoder not performing any encoding. Do not use this one in production. BCryptEncoder is recommended
        return  NoOpPasswordEncoder.getInstance();
        // return new BCryptEncoder
    }


}