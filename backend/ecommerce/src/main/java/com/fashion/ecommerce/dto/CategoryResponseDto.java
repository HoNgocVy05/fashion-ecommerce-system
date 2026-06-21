package com.fashion.ecommerce.dto;

import java.util.List;

public class CategoryResponseDto {

    private Long id;
    private String name;
    private String slug;

    private Long parentId;

    private List<CategoryResponseDto> children;

    // getters setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getSlug() { return slug; }
    public void setSlug(String slug) { this.slug = slug; }

    public Long getParentId() { return parentId; }
    public void setParentId(Long parentId) { this.parentId = parentId; }

    public List<CategoryResponseDto> getChildren() { return children; }
    public void setChildren(List<CategoryResponseDto> children) { this.children = children; }
}