package com.example.backend.dg_backend.controller;

import com.example.backend.dg_backend.model.User;
import com.example.backend.dg_backend.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/signup")
    public ResponseEntity<Map<String, Object>> signup(@Valid @RequestBody Map<String, String> signupData, BindingResult result) {
        Map<String, Object> response = new HashMap<>();

        try {
            // Validate input
            String email = signupData.get("email");
            String password = signupData.get("password");
            String firstName = signupData.get("firstName");
            String lastName = signupData.get("lastName");

            if (email == null || email.trim().isEmpty()) {
                throw new RuntimeException("Email is required");
            }
            if (password == null || password.length() < 6) {
                throw new RuntimeException("Password must be at least 6 characters long");
            }
            if (firstName == null || firstName.trim().isEmpty()) {
                throw new RuntimeException("First name is required");
            }
            if (lastName == null || lastName.trim().isEmpty()) {
                throw new RuntimeException("Last name is required");
            }

            User user = userService.registerUser(email.trim(), password, firstName.trim(), lastName.trim());

            response.put("success", true);
            response.put("message", "User registered successfully");
            response.put("user", Map.of(
                "id", user.getId(),
                "email", user.getEmail(),
                "firstName", user.getFirstName(),
                "lastName", user.getLastName(),
                "createdAt", user.getCreatedAt()
            ));

            return ResponseEntity.ok(response);

        } catch (Exception e) {
            response.put("success", false);
            response.put("message", e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }

    @PostMapping("/login")
    public ResponseEntity<Map<String, Object>> login(@RequestBody Map<String, String> loginData) {
        Map<String, Object> response = new HashMap<>();

        try {
            String email = loginData.get("email");
            String password = loginData.get("password");

            if (email == null || email.trim().isEmpty()) {
                throw new RuntimeException("Email is required");
            }
            if (password == null || password.isEmpty()) {
                throw new RuntimeException("Password is required");
            }

            User user = userService.loginUser(email.trim(), password);

            response.put("success", true);
            response.put("message", "Login successful");
            response.put("user", Map.of(
                "id", user.getId(),
                "email", user.getEmail(),
                "firstName", user.getFirstName(),
                "lastName", user.getLastName(),
                "createdAt", user.getCreatedAt()
            ));

            return ResponseEntity.ok(response);

        } catch (Exception e) {
            response.put("success", false);
            response.put("message", e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }

    @GetMapping("/check-email/{email}")
    public ResponseEntity<Map<String, Object>> checkEmail(@PathVariable String email) {
        Map<String, Object> response = new HashMap<>();

        boolean emailTaken = userService.isEmailTaken(email);
        response.put("emailTaken", emailTaken);
        response.put("message", emailTaken ? "Email is already taken" : "Email is available");

        return ResponseEntity.ok(response);
    }

    @PostMapping("/change-password")
    public ResponseEntity<Map<String, Object>> changePassword(@RequestBody Map<String, String> passwordData) {
        Map<String, Object> response = new HashMap<>();

        try {
            Long userId = Long.parseLong(passwordData.get("userId"));
            String currentPassword = passwordData.get("currentPassword");
            String newPassword = passwordData.get("newPassword");

            if (newPassword == null || newPassword.length() < 6) {
                throw new RuntimeException("New password must be at least 6 characters long");
            }

            User user = userService.changePassword(userId, currentPassword, newPassword);

            response.put("success", true);
            response.put("message", "Password changed successfully");

            return ResponseEntity.ok(response);

        } catch (Exception e) {
            response.put("success", false);
            response.put("message", e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }
}
