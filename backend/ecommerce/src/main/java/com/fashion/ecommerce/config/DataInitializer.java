package com.fashion.ecommerce.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import com.fashion.ecommerce.entity.AuthProvider;
import com.fashion.ecommerce.entity.RoleEntity;
import com.fashion.ecommerce.entity.UserEntity;
import com.fashion.ecommerce.repository.RoleRepository;
import com.fashion.ecommerce.repository.UserRepository;

@Component
public class DataInitializer implements CommandLineRunner {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    // admin config from environment variables
    @Value("${ADMIN_NAME}")
    private String adminName;

    @Value("${ADMIN_EMAIL}")
    private String adminEmail;

    @Value("${ADMIN_PASSWORD}")
    private String adminPassword;

    public DataInitializer(
            UserRepository userRepository,
            RoleRepository roleRepository
    ) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    @Override
    public void run(String... args) {

        // create roles if not exist
        RoleEntity userRole = roleRepository.findByName("USER");
        if (userRole == null) {
            userRole = new RoleEntity();
            userRole.setName("USER");
            roleRepository.save(userRole);
        }

        RoleEntity adminRole = roleRepository.findByName("ADMIN");
        if (adminRole == null) {
            adminRole = new RoleEntity();
            adminRole.setName("ADMIN");
            roleRepository.save(adminRole);
        }

        RoleEntity systemAdminRole = roleRepository.findByName("SYSTEM_ADMIN");
        if (systemAdminRole == null) {
            systemAdminRole = new RoleEntity();
            systemAdminRole.setName("SYSTEM_ADMIN");
            roleRepository.save(systemAdminRole);
        }

        // create default system admin account
        if (userRepository.findByEmail(adminEmail) == null) {

            UserEntity admin = new UserEntity();
            admin.setFullname(adminName);
            admin.setEmail(adminEmail);
            admin.setPassword(passwordEncoder.encode(adminPassword));
            admin.setRole(systemAdminRole);
            admin.setProvider(AuthProvider.LOCAL);
            admin.setIsActive(true);

            userRepository.save(admin);
        }
    }
}