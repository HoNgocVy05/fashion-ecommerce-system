package com.fashion.ecommerce.dto.product;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import lombok.Data;

@Data
public class ProductRequestDto {

    @NotBlank(message = "Tên sản phẩm không được để trống")
    private String name;

    private String description;

    private String thumbnail;

    @NotNull(message = "Giá sản phẩm không được để trống")
    @Positive(message = "Giá sản phẩm phải lớn hơn 0")
    private BigDecimal originalPrice;

    @Min(value = 0, message = "Giảm giá tối thiểu là 0")
    @Max(value = 100, message = "Giảm giá tối đa là 100")

    private Integer discountPercent = 0;

    private LocalDateTime discountStart;

    private LocalDateTime discountEnd;

    @NotBlank(message = "Danh mục không được để trống")
    private String categorySlug;

    private List<ProductImageDto> images;

    private List<ProductSizeDto> sizes;

}