package com.tplate.layers.a.rest.dtos.user;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class ContactDto {

    @NotBlank (message = "email is required")
    @Email (message = "invalid email.")
    private String email;

    @NotBlank(message = "name is required")
    private String name;

    @NotBlank(message = "lastname is required")
    private String lastname;

    private String phone;

}
