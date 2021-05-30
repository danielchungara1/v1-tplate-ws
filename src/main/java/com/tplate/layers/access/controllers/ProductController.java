package com.tplate.layers.access.controllers;

import com.tplate.layers.access.dtos.ResponseDto;
import com.tplate.layers.access.dtos.ResponseSimpleDto;
import com.tplate.layers.access.dtos.product.ProductDto;
import com.tplate.layers.access.dtos.product.ProductPageDto;
import com.tplate.layers.access.dtos.product.ProductResponseDto;
import com.tplate.layers.access.shared.Endpoints;
import com.tplate.layers.access.specifications.ProductSpecification;
import com.tplate.layers.business.exceptions.product.ProductNameExistException;
import com.tplate.layers.business.exceptions.product.ProductNotExistException;
import com.tplate.layers.business.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping
public class ProductController {

    @Autowired
    ProductService service;

    @GetMapping(value = Endpoints.PRODUCT_READ_ALL)
    @PreAuthorize("hasAuthority('READ_PRODUCTS')")
    @Transactional
    public ResponseDto findAll() {

        return ResponseDto.builder()
                .message("All products fetched.")
                .details("All products fetched successfully.")
                .data(this.service.findAll(), ProductResponseDto[].class)
                .build();
    }

    @PostMapping(value = Endpoints.PRODUCT_NEW)
    @PreAuthorize("hasAuthority('CREATE_PRODUCTS')")
    public ResponseDto create(@RequestBody(required = true) @Valid ProductDto dto) throws ProductNameExistException {

        return ResponseDto.builder()
                .message("Product created.")
                .details("Product was created successfully.")
                .data(this.service.saveModel(dto), ProductResponseDto.class)
                .build();

    }

    @GetMapping(value = Endpoints.PRODUCT_READ_ONE)
    @PreAuthorize("hasAuthority('READ_PRODUCTS')")
    public ResponseDto getById(@PathVariable Long id) throws ProductNotExistException {
        return ResponseDto.builder()
                .message("Product data.")
                .details("Product data found successfully.")
                .data(this.service.getModelById(id), ProductResponseDto.class)
                .build();
    }

    @PutMapping(value = Endpoints.PRODUCT_UPDATE)
    @PreAuthorize("hasAuthority('UPDATE_PRODUCTS')")
    public ResponseDto update(@RequestBody(required = true) @Valid ProductDto dto, @PathVariable Long id) throws ProductNameExistException, ProductNotExistException {
        return ResponseDto.builder()
                .message("Product updated.")
                .details("Product was updated successfully.")
                .data(this.service.updateModel(dto, id), ProductResponseDto.class)
                .build();

    }

    @DeleteMapping(value = Endpoints.PRODUCT_DELETE)
    @PreAuthorize("hasAuthority('DELETE_PRODUCTS')")
    public ResponseSimpleDto delete(@PathVariable Long id) throws ProductNotExistException {
        this.service.deleteModelById(id);
        return ResponseSimpleDto.builder()
                .message("Product deleted.")
                .details("The product was deleted successfully.")
                .build();
    }

    @GetMapping(value = Endpoints.PRODUCT)
    @PreAuthorize("hasAuthority('READ_PRODUCTS')")
    @Transactional
    public ResponseDto find(Pageable pageable, ProductSpecification specification) {

        return ResponseDto.builder()
                .message("Products fetched.")
                .details("Products fetched successfully.")
                .data(this.service.findAll(pageable, specification), ProductPageDto.class)
                .build();
    }

}


