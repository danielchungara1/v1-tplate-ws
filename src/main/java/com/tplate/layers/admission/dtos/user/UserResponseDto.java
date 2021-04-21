package com.tplate.layers.admission.dtos.user;

import com.tplate.layers.admission.dtos.role.RoleResponseDto;
import lombok.Data;

/**
 * Output DTO. Must not have javax validation.
 */

@Data
public class UserResponseDto {
    private Long id;
    private String email;
    private String lastname;
    private String name;
    private String phone;
    private String username;
    private RoleResponseDto role;
}
