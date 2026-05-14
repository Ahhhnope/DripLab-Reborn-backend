package com.example.cafe.Controller;

import com.example.cafe.DTO.ForgotPasswordRequest;
import com.example.cafe.DTO.LoginRequest;
import com.example.cafe.DTO.ResetPasswordRequest;
import com.example.cafe.DTO.UserRequest;
import com.example.cafe.DTO.VerifyOtpRequest;
import com.example.cafe.Entity.Cart.Cart;
import com.example.cafe.Entity.User;
import com.example.cafe.Exception.CustomResourceNotFound;
import com.example.cafe.Repository.Cart.CartRepository;
import com.example.cafe.Repository.UserRepository;
import com.example.cafe.Security.JwtService;
import com.example.cafe.Service.PasswordResetService;
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
    private final PasswordResetService passwordResetService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequest request) {
        User user = userRepository.findByEmail(request.getEmail()).orElse(null);

        if (user == null || !passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            return ResponseEntity.status(401).body(Map.of("message", "Email hoặc mật khẩu không chính xác"));
        }

        String token = jwtService.generateToken(user.getEmail());

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

        Cart cart = new Cart();
        cart.setUser(saved);
        cartRepository.save(cart);

        String token = jwtService.generateToken(saved.getEmail());

        ResponseCookie tokenCookie = ResponseCookie.from("token", token)
                .httpOnly(true)
                .path("/")
                .domain("localhost")
                .maxAge(60 * 120)
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
        ResponseCookie tokenCookie = ResponseCookie.from("token", "")
                .httpOnly(true)
                .path("/")
                .domain("localhost")
                .maxAge(0)
                .build();

        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, tokenCookie.toString())
                .body(Map.of("message", "Logged out successfully"));
    }

    // ══════════════════════════════════════════════════════════════
    //  FORGOT PASSWORD FLOW
    // ══════════════════════════════════════════════════════════════

    /**
     * Bước 1: Nhận email → tạo OTP → gửi về mail
     */
    @PostMapping("/forgot-password")
    public ResponseEntity<?> forgotPassword(@RequestBody ForgotPasswordRequest request) {
        try {
            passwordResetService.sendOtp(request.getEmail());
            return ResponseEntity.ok(Map.of("message", "Mã OTP đã được gửi tới email của bạn."));
        } catch (RuntimeException e) {
            return ResponseEntity.status(404).body(Map.of("message", e.getMessage()));
        }
    }

    /**
     * Bước 2: Xác nhận OTP
     */
    @PostMapping("/verify-otp")
    public ResponseEntity<?> verifyOtp(@RequestBody VerifyOtpRequest request) {
        try {
            passwordResetService.verifyOtp(request.getEmail(), request.getOtp());
            return ResponseEntity.ok(Map.of("message", "OTP hợp lệ."));
        } catch (RuntimeException e) {
            return ResponseEntity.status(400).body(Map.of("message", e.getMessage()));
        }
    }

    /**
     * Bước 3: Đặt lại mật khẩu mới
     */
    @PostMapping("/reset-password")
    public ResponseEntity<?> resetPassword(@RequestBody ResetPasswordRequest request) {
        try {
            passwordResetService.resetPassword(
                    request.getEmail(),
                    request.getOtp(),
                    request.getNewPassword()
            );
            return ResponseEntity.ok(Map.of("message", "Mật khẩu đã được cập nhật thành công."));
        } catch (RuntimeException e) {
            return ResponseEntity.status(400).body(Map.of("message", e.getMessage()));
        }
    }
}