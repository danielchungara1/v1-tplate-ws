package com.tplate.layers.access.dtos.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
public class UserBaseDto {

    private String name;

    private String lastname;

    private String phone;

    @NotNull(message = "roleId is required")
    private Long roleId;


}
