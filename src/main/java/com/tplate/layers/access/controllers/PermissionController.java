package com.tplate.layers.access.controllers;

import com.tplate.layers.access.dtos.ResponseDto;
import com.tplate.layers.access.dtos.permission.PermissionPageDto;
import com.tplate.layers.access.dtos.permission.PermissionResponseDto;
import com.tplate.layers.access.filters.SearchText;
import com.tplate.layers.access.shared.Endpoints;
import com.tplate.layers.business.services.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
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

    @GetMapping(value = Endpoints.PERMISSION_READ_ALL)
    @PreAuthorize("hasAuthority('READ_PERMISSIONS')")
    @Transactional
    public ResponseDto findAll() {

        return ResponseDto.builder()
                .message("All permissions fetched.")
                .details("All permissions fetched successfully.")
                .data(this.permissionService.findAll(), PermissionResponseDto[].class)
                .build();
    }

    @GetMapping(value = Endpoints.PERMISSION)
    @PreAuthorize("hasAuthority('READ_PERMISSIONS')")
    @Transactional
    public ResponseDto find(Pageable pageable, SearchText searchText) {

        return ResponseDto.builder()
                .message("Permissions fetched.")
                .details("Permissions fetched successfully.")
                .data(this.permissionService.find(pageable, searchText), PermissionPageDto.class)
                .build();
    }


}
