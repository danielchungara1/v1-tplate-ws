package com.tplate.layers.access.controllers;

import com.tplate.layers.access.dtos.ResponseDto;
import com.tplate.layers.access.dtos.ResponsePageDto;
import com.tplate.layers.access.dtos.ResponseSimpleDto;
import com.tplate.layers.access.dtos.category.CategoryDto;
import com.tplate.layers.access.dtos.category.CategoryPageDto;
import com.tplate.layers.access.dtos.category.CategoryResponseDto;
import com.tplate.layers.access.dtos.role.RoleDto;
import com.tplate.layers.access.dtos.role.RoleResponseDto;
import com.tplate.layers.access.shared.Endpoints;
import com.tplate.layers.access.specifications.CategorySpecification;
import com.tplate.layers.business.exceptions.category.CategoryNameExistException;
import com.tplate.layers.business.exceptions.category.CategoryNotExistException;
import com.tplate.layers.business.exceptions.permission.PermissionNotExistException;
import com.tplate.layers.business.exceptions.role.RoleMustNotBeDeletedException;
import com.tplate.layers.business.exceptions.role.RoleNameExistException;
import com.tplate.layers.business.exceptions.role.RoleNotExistException;
import com.tplate.layers.business.exceptions.role.RoleWithoutPermissionsException;
import com.tplate.layers.business.services.CategoryService;
import com.tplate.layers.business.services.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping
public class CategoryController {

    @Autowired
    CategoryService service;

    @GetMapping(value = Endpoints.CATEGORY_READ_ALL)
    @PreAuthorize("hasAuthority('READ_CATEGORIES')")
    @Transactional
    public ResponseDto findAll() {

        return ResponseDto.builder()
                .message("All categories fetched.")
                .details("All categories fetched successfully.")
                .data(this.service.findAll(), CategoryResponseDto[].class)
                .build();
    }

    @PostMapping(value = Endpoints.CATEGORY_NEW)
    @PreAuthorize("hasAuthority('CREATE_CATEGORIES')")
    public ResponseDto create(@RequestBody(required = true) @Valid CategoryDto dto) throws CategoryNameExistException {

        return ResponseDto.builder()
                .message("Category created.")
                .details("Category was created successfully.")
                .data(this.service.saveModel(dto), CategoryResponseDto.class)
                .build();

    }

    @GetMapping(value = Endpoints.CATEGORY_READ_ONE)
    @PreAuthorize("hasAuthority('READ_CATEGORIES')")
    public ResponseDto getById(@PathVariable Long id) throws CategoryNotExistException {
        return ResponseDto.builder()
                .message("Category data.")
                .details("Category data found successfully.")
                .data(this.service.getModelById(id), CategoryResponseDto.class)
                .build();
    }

    @PutMapping(value = Endpoints.CATEGORY_UPDATE)
    @PreAuthorize("hasAuthority('UPDATE_CATEGORIES')")
    public ResponseDto update(@RequestBody(required = true) @Valid CategoryDto dto, @PathVariable Long id) throws CategoryNameExistException, CategoryNotExistException {
        return ResponseDto.builder()
                .message("Category updated.")
                .details("Category was updated successfully.")
                .data(this.service.updateModel(dto, id), CategoryResponseDto.class)
                .build();

    }

    @DeleteMapping(value = Endpoints.CATEGORY_DELETE)
    @PreAuthorize("hasAuthority('DELETE_CATEGORIES')")
    public ResponseSimpleDto delete(@PathVariable Long id) throws CategoryNotExistException {
        this.service.deleteModelById(id);
        return ResponseSimpleDto.builder()
                .message("Category deleted.")
                .details("The category was deleted successfully and all its descendant subcategories.")
                .build();
    }

    @GetMapping(value = Endpoints.CATEGORY)
    @PreAuthorize("hasAuthority('READ_CATEGORIES')")
    @Transactional
    public ResponseDto find(Pageable pageable, CategorySpecification specification) {

        return ResponseDto.builder()
                .message("Categories fetched.")
                .details("Categories fetched successfully.")
                .data(this.service.find(pageable, specification), CategoryPageDto.class)
                .build();
    }

}


