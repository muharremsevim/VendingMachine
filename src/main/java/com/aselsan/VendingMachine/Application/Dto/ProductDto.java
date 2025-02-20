package com.aselsan.VendingMachine.Application.Dto;

import com.aselsan.VendingMachine.Domain.Model.ProductStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductDto {
    private Long id;
    @NotBlank(message = "Name is required")
    private String name;
    @Positive(message = "Price must be positive")
    @NotNull(message = "Price is required")
    private Double price;
    @PositiveOrZero(message = "Quantity must be zero or positive")
    @NotNull(message = "Quantity is required")
    private Integer quantity;
    @NotNull(message = "Status is required")
    private ProductStatus status;
}