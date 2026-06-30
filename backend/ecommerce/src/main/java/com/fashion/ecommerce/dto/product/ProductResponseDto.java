package com.fashion.ecommerce.dto.product;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import lombok.Data;

@Data
public class ProductResponseDto {

    private Long id;

    private String name;

    private String slug;

    private String description;

    private String thumbnail;

    private BigDecimal originalPrice;

    private BigDecimal salePrice;

    private Integer discountPercent;

    private Boolean sale;
    
    private String categoryName;

    private String categorySlug;

    private Integer totalQuantity;

    private List<ProductImageDto> images;

    private List<ProductSizeDto> sizes;

    private LocalDateTime createdAt;

}