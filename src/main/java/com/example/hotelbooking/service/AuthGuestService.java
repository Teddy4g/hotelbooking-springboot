package com.example.hotelbooking.service;

import com.example.hotelbooking.entity.Guest;
import com.example.hotelbooking.repository.AuthGuestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthGuestService {

    @Autowired
    private AuthGuestRepository authGuestRepository;

    // Login Guest
    public Guest login(String email, String pass) {
        return authGuestRepository.findByEmailAndPass(email, pass); // Login tanpa enkripsi
    }

    // Registrasi Guest dengan auto-generate ID
    public Guest signup(Guest guest) {
        if (authGuestRepository.existsByEmail(guest.getEmail())) {
            throw new IllegalArgumentException("Email sudah terdaftar!");
        }

        // Generate ID dengan format CS001, CS002, dst.
        String newId = generateGuestId();
        guest.setId(newId);

        return authGuestRepository.save(guest);
    }

    private String generateGuestId() {
        long count = authGuestRepository.count() + 1;
        return String.format("CS%03d", count);
    }

    // 👉 method tambahan untuk dipanggil dari BookingController
    public String generateGuestIdForBooking() {
        long count = authGuestRepository.count() + 1;
        return String.format("CS%03d", count);
    }
}
