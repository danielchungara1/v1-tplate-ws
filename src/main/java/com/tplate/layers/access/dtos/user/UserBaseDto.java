package com.tplate.layers.access.dtos.user;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class UserBaseDto {

    @NotBlank(message = "email is required")
    @Email(message = "invalid email.")
    private String email;

    @NotBlank(message = "username is required")
    private String username;

    @NotNull(message = "roleId is required")
    private Long roleId;

    private String name;

    private String lastname;

    private String phone;

}
