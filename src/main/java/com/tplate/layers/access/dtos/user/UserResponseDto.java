package com.tplate.layers.access.dtos.user;

import com.tplate.layers.access.dtos.role.RoleResponseDto;
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

//    @Data
//    private static class UserRoleDto {
//        private Long id;
//        private String name;
//        private String description;
//    }
}
