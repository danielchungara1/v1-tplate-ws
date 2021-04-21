package com.tplate.layers.a.rest.dtos.user;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class UserBaseDto {

    private String name;

    private String lastname;

    private String phone;

    @NotNull(message = "roleId is required")
    private Long roleId;


}
