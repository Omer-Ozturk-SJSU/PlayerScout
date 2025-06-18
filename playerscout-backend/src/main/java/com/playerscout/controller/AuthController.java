package com.playerscout.controller;

import com.playerscout.model.User;
import com.playerscout.service.UserService;
import com.playerscout.service.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired
    private UserService userService;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest request) {
        try {
            User user = userService.registerUser(request.getUsername(), request.getPassword(), request.getRole());
            return ResponseEntity.ok(user);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    /**
     * Login endpoint: checks username and password, returns JWT if valid.
     *
     * How it works:
     * 1. Receives username and password from the client.
     * 2. Looks up the user in the database.
     * 3. Uses BCrypt to check if the password matches the stored hash.
     * 4. If valid, generates a JWT and returns it.
     * 5. If invalid, returns an error message.
     */
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        Optional<User> userOpt = userService.findByUsername(request.getUsername());
        if (userOpt.isEmpty()) {
            return ResponseEntity.status(401).body("Invalid username or password");
        }
        User user = userOpt.get();
        // BCrypt checks the password
        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            return ResponseEntity.status(401).body("Invalid username or password");
        }
        // Generate JWT
        String token = jwtUtil.generateToken(user.getUsername(), user.getRole());
        // Return the token to the client
        return ResponseEntity.ok(new JwtResponse(token));
    }

    /**
     * RegisterRequest: represents the data sent by the client to register a new
     * user.
     */
    public static class RegisterRequest {
        private String username;
        private String password;
        private String role;

        // Getters and setters
        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public String getRole() {
            return role;
        }

        public void setRole(String role) {
            this.role = role;
        }
    }

    /**
     * LoginRequest: represents the data sent by the client to log in.
     */
    public static class LoginRequest {
        private String username;
        private String password;

        // Getters and setters
        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }
    }

    /**
     * JwtResponse: represents the response sent back to the client after login.
     */
    public static class JwtResponse {
        private String token;

        public JwtResponse(String token) {
            this.token = token;
        }

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }
    }
}
