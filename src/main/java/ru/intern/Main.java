package ru.intern;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.retry.annotation.EnableRetry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

/**
 * @author Kir
 * Created on 07.07.2021
 */
@SpringBootApplication
@EnableJpaRepositories
@EnableWebMvc
@EnableRetry

public class Main {
    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }
}
