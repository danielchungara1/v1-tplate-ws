package com.tplate.layers.access.controllers;

import com.tplate.layers.access.dtos.ResponseDto;
import com.tplate.layers.access.dtos.permission.PermissionResponseDto;
import com.tplate.layers.access.dtos.role.RoleResponseDto;
import com.tplate.layers.access.shared.Endpoints;
import com.tplate.layers.business.services.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping
public class PermissionController {

    @Autowired
    PermissionService permissionService;

    @GetMapping(value = Endpoints.PERMISSIONS_READ_ALL)
    @PreAuthorize("hasAuthority('READ_PERMISSIONS')")
    @Transactional
    public ResponseDto findAll() {

        return ResponseDto.builder()
                .message("All permissions fetched.")
                .details("All permissions fetched successfully.")
                .data(this.permissionService.findAll(), PermissionResponseDto[].class)
                .build();
    }


}
