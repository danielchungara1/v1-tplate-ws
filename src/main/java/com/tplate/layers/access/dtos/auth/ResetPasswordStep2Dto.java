package com.tplate.layers.access.dtos.auth;

import lombok.Data;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Data
public class ResetPasswordStep2Dto {

    @NotBlank(message = "email is required")
    @Email(message = "email not valid")
    private String email;

    @NotBlank(message = "code is required")
    private String code;

    @NotBlank(message = "password is required")
    private String password;

}
