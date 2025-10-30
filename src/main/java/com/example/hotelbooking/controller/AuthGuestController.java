package com.example.hotelbooking.controller;

import com.example.hotelbooking.entity.Guest;
import com.example.hotelbooking.service.AuthGuestService;

import jakarta.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/guest")
public class AuthGuestController {

    @Autowired
    private AuthGuestService authGuestService;

    // Halaman Login
    @GetMapping("/login")
    public String loginPage() {
        return "guestlogin";
    }

    // Proses Login
    @PostMapping("/login")
    public String loginGuest(@RequestParam("logemail") String email,
            @RequestParam("logpass") String pass,
            HttpSession session,
            RedirectAttributes redirectAttributes) {

        Guest guest = authGuestService.login(email, pass);

        if (guest != null) {
            // Simpan guest ke session agar bisa diakses dari controller lain
            session.setAttribute("loggedGuest", guest);

            // Tambahkan pesan sukses
            redirectAttributes.addFlashAttribute("infoMessage", guest.displayInfo());

            // Arahkan ke halaman dashboard atau booking, tergantung use case kamu
            return "redirect:/guest/dashboard";
            // atau kalau kamu mau langsung booking:
            // return "redirect:/booking/add";
        } else {
            // Jika gagal login
            redirectAttributes.addFlashAttribute("errorMessage", "Email atau Password salah!");
            return "redirect:/guest/login";
        }
    }

    // Halaman Sign Up
    @GetMapping("/signup")
    public String signUpPage() {
        return "guestsignup";
    }

    // Proses Sign Up
    @PostMapping("/signup")
    public String signUp(@RequestParam("logname") String name,
            @RequestParam("logemail") String email,
            @RequestParam("logpass") String password,
            @RequestParam("address") String address,
            @RequestParam("phone") String phone,
            RedirectAttributes redirectAttributes) {
        try {
            // Membuat objek Guest baru
            Guest newGuest = new Guest();
            newGuest.setName(name);
            newGuest.setEmail(email);
            newGuest.setPass(password);
            newGuest.setAddress(address);
            newGuest.setPhone(phone);

            // Simpan data ke database melalui service
            authGuestService.signup(newGuest);

            // Berikan pesan sukses dan redirect ke halaman login
            redirectAttributes.addFlashAttribute("infoMessage", "Akun berhasil dibuat. Silakan login.");
            return "redirect:/guest/login";
        } catch (IllegalArgumentException e) {
            // Berikan pesan error jika ada masalah
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
            return "redirect:/guest/signup";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage",
                    "Terjadi kesalahan saat membuat akun. Silakan coba lagi.");
            return "redirect:/guest/signup";
        }
    }

    @GetMapping("/dashboard")
    public String dashboard(Model model) {
        return "guestdashboard";
    }

}
