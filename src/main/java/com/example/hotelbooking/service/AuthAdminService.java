package com.example.hotelbooking.service;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import com.example.hotelbooking.entity.Admin;
import com.example.hotelbooking.repository.AuthAdminRepository;

@Service
public class AuthAdminService {
    @Autowired
    private AuthAdminRepository authAdminRepository;

    // Login Admin
    public Admin login(String email, String pass) {
        return authAdminRepository.findByEmailAndPass(email, pass);
    }

    // Registrasi Admin dengan Auto-generate ID
    public Admin register(Admin admin) {
        // Generate ID
        String newId = generateAdminId();
        admin.setId(newId);

        return authAdminRepository.save(admin);
    }

    private String generateAdminId() {
        long count = authAdminRepository.count() + 1; // Hitung total admin di database
        return String.format("ADM%03d", count); // Format ID seperti ADM001, ADM002
    }
}