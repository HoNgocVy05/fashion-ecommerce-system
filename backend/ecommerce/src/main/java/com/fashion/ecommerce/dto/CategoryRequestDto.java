package com.fashion.ecommerce.dto;

public class CategoryRequestDto {

    private String name;
    
    private Long parentId;

    // getters setters
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public Long getParentId() { return parentId; }
    public void setParentId(Long parentId) { this.parentId = parentId; }
}