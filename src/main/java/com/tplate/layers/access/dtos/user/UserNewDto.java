package com.tplate.layers.access.dtos.user;

import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class UserNewDto extends UserBaseDto{

    @NotBlank(message = "email is required")
    @Email(message = "invalid email.")
    private String email;

    @NotBlank(message = "username is required")
    private String username;

    @NotBlank(message = "password is required")
    private String password;


}
