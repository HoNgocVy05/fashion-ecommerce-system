package com.fashion.ecommerce.dto.category;

import lombok.Data;

@Data
public class CategoryRequestDto {

    private String name;
    
    private Long parentId;
}