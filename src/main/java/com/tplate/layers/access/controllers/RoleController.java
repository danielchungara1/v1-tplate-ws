package com.tplate.layers.access.controllers;

import com.tplate.layers.access.dtos.ResponseDto;
import com.tplate.layers.access.dtos.role.RoleResponseDto;
import com.tplate.layers.access.shared.Endpoints;
import com.tplate.layers.business.services.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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


}
