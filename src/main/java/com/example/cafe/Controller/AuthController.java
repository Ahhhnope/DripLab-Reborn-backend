//package com.example.cafe.Controller;
//
//import com.example.cafe.DTO.LoginRequest;
//import com.example.cafe.DTO.UserRequest;
//import com.example.cafe.DTO.UserResponse;
//import com.example.cafe.Entity.User;
//import com.example.cafe.Exception.CustomResourceNotFound;
//import com.example.cafe.Repository.UserRepository;
//import com.example.cafe.Security.JwtService;
//import com.example.cafe.Service.UserService;
//import jakarta.validation.Valid;
//import lombok.RequiredArgsConstructor;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.ResponseEntity;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.HashMap;
//import java.util.Map;
//
//@RestController
//@RequestMapping("/api/auth")
//public class AuthController {
//
//    @Autowired
//    private JwtService jwtService; // Ensure you have your JWT Service set up
//    @Autowired
//    private PasswordEncoder passwordEncoder;
//    @Autowired
//    private UserRepository userRepository;
//
//    @PostMapping("/login")
//    public ResponseEntity<?> login(@RequestBody Map<String, String> request) {
//        String email = request.get("email");
//        String password = request.get("password");
//
//        // 1. Find user by email in DB
//        User user = userRepository.findByEmail(email)
//                .orElse(null);
//
//        // 2. Check if user exists AND password matches
//        // Note: We use passwordEncoder.matches(raw, encoded) because DB passwords should be hashed!
//        if (user != null && passwordEncoder.matches(password, user.getPassword())) {
//
//            String token = jwtService.generateToken(user.getEmail());
//
//            Map<String, Object> response = new HashMap<>();
//            response.put("token", token);
//            response.put("user", Map.of(
//                    "fullName", user.getFullName(),
//                    "email", user.getEmail(),
//                    "role", user.getRole() // Good for your Gate Boiz!
//            ));
//
//            return ResponseEntity.ok(response);
//        }
//
//        return ResponseEntity.status(401).body(Map.of("message", "Email hoặc mật khẩu không chính xác"));
//    }
//}
