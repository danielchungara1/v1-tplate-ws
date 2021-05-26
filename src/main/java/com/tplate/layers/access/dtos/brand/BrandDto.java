package com.tplate.layers.access.dtos.brand;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class BrandDto {

    @NotBlank(message = "name is required")
    private String name;

    @NotBlank(message = "title is required")
    private String title;

    @NotBlank(message = "description is required")
    private String description;



}
