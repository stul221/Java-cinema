package com.example.owp.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class UpdatePasswordRequest {

    @NotBlank
    @Size(min = 6)
    private String newPassword;


    @NotBlank
    private String oldPassword;

    public String getNewPassword() {
        return this.newPassword;
    }

    public String getOldPassword() {
        return this.oldPassword;
    }
}
