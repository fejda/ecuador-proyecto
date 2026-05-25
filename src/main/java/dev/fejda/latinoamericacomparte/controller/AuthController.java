package dev.fejda.latinoamericacomparte.controller;

import dev.fejda.latinoamericacomparte.config.JwtUtil;
import dev.fejda.latinoamericacomparte.model.entity.Administrator;
import dev.fejda.latinoamericacomparte.model.service.AdministratorService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;

@Controller
@RequestMapping("/auth")
public class AuthController {

    private final AdministratorService adminService;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public AuthController(AdministratorService adminService, PasswordEncoder passwordEncoder, JwtUtil jwtUtil) {
        this.adminService = adminService;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
    }

    @GetMapping("/login-page")
    public String loginPage() {
        return "auth/login";
    }


    @PostMapping("/login")
    public String login(@RequestParam String email, @RequestParam String password,
                        HttpServletResponse response, Model model) {

        var adminOpt = adminService.findAdministratorByEmail(email);

        if (adminOpt.isPresent() && passwordEncoder.matches(password, adminOpt.get().getPassword())) {
            String token = jwtUtil.generateToken(email);
            Cookie cookie = new Cookie("jwt_token", token);
            cookie.setPath("/");
            cookie.setMaxAge(86400);
            response.addCookie(cookie);
            return "redirect:/admin/dashboard";
        }

        model.addAttribute("error", "Credenciales inválidas");
        return "auth/login";
    }

    @GetMapping("/logout")
    public String logout(HttpServletResponse response) {
        Cookie cookie = new Cookie("jwt_token", null);
        cookie.setPath("/");
        cookie.setMaxAge(0);
        response.addCookie(cookie);
        return "redirect:/auth/login-page";
    }
}