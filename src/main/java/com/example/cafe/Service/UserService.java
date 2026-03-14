package com.example.cafe.Service;

import com.example.cafe.DTO.LoginRequest;
import com.example.cafe.DTO.UserRequest;
import com.example.cafe.DTO.UserResponse;
import com.example.cafe.Entity.User;
import org.jspecify.annotations.Nullable;

import java.util.List;

public interface UserService {
    List<UserResponse> getAll();
    UserResponse findById(Integer id);
    UserResponse add(UserRequest userRequest);
    UserResponse update(UserRequest userRequest, Integer id);
    UserResponse delete(Integer id);
    UserResponse login(LoginRequest req);


}
