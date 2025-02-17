package com.aselsan.VendingMachine.Application.Dto;

import com.aselsan.VendingMachine.Domain.Model.ProductStatus;
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
    private String name;
    private Double price;
    private Integer quantity;
    private ProductStatus status;
}