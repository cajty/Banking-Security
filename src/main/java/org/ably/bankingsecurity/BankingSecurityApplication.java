package org.ably.bankingsecurity;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EntityScan(basePackages = "org.ably.bankingsecurity.model")
@EnableJpaRepositories(basePackages = "org.ably.bankingsecurity.repository")
public class BankingSecurityApplication {

    public static void main(String[] args) {
        SpringApplication.run(BankingSecurityApplication.class, args);
    }
}
