package com.example.cafe.Controller;

import com.example.cafe.DTO.LoginRequest;
import com.example.cafe.DTO.UserRequest;
import com.example.cafe.Entity.Cart.Cart;
import com.example.cafe.Entity.User;
import com.example.cafe.Exception.CustomResourceNotFound;
import com.example.cafe.Repository.Cart.CartRepository;
import com.example.cafe.Repository.UserRepository;
import com.example.cafe.Security.JwtService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
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
    private final CartRepository cartRepository;

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequest request) {
        User user = userRepository.findByEmail(request.getEmail()).orElse(null);

        if (user == null || !passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            return ResponseEntity.status(401).body(Map.of("message", "Email hoặc mật khẩu không chính xác"));
        }

        String token = jwtService.generateToken(user.getEmail());


        //save that token to a cookie =w=
        ResponseCookie tokenCookie = ResponseCookie.from("token", token)
                .httpOnly(true)
                .path("/")
                .domain("localhost")
                .maxAge(60 * 60) // 1 hour
                .build();

        return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, tokenCookie.toString()).body(Map.of(
                        "user", Map.of(
                                "id", user.getId(),
                                "fullName", user.getFullName(),
                                "email", user.getEmail(),
                                "role", user.getRole(),
                                "avatar", user.getAvatar() != null ? user.getAvatar() : "null"
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

        //assign a cart to them :)
        Cart cart = new Cart();
        cart.setUser(saved);
        cartRepository.save(cart);


        String token = jwtService.generateToken(saved.getEmail());

        ResponseCookie tokenCookie = ResponseCookie.from("token", token)
                .httpOnly(true)
                .path("/")
                .domain("localhost")
                .maxAge(60 * 120) //2 hour
                .build();

        return ResponseEntity.status(201)
                .header(HttpHeaders.SET_COOKIE, tokenCookie.toString())
                .body(Map.of(
                        "user", Map.of(
                                "id", saved.getId(),
                                "fullName", saved.getFullName(),
                                "email", saved.getEmail(),
                                "role", saved.getRole(),
                                "avatar", saved.getAvatar() != null ? saved.getAvatar() : "null"
                        )
                ));
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout() {
        //create an expired cookie to clear the token on client
        ResponseCookie tokenCookie = ResponseCookie.from("token", "")
                .httpOnly(true)
                .path("/")
                .domain("localhost")
                .maxAge(0) //immediately expires lmao
                .build();

        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, tokenCookie.toString())
                .body(Map.of("message", "Logged out successfully"));
    }
}