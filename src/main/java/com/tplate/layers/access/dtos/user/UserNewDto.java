package com.tplate.layers.access.dtos.user;

import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class UserNewDto extends UserBaseDto{

    @NotBlank(message = "password is required")
    private String password;

}
