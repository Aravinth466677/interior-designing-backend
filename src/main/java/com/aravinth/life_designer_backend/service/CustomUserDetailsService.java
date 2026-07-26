package com.aravinth.life_designer_backend.service;

import com.aravinth.life_designer_backend.entity.Admin;
import com.aravinth.life_designer_backend.repository.AdminRepository;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    private final AdminRepository adminRepository;

    public CustomUserDetailsService(AdminRepository adminRepository) {
        this.adminRepository = adminRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email)
            throws UsernameNotFoundException {

        Admin admin = adminRepository.findByEmail(email)
                .orElseThrow(() ->
                        new UsernameNotFoundException("Admin not found"));

        return User.builder()
                .username(admin.getEmail())
                .password(admin.getPassword())
                .roles("ADMIN")
                .build();
    }
}
