package com.example.cafe.Controller;

import com.example.cafe.DTO.LoginRequest;
import com.example.cafe.DTO.UserRequest;
import com.example.cafe.DTO.UserResponse;
import com.example.cafe.Entity.User;
import com.example.cafe.Repository.UserRepository;
import com.example.cafe.Service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import jakarta.servlet.http.HttpSession;
import java.util.List;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class CafeController {

//    private final UserService userService;
//
//    @GetMapping
//    public ResponseEntity<List<UserResponse>> getDrinks() {
//        return new ResponseEntity<>(userService.getAll(), HttpStatus.OK);
//    }
//    @GetMapping("/id/{id}")
//    public ResponseEntity<UserResponse> findById(@PathVariable Integer id) {
//        return new ResponseEntity<>(userService.findById(id), HttpStatus.OK);
//    }
//    @PostMapping("/register")
//    public ResponseEntity<UserResponse> add(@Valid @RequestBody UserRequest er) {
//        return new ResponseEntity<>(userService.add(er), HttpStatus.CREATED);
//    }
//
//
//
//    //login and auth
//    @PostMapping("/auth/login")
//    public ResponseEntity<UserResponse> login(@RequestBody LoginRequest req, HttpSession session) {
//        UserResponse user = userService.login(req);
//        session.setAttribute("user", user);
//        return ResponseEntity.ok(user);
//    }
//
//    @GetMapping("/auth/me")
//    public ResponseEntity<UserResponse> user(HttpSession session) {
//
//        UserResponse user = (UserResponse) session.getAttribute("user");
//        if (user == null) {
//            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
//        }
//
//        return ResponseEntity.ok(user);
//    }
}
