package dev.fejda.latinoamericacomparte.controller;

import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/user")
public class UserController {

    @PostMapping("/create")
    public ResponseEntity<Map<String, Object>> createUser(@RequestBody Map<String, String> user) {
        Map<String, Object> response = new HashMap<>();
        response.put("message", "Usuario creado exitosamente");
        response.put("user", user);
        response.put("status", "success");
        return ResponseEntity.ok(response);
    }

    @PostMapping("/")
    public ResponseEntity<Map<String, Object>> login(@RequestBody Map<String, String> credentials) {
        Map<String, Object> response = new HashMap<>();
        String email = credentials.get("email");
        String password = credentials.get("password");
        
        response.put("message", "Login exitoso");
        response.put("email", email);
        response.put("token", "jwt-token-ejemplo-" + System.currentTimeMillis());
        return ResponseEntity.ok(response);
    }
}