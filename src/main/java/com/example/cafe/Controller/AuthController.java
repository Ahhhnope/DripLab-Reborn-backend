package com.example.cafe.Controller;

import com.example.cafe.DTO.LoginRequest;
import com.example.cafe.DTO.UserRequest;
import com.example.cafe.DTO.UserResponse;
import com.example.cafe.Entity.User;
import com.example.cafe.Exception.CustomResourceNotFound;
import com.example.cafe.Security.JwtUtils;
import com.example.cafe.Service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
//import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

//@RestController
//@RequestMapping("/api/auth")
//@RequiredArgsConstructor
public class AuthController {
//    private final JwtUtils jwtUtils;
//    private final UserService userService;
//    private final PasswordEncoder passwordEncoder;
//
//    @PostMapping("/login")
//    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
//        // 1. Find user in Database
//        User user = userService.findByEmail(loginRequest.getEmail());
//
//        if (user == null) {
//            throw new CustomResourceNotFound("Không tìm thấy email: " + loginRequest.getEmail());
//        }
//
//        if (!passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())) {
//            throw new RuntimeException("Mật khẩu sai");
//        }
//
//        String token = jwtUtils.generateToken(user.getFullName());
//
//        return ResponseEntity.ok(new UserResponse(
//                user.getFullName(),
//                user.getEmail(),
//                user.getAvatar(),
//                user.getPhone(),
//                token
//        ));
//    }
//
//    @PostMapping("/register")
//    public ResponseEntity<?> register(@Valid @RequestBody UserRequest userRequest) {
//        if (userService.findByEmail(userRequest.getEmail()) != null) {
//            throw new RuntimeException("Email đã được sử dụng!");
//        }
//
//        User user = new User();
//        user.setFullName(userRequest.getFullName());
//        user.setEmail(userRequest.getEmail());
//        user.setPhone(userRequest.getPhone());
//        user.setPassword(passwordEncoder.encode(userRequest.getPassword()));
//        user.setAvatar("default-avatar.png");
//
//        UserResponse response = userService.add(user);
//        return ResponseEntity.ok(response);
//    }
}
