package com.fashion.ecommerce.service;

import java.util.List;

import com.fashion.ecommerce.dto.category.CategoryRequestDto;
import com.fashion.ecommerce.dto.category.CategoryResponseDto;

public interface CategoryService {

    List<CategoryResponseDto> getAll();

    CategoryResponseDto getBySlug(String slug);

    CategoryResponseDto create(CategoryRequestDto dto);

    CategoryResponseDto update(String slug, CategoryRequestDto dto);

    void delete(String slug);
}