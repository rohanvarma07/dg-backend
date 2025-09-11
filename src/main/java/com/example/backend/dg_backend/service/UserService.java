package com.example.backend.dg_backend.service;

import com.example.backend.dg_backend.model.User;
import com.example.backend.dg_backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    /**
     * Register a new user with encrypted password
     */
    public User registerUser(String email, String rawPassword, String firstName, String lastName) {
        // Check if user already exists
        if (userRepository.existsByEmail(email)) {
            throw new RuntimeException("Email already exists");
        }

        // Encrypt the password using BCrypt
        String encryptedPassword = passwordEncoder.encode(rawPassword);

        // Create new user with encrypted password
        User user = new User(email, encryptedPassword, firstName, lastName);
        return userRepository.save(user);
    }

    /**
     * Authenticate user login with encrypted password verification
     */
    public User loginUser(String email, String rawPassword) {
        Optional<User> userOptional = userRepository.findByEmail(email);

        if (userOptional.isPresent()) {
            User user = userOptional.get();
            // Use BCrypt to verify the password against the encrypted version
            if (passwordEncoder.matches(rawPassword, user.getPassword())) {
                return user;
            }
        }

        throw new RuntimeException("Invalid email or password");
    }

    /**
     * Check if email is already taken
     */
    public boolean isEmailTaken(String email) {
        return userRepository.existsByEmail(email);
    }

    /**
     * Change user password with proper encryption
     */
    public User changePassword(Long userId, String currentPassword, String newPassword) {
        Optional<User> userOptional = userRepository.findById(userId);

        if (userOptional.isEmpty()) {
            throw new RuntimeException("User not found");
        }

        User user = userOptional.get();

        // Verify current password
        if (!passwordEncoder.matches(currentPassword, user.getPassword())) {
            throw new RuntimeException("Current password is incorrect");
        }

        // Encrypt and set new password
        String encryptedNewPassword = passwordEncoder.encode(newPassword);
        user.setPassword(encryptedNewPassword);

        return userRepository.save(user);
    }
}
