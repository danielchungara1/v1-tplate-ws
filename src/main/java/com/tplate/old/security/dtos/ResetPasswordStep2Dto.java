package com.tplate.old.security.dtos;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class ResetPasswordStep2Dto {
    private String email;
    private String code;
    private String password;
}
