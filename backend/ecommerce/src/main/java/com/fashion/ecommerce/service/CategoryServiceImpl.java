package com.fashion.ecommerce.service;

import java.text.Normalizer;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.fashion.ecommerce.dto.category.CategoryRequestDto;
import com.fashion.ecommerce.dto.category.CategoryResponseDto;
import com.fashion.ecommerce.entity.CategoryEntity;
import com.fashion.ecommerce.exception.ApiException;
import com.fashion.ecommerce.repository.CategoryRepository;

@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository repo;

    public CategoryServiceImpl(CategoryRepository repo) {
        this.repo = repo;
    }

    // get all category
    @Override
    public List<CategoryResponseDto> getAll() {
        return repo.findAll().stream().map(this::convert).toList();
    }

    // get category
    @Override
    public CategoryResponseDto getBySlug(String slug) {
        CategoryEntity c = repo.findBySlug(slug)
                .orElseThrow(() -> new ApiException("Không tìm thấy danh mục", HttpStatus.NOT_FOUND));
        return convert(c);
    }

    // create category
    @Override
    public CategoryResponseDto create(CategoryRequestDto dto) {

        CategoryEntity c = new CategoryEntity();
        c.setName(dto.getName());
        c.setSlug(generateUniqueSlug(dto.getName()));

        if (dto.getParentId() != null) {
            CategoryEntity parent = repo.findById(dto.getParentId())
                    .orElseThrow(() -> new ApiException("Không tìm thấy danh mục cha", HttpStatus.NOT_FOUND));
            c.setParent(parent);
        }

        return convert(repo.save(c));
    }

    // update category
    @Override
    public CategoryResponseDto update(String slug, CategoryRequestDto dto) {

        CategoryEntity c = repo.findBySlug(slug)
                .orElseThrow(() -> new ApiException("Không tìm thấy danh mục", HttpStatus.NOT_FOUND));

        // update name + regenerate slug if name changed
        if (dto.getName() != null && !dto.getName().equals(c.getName())) {
            c.setName(dto.getName());
            c.setSlug(generateUniqueSlug(dto.getName()));
        }

        // update parent category
        if (dto.getParentId() != null) {
            CategoryEntity parent = repo.findById(dto.getParentId())
                    .orElseThrow(() -> new ApiException("Không tìm thấy danh mục cha", HttpStatus.NOT_FOUND));
            c.setParent(parent);
        } else {
            c.setParent(null);
        }

        return convert(repo.save(c));
    }

    // delete category
    @Override
    public void delete(String slug) {

        CategoryEntity c = repo.findBySlug(slug)
                .orElseThrow(() -> new ApiException("Không tìm thấy danh mục", HttpStatus.NOT_FOUND));

        repo.delete(c);
    }

    // convert entity to response dto
    private CategoryResponseDto convert(CategoryEntity c) {

        CategoryResponseDto dto = new CategoryResponseDto();

        dto.setId(c.getId());
        dto.setName(c.getName());
        dto.setSlug(c.getSlug());

        if (c.getParent() != null) {
            dto.setParentId(c.getParent().getId());
        }

        return dto;
    }

    // generate unique slug
    private String generateUniqueSlug(String name) {

        String baseSlug = toSlug(name);
        String slug = baseSlug;
        int i = 1;

        while (repo.existsBySlug(slug)) {
            slug = baseSlug + "-" + i++;
        }

        return slug;
    }

    // convert string to slug
    private String toSlug(String input) {

        String normalized = Normalizer.normalize(input, Normalizer.Form.NFD);

        String slug = normalized.replaceAll("\\p{InCombiningDiacriticalMarks}+", "");

        slug = slug.toLowerCase()
                .replaceAll("đ", "d")
                .replaceAll("[^a-z0-9\\s-]", "")
                .trim()
                .replaceAll("\\s+", "-");

        return slug;
    }
}