package com.tplate.layers.access.dtos.user;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class UserNewDto extends UserBaseDto{

    @NotBlank(message = "password is required")
    private String password;

}
