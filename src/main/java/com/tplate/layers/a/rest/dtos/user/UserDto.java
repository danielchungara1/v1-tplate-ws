package com.tplate.layers.a.rest.dtos.user;

import com.tplate.layers.b.business.validators.FormValidator;
import com.tplate.old.exceptions.FormValidatorException;
import lombok.Data;

@Data
public class UserDto {
    private Long id;
    private String username;
}
