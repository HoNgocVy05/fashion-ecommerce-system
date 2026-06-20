package com.fashion.ecommerce.controller;


import java.util.List;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.fashion.ecommerce.dto.CategoryDto;
import com.fashion.ecommerce.service.CategoryService;



@RestController
@RequestMapping("/api/categories")
public class CategoryController {



    private final CategoryService service;



    public CategoryController(
            CategoryService service
    ){
        this.service = service;
    }


    @GetMapping
    public List<CategoryDto> getAll(){

        return service.getAll();

    }

    @PostMapping
    @PreAuthorize(
            "hasAnyRole('ADMIN','SYSTEM_ADMIN')"
    )
    public CategoryDto create(
            @RequestBody CategoryDto dto
    ){

        return service.create(dto);

    }


    @PutMapping("/{id}")
    @PreAuthorize(
            "hasAnyRole('ADMIN','SYSTEM_ADMIN')"
    )
    public CategoryDto update(
            @PathVariable Integer id,
            @RequestBody CategoryDto dto
    ){

        return service.update(id,dto);

    }


    @DeleteMapping("/{id}")
    @PreAuthorize(
            "hasAnyRole('ADMIN','SYSTEM_ADMIN')"
    )
    public void delete(
            @PathVariable Integer id
    ){

        service.delete(id);

    }


}