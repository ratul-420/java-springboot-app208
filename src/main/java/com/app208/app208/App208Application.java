package com.app208.app208;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.app208.app208.model.Role;
import com.app208.app208.model.User;
import com.app208.app208.repository.RoleRepository;
import com.app208.app208.repository.UserRepository;

@SpringBootApplication
public class App208Application {

    public static void main(String[] args) {
        SpringApplication.run(App208Application.class, args);
    }

    // Role/User seeding
    @Bean
    CommandLineRunner initRolesAndAdmin(RoleRepository roleRepository,
                                        UserRepository userRepository,
                                        PasswordEncoder passwordEncoder) {
        return args -> {
            Role userRole = roleRepository.findByName("ROLE_USER");
            if (userRole == null) {
                userRole = new Role();
                userRole.setName("ROLE_USER");
                roleRepository.save(userRole);
            }
            Role adminRole = roleRepository.findByName("ROLE_ADMIN");
            if (adminRole == null) {
                adminRole = new Role();
                adminRole.setName("ROLE_ADMIN");
                roleRepository.save(adminRole);
            }

            if (userRepository.findByEmail("admin@example.com") == null) {
                User admin = new User();
                admin.setUsername("admin");
                admin.setEmail("admin@example.com");
                admin.setPassword(passwordEncoder.encode("admin123"));
                admin.getRoles().add(adminRole);
                userRepository.save(admin);
            }
        };
    }

}
