package com.fashion.ecommerce.service;


import java.util.List;
import com.fashion.ecommerce.dto.CategoryDto;


public interface CategoryService {


    List<CategoryDto> getAll();


    CategoryDto create(CategoryDto dto);


    CategoryDto update(
            Integer id,
            CategoryDto dto
    );


    void delete(Integer id);

}