package com.aravinth.life_designer_backend.config;

import com.aravinth.life_designer_backend.entity.Admin;
import com.aravinth.life_designer_backend.repository.AdminRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class AdminSeeder implements CommandLineRunner {
    private final AdminRepository adminRepository;
    private final PasswordEncoder passwordEncoder;

    @Value("${admin.email}")
    private String adminEmail;

    @Value("${admin.password}")
    private String adminPassword;

    public AdminSeeder(AdminRepository adminRepository,
                       PasswordEncoder passwordEncoder) {
        this.adminRepository = adminRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) {

        if (adminRepository.count() == 0) {

            Admin admin = new Admin();

            admin.setEmail(adminEmail);
            admin.setPassword(passwordEncoder.encode(adminPassword));

            adminRepository.save(admin);

            System.out.println("Default Admin Created");
        } else {
            System.out.println("Admin Already Exists");
        }
    }
}
