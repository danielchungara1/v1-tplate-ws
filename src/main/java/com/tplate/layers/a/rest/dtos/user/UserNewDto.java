package com.tplate.layers.a.rest.dtos.user;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

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
