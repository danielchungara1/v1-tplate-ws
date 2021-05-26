package com.tplate.layers.access.controllers;

import com.tplate.layers.access.dtos.ResponseDto;
import com.tplate.layers.access.dtos.ResponseSimpleDto;
import com.tplate.layers.access.dtos.brand.BrandDto;
import com.tplate.layers.access.dtos.brand.BrandResponseDto;
import com.tplate.layers.access.dtos.role.RoleDto;
import com.tplate.layers.access.dtos.role.RoleResponseDto;
import com.tplate.layers.access.shared.Endpoints;
import com.tplate.layers.business.exceptions.*;
import com.tplate.layers.business.exceptions.brand.BrandNameExistException;
import com.tplate.layers.business.exceptions.brand.BrandNotExistException;
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

    @PostMapping(value = Endpoints.BRAND_NEW)
    @PreAuthorize("hasAuthority('CREATE_BRANDS')")
    public ResponseDto createBrand(@RequestBody(required = true) @Valid BrandDto brandDto) throws BrandNameExistException {

        return ResponseDto.builder()
                .message("Brand created.")
                .details("Brand was created successfully.")
                .data(this.brandService.saveModel(brandDto), BrandResponseDto.class)
                .build();

    }

    @GetMapping(value = Endpoints.BRAND_READ_ONE)
    @PreAuthorize("hasAuthority('READ_BRANDS')")
    public ResponseDto getBrandById(@PathVariable Long id) throws BrandNotExistException {
        return ResponseDto.builder()
                .message("Brand data.")
                .details("Brand data found successfully.")
                .data(this.brandService.getModelById(id), BrandResponseDto.class)
                .build();
    }

    @PutMapping(value = Endpoints.BRAND_UPDATE)
    @PreAuthorize("hasAuthority('UPDATE_BRANDS')")
    public ResponseDto updateRole(@RequestBody(required = true) @Valid BrandDto dto, @PathVariable Long id) throws BrandNotExistException, BrandNameExistException {
        return ResponseDto.builder()
                .message("Brand updated.")
                .details("Brand was updated successfully.")
                .data(this.brandService.updateModel(dto, id), BrandResponseDto.class)
                .build();

    }

    @DeleteMapping(value = Endpoints.BRAND_DELETE)
    @PreAuthorize("hasAuthority('DELETE_BRANDS')")
    public ResponseSimpleDto delete(@PathVariable Long id) throws BrandNotExistException {
        this.brandService.deleteModelById(id);
        return ResponseSimpleDto.builder()
                .message("Brand deleted.")
                .details("The brand was deleted successfully.")
                .build();
    }

}


