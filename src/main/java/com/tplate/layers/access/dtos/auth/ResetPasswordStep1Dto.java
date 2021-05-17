package com.tplate.layers.access.dtos.auth;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ResetPasswordStep1Dto {

    @NotBlank(message = "email is required")
    @Email(message = "email invalid")
    private String email;

}
