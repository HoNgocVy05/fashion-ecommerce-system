package com.fashion.ecommerce.controller;

import java.util.List;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fashion.ecommerce.dto.category.CategoryRequestDto;
import com.fashion.ecommerce.dto.category.CategoryResponseDto;
import com.fashion.ecommerce.service.CategoryService;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {

    private final CategoryService service;

    public CategoryController(CategoryService service) {
        this.service = service;
    }

    @GetMapping
    public List<CategoryResponseDto> getAll() {
        return service.getAll();
    }

    @GetMapping("/{slug}")
    public CategoryResponseDto getBySlug(@PathVariable String slug) {
        return service.getBySlug(slug);
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN','SYSTEM_ADMIN')")
    public CategoryResponseDto create(@RequestBody CategoryRequestDto dto) {
        return service.create(dto);
    }

    @PutMapping("/{slug}")
    @PreAuthorize("hasAnyRole('ADMIN','SYSTEM_ADMIN')")
    public CategoryResponseDto update(@PathVariable String slug, @RequestBody CategoryRequestDto dto) {
        return service.update(slug, dto);
    }

    @DeleteMapping("/{slug}")
    @PreAuthorize("hasAnyRole('ADMIN','SYSTEM_ADMIN')")
    public void delete(@PathVariable String slug) {
        service.delete(slug);
    }
}