package com.tplate.layers.admission.dtos.user;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class UserBaseDto {

    private String name;

    private String lastname;

    private String phone;

    @NotNull(message = "roleId is required")
    private Long roleId;


}
