package com.flypass.transaccion;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories
public class TransaccionApplication {

    public static void main(String[] args) {
        SpringApplication.run(TransaccionApplication.class, args);
    }

}
