package com.aravinth.life_designer_backend.controller;

import com.aravinth.life_designer_backend.dto.request.LoginRequest;
import com.aravinth.life_designer_backend.dto.response.LoginResponse;
import com.aravinth.life_designer_backend.entity.Admin;
import com.aravinth.life_designer_backend.repository.AdminRepository;
import com.aravinth.life_designer_backend.service.JwtService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final AdminRepository adminRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService ;

    public AuthController(AdminRepository adminRepository,
                          PasswordEncoder passwordEncoder,
                          JwtService jwtService) {
        this.adminRepository = adminRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {

        Optional<Admin> admin = adminRepository.findByEmail(request.getEmail());

        if (admin.isEmpty()) {
            return ResponseEntity.badRequest().body("Invalid Email");
        }

        if (!passwordEncoder.matches(request.getPassword(), admin.get().getPassword())) {
            return ResponseEntity.badRequest().body("Wrong Password");
        }

        String token = jwtService.generateToken(admin.get().getEmail());

        return ResponseEntity.ok(new LoginResponse(token));
    }
}
