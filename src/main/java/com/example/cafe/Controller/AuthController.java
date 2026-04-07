package com.example.cafe.Controller;

import com.example.cafe.DTO.LoginRequest;
import com.example.cafe.DTO.UserRequest;
import com.example.cafe.Entity.User;
import com.example.cafe.Exception.CustomResourceNotFound;
import com.example.cafe.Repository.UserRepository;
import com.example.cafe.Security.JwtService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequest request) {
        User user = userRepository.findByEmail(request.getEmail()).orElse(null);

        if (user == null || !passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            return ResponseEntity.status(401).body(Map.of("message", "Email hoặc mật khẩu không chính xác"));
        }

        String token = jwtService.generateToken(user.getEmail());

        return ResponseEntity.ok(Map.of(
                "token", token,
                "user", Map.of(
                        "id", user.getId(),
                        "fullName", user.getFullName(),
                        "email", user.getEmail(),
                        "role", user.getRole(),
                        "avatar", user.getAvatar() != null ? user.getAvatar() : ""
                )
        ));
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody UserRequest request) {
        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            return ResponseEntity.status(409).body(Map.of("message", "Email đã được sử dụng"));
        }

        User user = new User();
        user.setFullName(request.getFullName());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setPhone(request.getPhone());
        user.setDefaultAddress(request.getDefaultAddress());
        user.setRole("USER");

        User saved = userRepository.save(user);
        String token = jwtService.generateToken(saved.getEmail());

        return ResponseEntity.status(201).body(Map.of(
                "token", token,
                "user", Map.of(
                        "id", saved.getId(),
                        "fullName", saved.getFullName(),
                        "email", saved.getEmail(),
                        "role", saved.getRole(),
                        "avatar", ""
                )
        ));
    }
}