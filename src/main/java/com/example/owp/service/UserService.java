package com.example.owp.service;

import com.example.owp.dto.UserResponse;
import com.example.owp.model.User;
import com.example.owp.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    private final JwtService jwtService;
    private final UserRepository userRepository;

    public UserService(JwtService jwtService, UserRepository userRepository) {
        this.jwtService = jwtService;
        this.userRepository = userRepository;
    }

    public UserResponse getMyProfile(String token) {
        Optional<User> optionalUser = userRepository.findById(
                jwtService.parseJwt(token).get("userId", Integer.class)
        );
        if (optionalUser.isEmpty()) {
            throw new RuntimeException("User not found");
        }
        User user = optionalUser.get();
        return new UserResponse(user);
    }

    public List<UserResponse> getAllUsers() {

        return userRepository.findAll().stream().map(UserResponse::new).toList();
    }
}
