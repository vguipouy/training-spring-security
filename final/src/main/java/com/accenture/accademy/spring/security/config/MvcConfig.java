package com.accenture.accademy.spring.security.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Spring MVC configuration class.
 */
@Configuration
public class MvcConfig implements WebMvcConfigurer {

    /**
     * Map url to view template without having to define a custom controller,
     *
     * @param registry Views registry
     */
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/login").setViewName("login");
    }

}