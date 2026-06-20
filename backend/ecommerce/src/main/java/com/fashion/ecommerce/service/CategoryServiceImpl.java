package com.fashion.ecommerce.service;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.fashion.ecommerce.dto.CategoryDto;
import com.fashion.ecommerce.entity.CategoryEntity;
import com.fashion.ecommerce.exception.ApiException;
import com.fashion.ecommerce.repository.CategoryRepository;


@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository repo;

    public CategoryServiceImpl(CategoryRepository repo){
        this.repo = repo;
    }


    @Override
    public List<CategoryDto> getAll(){
        return repo.findAll()
                .stream()
                .map(this::convert)
                .toList();
    }


    @Override
    public CategoryDto create(CategoryDto dto){

        CategoryEntity c = new CategoryEntity();

        c.setName(dto.getName());
        c.setDescription(dto.getDescription());

        if(dto.getParentId() != null){

            CategoryEntity parent =
                    repo.findById(dto.getParentId())
                            .orElseThrow(
                                    () -> new ApiException(
                                            "Không tìm thấy danh mục cha",
                                            HttpStatus.NOT_FOUND
                                    )
                            );

            c.setParent(parent);
        }

        return convert(repo.save(c));
    }


    @Override
    public CategoryDto update(
            Integer id,
            CategoryDto dto
    ){

        CategoryEntity c =
                repo.findById(id)
                        .orElseThrow(
                                () -> new ApiException(
                                        "Không tìm thấy danh mục",
                                        HttpStatus.NOT_FOUND
                                )
                        );

        c.setName(dto.getName());
        c.setDescription(dto.getDescription());

        return convert(repo.save(c));
    }


    @Override
    public void delete(Integer id){

        CategoryEntity c =
                repo.findById(id)
                        .orElseThrow(
                                () -> new ApiException(
                                        "Không tìm thấy danh mục",
                                        HttpStatus.NOT_FOUND
                                )
                        );

        repo.delete(c);
    }


    private CategoryDto convert(CategoryEntity c){

        CategoryDto dto = new CategoryDto();

        dto.setId(c.getId());
        dto.setName(c.getName());
        dto.setDescription(c.getDescription());

        if(c.getParent() != null){
            dto.setParentId(
                    c.getParent().getId()
            );
        }

        return dto;
    }
}