package com.fashion.ecommerce.dto.category;

import java.util.List;

import lombok.Data;

@Data
public class CategoryResponseDto {

    private Long id;
    private String name;
    private String slug;
    private Long parentId;
    private List<CategoryResponseDto> children;
}