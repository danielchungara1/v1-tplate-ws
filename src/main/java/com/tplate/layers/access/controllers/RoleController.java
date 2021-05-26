package com.tplate.layers.access.controllers;

import com.tplate.layers.access.dtos.ResponseDto;
import com.tplate.layers.access.dtos.role.RoleDto;
import com.tplate.layers.access.dtos.role.RoleResponseDto;
import com.tplate.layers.access.dtos.user.UserResponseDto;
import com.tplate.layers.access.dtos.user.UserUpdateDto;
import com.tplate.layers.access.shared.Endpoints;
import com.tplate.layers.business.exceptions.*;
import com.tplate.layers.business.services.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping
public class RoleController {

    @Autowired
    RoleService roleService;

    @GetMapping(value = Endpoints.ROLE_READ_ALL)
    @PreAuthorize("hasAuthority('READ_ROLES')")
    @Transactional
    public ResponseDto findAll() {

        return ResponseDto.builder()
                .message("All roles fetched.")
                .details("All roles fetched successfully.")
                .data(this.roleService.findAll(), RoleResponseDto[].class)
                .build();
    }

    @PostMapping(value = Endpoints.ROLE_NEW)
    @PreAuthorize("hasAuthority('CREATE_ROLES')")
    public ResponseDto createRole(@RequestBody(required = true) @Valid RoleDto roleDto) throws RoleNameExistException, RoleWithoutPermissionsException, PermissionNotExistException {

        return ResponseDto.builder()
                .message("Role created.")
                .details("Role was created successfully.")
                .data(this.roleService.saveModel(roleDto), RoleResponseDto.class)
                .build();

    }

    @GetMapping(value = Endpoints.ROLE_READ_ONE)
    @PreAuthorize("hasAuthority('READ_USERS')")
    public ResponseDto getUserById(@PathVariable Long id) throws RoleNotExistException {
        return ResponseDto.builder()
                .message("Role data.")
                .details("Role data found successfully.")
                .data(this.roleService.getModelById(id), RoleResponseDto.class)
                .build();
    }

    @PutMapping(value = Endpoints.ROLE_UPDATE)
    @PreAuthorize("hasAuthority('UPDATE_ROLES')")
    public ResponseDto updateUser(@RequestBody(required = true) @Valid RoleDto roleDto, @PathVariable Long id) throws RoleWithoutPermissionsException, RoleNotExistException, RoleNameExistException, PermissionNotExistException {
        return ResponseDto.builder()
                .message("Role updated.")
                .details("Role was updated successfully.")
                .data(this.roleService.updateModel(roleDto, id), RoleResponseDto.class)
                .build();

    }

}
