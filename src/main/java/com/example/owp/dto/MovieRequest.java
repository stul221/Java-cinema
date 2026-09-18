package com.example.owp.dto;

import jakarta.validation.constraints.NotBlank;

public class MovieRequest {

    @NotBlank
    private String title;


    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

}
