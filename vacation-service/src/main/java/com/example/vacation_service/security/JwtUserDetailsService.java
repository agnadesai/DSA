package com.example.vacation_service.security;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import jakarta.annotation.PostConstruct;
import java.util.Collections;
import java.util.concurrent.ConcurrentHashMap;
import java.util.Map;

@Service
public class JwtUserDetailsService implements UserDetailsService {

    private final Map<String, UserDetails> users = new ConcurrentHashMap<>();
    private final PasswordEncoder passwordEncoder;

    public JwtUserDetailsService(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @PostConstruct
    public void initialize() {
        // Initialize predefined users with encoded passwords
        users.put("john", User.builder()
            .username("john")
            .password(passwordEncoder.encode("password"))
            .authorities(new SimpleGrantedAuthority("ROLE_EMPLOYEE"))
            .build());
            
        users.put("alice", User.builder()
            .username("alice")
            .password(passwordEncoder.encode("password"))
            .authorities(new SimpleGrantedAuthority("ROLE_EMPLOYEE"))
            .build());
            
        users.put("bob", User.builder()
            .username("bob")
            .password(passwordEncoder.encode("password"))
            .authorities(new SimpleGrantedAuthority("ROLE_MANAGER"))
            .build());
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserDetails user = users.get(username);
        if (user == null) {
            throw new UsernameNotFoundException("User not found with username: " + username);
        }
        return user;
    }
}
