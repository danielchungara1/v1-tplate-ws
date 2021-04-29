package com.tplate.layers.access.dtos.auth;

import lombok.Data;
import lombok.ToString;

@Data
public class LoginDto {

    private String username;
    private String password;

}
