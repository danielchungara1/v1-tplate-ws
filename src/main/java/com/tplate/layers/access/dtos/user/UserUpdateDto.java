package com.tplate.layers.access.dtos.user;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@Getter
@Setter
public class UserUpdateDto extends UserBaseDto{

    private String password;

}
