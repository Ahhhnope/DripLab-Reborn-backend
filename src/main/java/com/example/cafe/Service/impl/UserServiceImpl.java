package com.example.cafe.Service.impl;

import com.example.cafe.DTO.LoginRequest;
import com.example.cafe.DTO.UserRequest;
import com.example.cafe.DTO.UserResponse;
import com.example.cafe.Entity.User;
import com.example.cafe.Exception.CustomResourceNotFound;
import com.example.cafe.Repository.UserRepository;
import com.example.cafe.Service.UserService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;

    @Override
    public List<UserResponse> getAll() {
        return userRepository.findAll().stream()
                .map(b -> modelMapper.map(b, UserResponse.class))
                .toList();
    }

    @Override
    public UserResponse findById(Integer id) {
        return userRepository.findById(id)
                .map(e -> modelMapper.map(e, UserResponse.class))
                .orElseThrow(() -> new CustomResourceNotFound("Not found id: " + id));
    }

    @Override
    public UserResponse add(UserRequest userRequest) {
        User user = modelMapper.map(userRequest, User.class);
        // Hash password before saving
        if (user.getPassword() != null) {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
        }
        return modelMapper.map(userRepository.save(user), UserResponse.class);
    }

    @Override
    public UserResponse update(UserRequest userRequest, Integer id) {
        // TODO: implement
        return null;
    }

    @Override
    public UserResponse delete(Integer id) {
        // TODO: implement
        return null;
    }

    @Override
    public UserResponse login(LoginRequest req) {
        User user = userRepository.findByEmail(req.getEmail())
                .orElseThrow(() -> new CustomResourceNotFound("Không tìm thấy: " + req.getEmail()));

        if (!passwordEncoder.matches(req.getPassword(), user.getPassword())) {
            throw new RuntimeException("Sai mật khẩu");
        }

        return new UserResponse(user);
    }

    @Override
    public User findByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new CustomResourceNotFound("Không tìm thấy user với email: " + email));
    }

    @Override
    public UserResponse add(User user) {
        return modelMapper.map(userRepository.save(user), UserResponse.class);
    }
}
