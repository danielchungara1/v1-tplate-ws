package com.tplate.layers.access.controllers;

import com.tplate.layers.access.dtos.ResponseDto;
import com.tplate.layers.access.dtos.ResponseSimpleDto;
import com.tplate.layers.access.dtos.brand.BrandResponseDto;
import com.tplate.layers.access.dtos.role.RoleDto;
import com.tplate.layers.access.dtos.role.RoleResponseDto;
import com.tplate.layers.access.shared.Endpoints;
import com.tplate.layers.business.exceptions.*;
import com.tplate.layers.business.services.BrandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping
public class BrandController {

    @Autowired
    BrandService brandService;

    @GetMapping(value = Endpoints.BRAND_READ_ALL)
    @PreAuthorize("hasAuthority('READ_BRANDS')")
    @Transactional
    public ResponseDto findAll() {

        return ResponseDto.builder()
                .message("All brands fetched.")
                .details("All brands fetched successfully.")
                .data(this.brandService.findAll(), BrandResponseDto[].class)
                .build();
    }

//    @PostMapping(value = Endpoints.ROLE_NEW)
//    @PreAuthorize("hasAuthority('CREATE_ROLES')")
//    public ResponseDto createRole(@RequestBody(required = true) @Valid RoleDto roleDto) throws RoleNameExistException, RoleWithoutPermissionsException, PermissionNotExistException {
//
//        return ResponseDto.builder()
//                .message("Role created.")
//                .details("Role was created successfully.")
//                .data(this.brandService.saveModel(roleDto), RoleResponseDto.class)
//                .build();
//
//    }
//
//    @GetMapping(value = Endpoints.ROLE_READ_ONE)
//    @PreAuthorize("hasAuthority('READ_USERS')")
//    public ResponseDto getRoleById(@PathVariable Long id) throws RoleNotExistException {
//        return ResponseDto.builder()
//                .message("Role data.")
//                .details("Role data found successfully.")
//                .data(this.brandService.getModelById(id), RoleResponseDto.class)
//                .build();
//    }
//
//    @PutMapping(value = Endpoints.ROLE_UPDATE)
//    @PreAuthorize("hasAuthority('UPDATE_ROLES')")
//    public ResponseDto updateRole(@RequestBody(required = true) @Valid RoleDto roleDto, @PathVariable Long id) throws RoleWithoutPermissionsException, RoleNotExistException, RoleNameExistException, PermissionNotExistException {
//        return ResponseDto.builder()
//                .message("Role updated.")
//                .details("Role was updated successfully.")
//                .data(this.brandService.updateModel(roleDto, id), RoleResponseDto.class)
//                .build();
//
//    }
//
//    @DeleteMapping(value = Endpoints.ROLE_DELETE)
//    @PreAuthorize("hasAuthority('DELETE_ROLES')")
//    public ResponseSimpleDto deleteRole(@PathVariable Long id) throws RoleNotExistException, RoleMustNotBeDeletedException {
//        this.brandService.deleteModelById(id);
//        return ResponseSimpleDto.builder()
//                .message("Role deleted.")
//                .details("The role was deleted successfully.")
//                .build();
//    }

}


