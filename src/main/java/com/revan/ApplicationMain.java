package com.revan;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.PropertySource;

@SpringBootApplication
@PropertySource({"classpath:application.properties"})
public class ApplicationMain {

    public static void main(String[] args) {
        // Starting the Spring Boot Application
        SpringApplication.run(ApplicationMain.class, args);
    }

    // Creating a bean that will initialize the CORS (Cross Origin Resource Sharing) Filter
    @Bean
    public FilterRegistrationBean corsFilterRegistration() {
        FilterRegistrationBean registrationBean =
                new FilterRegistrationBean(new CORSFilter());
        registrationBean.setName("CORS Filter");
        registrationBean.addUrlPatterns("/*");
        registrationBean.setOrder(1);
        return registrationBean;
    }
}