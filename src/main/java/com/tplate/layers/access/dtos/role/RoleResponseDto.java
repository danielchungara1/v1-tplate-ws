package com.tplate.layers.access.dtos.role;

import com.tplate.layers.access.dtos.permission.PermissionResponseDto;
import lombok.Data;

import java.util.List;

@Data
public class RoleResponseDto {
    private Long id;
    private String name;
    private String description;
    private List<PermissionResponseDto> permissions;
}
