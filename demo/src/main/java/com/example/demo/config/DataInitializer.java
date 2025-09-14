package com.example.demo.config;

import com.example.demo.model.Role;
import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class DataInitializer {

    @Bean
    CommandLineRunner initDatabase(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        return args -> {
            // Create manager
            User manager = new User();
            manager.setUsername("manager");
            manager.setPassword(passwordEncoder.encode("manager123"));
            manager.setRole(Role.MANAGER);
            manager.setRemainingVacationDays(30);
            userRepository.save(manager);

            // Create employees
            User employee1 = new User();
            employee1.setUsername("john");
            employee1.setPassword(passwordEncoder.encode("john123"));
            employee1.setRole(Role.EMPLOYEE);
            employee1.setRemainingVacationDays(30);
            userRepository.save(employee1);

            User employee2 = new User();
            employee2.setUsername("alice");
            employee2.setPassword(passwordEncoder.encode("alice123"));
            employee2.setRole(Role.EMPLOYEE);
            employee2.setRemainingVacationDays(30);
            userRepository.save(employee2);
        };
    }
}
