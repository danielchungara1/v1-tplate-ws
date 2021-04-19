package com.tplate.layers.a.rest.dtos.user;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class CredentialsDto {

    @NotBlank(message = "username is required")
    private String username;

    /**
     * Password obligatory depends on Context Controller:
     * 1- Post: is required.
     * 2- Update: is optional.
     * 2.1- If it is null then the original password remains same Otherwise it's hashed and saved.
     */
    private String password; // Todo: add obligatory restrictions for Swagger (into controllers).

}
