package com.fashion.ecommerce.dto.product;

import java.math.BigDecimal;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.PositiveOrZero;

import lombok.Data;

@Data
public class ProductFilterRequestDto {

    private String keyword;

    private String categorySlug;

    @PositiveOrZero(message = "Giá tối thiểu phải >= 0")
    private BigDecimal minPrice;

    @PositiveOrZero(message = "Giá tối đa phải >= 0")
    private BigDecimal maxPrice;

    private Boolean sale;

    private String sort = "newest";

    @Min(value = 0, message = "Page phải >= 0")
    private Integer page = 0;

    @Min(value = 1, message = "Size phải >= 1")
    @Max(value = 100, message = "Size tối đa là 100")
    private Integer size = 12;

}