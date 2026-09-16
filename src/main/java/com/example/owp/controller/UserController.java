package com.example.owp.controller;

import com.example.owp.dto.*;
import com.example.owp.model.User;
import com.example.owp.repository.UserRepository;
import com.example.owp.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

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

    @DeleteMapping("/me")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteMyProfile() {
        userService.deleteMyProfile();
    }

    @PutMapping("/me")
    @ResponseStatus(HttpStatus.OK)
    public void updateMyProfile(@Valid @RequestBody UpdateUserRequest request) {
        userService.updateMyProfile(request);
    }

    @PutMapping("/me/password")
    @ResponseStatus(HttpStatus.OK)
    public void updatePassword(@Valid @RequestBody UpdatePasswordRequest request) {
        userService.updatePassword(request);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}/updateRole")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateRole(@PathVariable Integer id, @RequestBody UpdateRoleRequest request) {
        userService.updateRole(id,request);
    }
}