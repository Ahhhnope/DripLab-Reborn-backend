package com.example.cafe.Controller;

import com.example.cafe.DTO.UserRequest;
import com.example.cafe.DTO.UserResponse;
import com.example.cafe.Entity.User;
import com.example.cafe.Exception.CustomResourceNotFound;
import com.example.cafe.Repository.UserRepository;
import com.example.cafe.Service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserController {

    private final UserRepository userRepository;
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
        return ResponseEntity.ok(userRepository.findAll());
    }

    //boi the password reset
    @PutMapping("/{id}/reset-password")
    public ResponseEntity<?> resetPassword(@PathVariable Integer id, @RequestBody Map<String, String> body) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new CustomResourceNotFound("User not found: " + id));
        user.setPassword(passwordEncoder.encode(body.get("newPassword")));
        userRepository.save(user);
        return ResponseEntity.ok(Map.of("message", "Reset mật khẩu thành công"));
    }


    // user's account page stuff
    @GetMapping("/me")
    public ResponseEntity<UserResponse> getMyInfo(@AuthenticationPrincipal User currentUser) {
        return ResponseEntity.ok(userService.findById(currentUser.getId()));
    }

    @PutMapping("/me")
    public ResponseEntity<UserResponse> updateMyInfo(@AuthenticationPrincipal User currentUser, @RequestBody UserRequest userRequest) {
        return ResponseEntity.ok(userService.update(userRequest, currentUser.getId()));
    }

    @DeleteMapping("/me")
    public ResponseEntity<Void> deleteMyAccount(@AuthenticationPrincipal User currentUser) {
        userService.delete(currentUser.getId());
        return ResponseEntity.noContent().build();
    }
}
