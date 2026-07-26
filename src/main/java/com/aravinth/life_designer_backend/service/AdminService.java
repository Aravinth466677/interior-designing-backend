package com.aravinth.life_designer_backend.service;

import com.aravinth.life_designer_backend.entity.Admin;
import com.aravinth.life_designer_backend.repository.AdminRepository;
import java.util.Optional;
import org.springframework.stereotype.Service;

@Service
public class AdminService {
    private final AdminRepository adminRepository;

    public AdminService(AdminRepository adminRepository) {
        this.adminRepository = adminRepository;
    }

    public Optional<Admin> getAdminByEmail(String email) {
        return adminRepository.findByEmail(email);
    }

    public Admin save(Admin admin) {
        return adminRepository.save(admin);
    }
}
