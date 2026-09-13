package com.example.owp.controller;

import com.example.owp.dto.UserResponse;
import com.example.owp.model.User;
import com.example.owp.repository.UserRepository;
import com.example.owp.service.UserService;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping("/my")
    public UserResponse getMy(@RequestHeader("Authorization") String authorization) {
        String token = authorization.substring(7);
        return userService.getMyProfile(token);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @RequestMapping("/all")
    public List<UserResponse> getAllUsers() {
        return userService.getAllUsers();
    }

}