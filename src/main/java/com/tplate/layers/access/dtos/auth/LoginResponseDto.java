package com.tplate.layers.access.dtos.auth;

import com.tplate.layers.access.dtos.user.UserResponseDto;
import lombok.Data;

@Data
public class LoginResponseDto {

    private String token;
    private UserResponseDto user;

}
