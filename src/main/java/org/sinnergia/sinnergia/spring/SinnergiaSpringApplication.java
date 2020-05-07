package org.sinnergia.sinnergia.spring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(exclude = { SecurityAutoConfiguration.class})//toConfigureSecurity
public class SinnergiaSpringApplication {

    public static void main(String[] args) {
        SpringApplication.run(SinnergiaSpringApplication.class, args);
    }

}
