package com.tplate.layers.access.dtos.auth;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class LoginDto {

    @NotBlank(message = "username is required")
    private String username;

    @NotBlank(message = "password is required")
    private String password;

}
