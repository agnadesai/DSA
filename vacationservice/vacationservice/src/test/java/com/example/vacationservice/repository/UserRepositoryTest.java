package com.example.vacationservice.repository;

import com.example.vacationservice.model.User;
import com.example.vacationservice.model.User.Role;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class UserRepositoryTests {

    @Autowired
    private UserRepository userRepository;

    @Test
    void testFindByUsername() {
        User user = new User();
        user.setUsername("john");
        user.setPassword("password123");
        user.setRole(Role.EMPLOYEE);
        userRepository.save(user);

        Optional<User> foundUser = userRepository.findByUsername("john");

        assertThat(foundUser).isPresent();
        assertThat(foundUser.get().getUsername()).isEqualTo("john");
    }

    @Test
    void testExistsByUsername() {
        User user = new User();
        user.setUsername("alice");
        user.setPassword("password123");
        user.setRole(Role.MANAGER);
        userRepository.save(user);

        boolean exists = userRepository.existsByUsername("alice");

        assertThat(exists).isTrue();
    }

    @Test
    void testDoesNotExistByUsername() {
        boolean exists = userRepository.existsByUsername("non_existent_user");

        assertThat(exists).isFalse();
    }
}
