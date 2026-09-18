package com.example.owp.dto;

import com.example.owp.model.Role;
import jakarta.validation.constraints.NotNull;

public class UpdateRoleRequest {
    @NotNull
    private Role role;

    public Role getRole() {
        return this.role;
    }

    public void setRole(Role role) {
        this.role = role;
    }
}
