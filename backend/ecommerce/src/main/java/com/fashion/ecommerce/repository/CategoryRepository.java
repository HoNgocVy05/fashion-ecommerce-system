package com.fashion.ecommerce.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import com.fashion.ecommerce.entity.CategoryEntity;


public interface CategoryRepository
        extends JpaRepository<CategoryEntity,Integer>{


}