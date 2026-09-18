package com.example.owp.service;

import com.example.owp.dto.*;
import com.example.owp.model.User;
import com.example.owp.repository.UserRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    private final JwtService jwtService;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(JwtService jwtService, UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.jwtService = jwtService;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public UserResponse getMyProfile(String token) {
        Integer id = (Integer) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Optional<User> optionalUser = userRepository.findById(id);
        if (optionalUser.isEmpty()) {
            throw new RuntimeException("User not found");
        }
        User user = optionalUser.get();
        return new UserResponse(user);
    }

    public List<UserResponse> getAllUsers() {

        return userRepository.findAll().stream().map(UserResponse::new).toList();
    }

    public void deleteMyProfile() {
        Integer id = (Integer) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        userRepository.deleteById(id);
    }

    public void updateMyProfile(UpdateUserRequest request) {
        Integer id = (Integer) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Optional<User> optimalUser = userRepository.findById(id);
        if (optimalUser.isEmpty()) {
            throw new RuntimeException("User not found");
        }
        User user = optimalUser.get();
        user.setEmail(request.getEmail());
        user.setUserName(request.getUserName());

        userRepository.save(user);
    }

    public void updatePassword(UpdatePasswordRequest request) {
        Integer id = (Integer) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Optional<User> optionalUser = userRepository.findById(id);
        if (optionalUser.isEmpty()) {
            throw new RuntimeException("User not found");
        }
        User user = optionalUser.get();

        if (!passwordEncoder.matches(
                request.getOldPassword(),
                user.getPassword()
        )) {
            throw new RuntimeException("Wrong old password");
        }

        user.setPassword(passwordEncoder.encode(request.getNewPassword()));
        userRepository.save(user);
    }

    public void updateRole(Integer id, UpdateRoleRequest request) {
        Optional<User> optionalUser = userRepository.findById(id);
        if (optionalUser.isEmpty()) {
            throw new RuntimeException("User not found");
        }
        User user = optionalUser.get();

        if(user.getRole().name().equals(request.getRole().name())) {
            throw new RuntimeException("The old and new roles should be different");
        }

        user.setRole(request.getRole());
        userRepository.save(user);
    }

}
