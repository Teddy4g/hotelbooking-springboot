package com.example.hotelbooking.repository;

import com.example.hotelbooking.entity.Admin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthAdminRepository extends JpaRepository<Admin, String> {
    Admin findByEmailAndPass(String email, String pass); // Untuk autentikasi berdasarkan email dan password
}
