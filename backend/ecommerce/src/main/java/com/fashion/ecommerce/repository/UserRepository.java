package com.fashion.ecommerce.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fashion.ecommerce.entity.UserEntity;

public interface UserRepository extends JpaRepository<UserEntity, Integer> {

    UserEntity findByEmail(String email);
    UserEntity findByProviderAndProviderId(String provider, String providerId);
}