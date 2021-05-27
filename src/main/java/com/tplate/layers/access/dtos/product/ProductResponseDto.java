package com.tplate.layers.access.dtos.product;

import com.tplate.layers.access.dtos.brand.BrandResponseDto;
import com.tplate.layers.access.dtos.category.CategoryResponseDto;
import lombok.Data;

@Data
public class ProductResponseDto {
    private Long id;
    private String name;
    private String description;
    private String title;
    private BrandResponseDto brand;
    private CategoryResponseDto category;
}
