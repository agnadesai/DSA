package com.example.vacationservice.config;

import com.example.vacationservice.model.User;
import com.example.vacationservice.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class DataLoader implements CommandLineRunner {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) {
        // Only add users if the repository is empty
        if (userRepository.count() == 0) {
            // Create workers
            User john = new User();
            john.setUsername("john");
            john.setPassword(passwordEncoder.encode("password123"));
            john.setRole(User.Role.EMPLOYEE);
            userRepository.save(john);

            User alice = new User();
            alice.setUsername("alice");
            alice.setPassword(passwordEncoder.encode("password123"));
            alice.setRole(User.Role.EMPLOYEE);
            userRepository.save(alice);

            // Create manager
            User bob = new User();
            bob.setUsername("bob");
            bob.setPassword(passwordEncoder.encode("password123"));
            bob.setRole(User.Role.MANAGER);
            userRepository.save(bob);
        }
    }
}
