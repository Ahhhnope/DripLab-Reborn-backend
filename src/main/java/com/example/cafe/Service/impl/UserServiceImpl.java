package com.example.cafe.Service.impl;

import com.example.cafe.DTO.LoginRequest;
import com.example.cafe.DTO.UserRequest;
import com.example.cafe.DTO.UserResponse;
import com.example.cafe.Entity.User;
import com.example.cafe.Exception.CustomResourceNotFound;
import com.example.cafe.Repository.UserRepository;
import com.example.cafe.Service.UserService;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    @Override
    public List<UserResponse> getAll() {
        return userRepository.findAll().stream().map(b -> modelMapper.map(b , UserResponse.class)).toList();
    }

    @Override
    public UserResponse findById(Integer id) {
        return userRepository.findById(id).map(e -> modelMapper.map(e, UserResponse.class)).orElseThrow(() -> new CustomResourceNotFound("Not found id: " +id));
    }


    @Override
    public UserResponse add(UserRequest userRequest) {
        User em = modelMapper.map(userRequest, User.class);
        userRepository.save(em);
        return modelMapper.map(userRequest, UserResponse.class);
    }

    @Override
    public UserResponse update(UserRequest userRequest, Integer id) {
        return null;
    }

    @Override
    public UserResponse delete(Integer id) {
        return null;
    }

    @Override
    public UserResponse login(LoginRequest req) {

            User user = userRepository.findByEmail(req.getEmail());

            if(user == null){
                throw new RuntimeException("User not found");
            }

            if(!user.getPassword().equals(req.getPassword())){
                throw new RuntimeException("Wrong password");
            }

            return new UserResponse(user);
    }

    @Override
    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public UserResponse add(User user) {
        User savedUser = userRepository.save(user);

        return new UserResponse(
                savedUser.getFullName(),
                savedUser.getEmail(),
                savedUser.getAvatar(),
                savedUser.getPhone(),
                null
        );
    }

}
