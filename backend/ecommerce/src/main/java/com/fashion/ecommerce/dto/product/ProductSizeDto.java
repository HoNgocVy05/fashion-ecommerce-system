package com.fashion.ecommerce.dto.product;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data 
public class ProductSizeDto {

    @NotBlank(message = "Kích cỡ (Size) không được để trống")
    private String size;

    @NotNull(message = "Số lượng không được để trống")
    @Min(value = 0, message = "Số lượng sản phẩm tối thiểu phải bằng 0")
    private Integer quantity; 
}