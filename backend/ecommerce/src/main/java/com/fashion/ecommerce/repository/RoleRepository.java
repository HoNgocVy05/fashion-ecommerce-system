package com.fashion.ecommerce.repository;
import org.springframework.data.jpa.repository.JpaRepository;

import com.fashion.ecommerce.entity.RoleEntity;

public interface RoleRepository extends JpaRepository<RoleEntity, Integer> {
    RoleEntity findByName(String name);
}