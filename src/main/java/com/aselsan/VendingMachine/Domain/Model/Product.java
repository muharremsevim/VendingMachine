package com.aselsan.VendingMachine.Domain.Model;

import com.aselsan.VendingMachine.Domain.Annotation.DomainEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@DomainEntity(description = "Represents a product that can be sold through the vending machine")
@Builder
@AllArgsConstructor
public class Product {
    private Long id;
    private String name;
    private Double price;
    private Integer quantity;
    private ProductStatus status;

    public boolean isAvailable() {
        return status == ProductStatus.AVAILABLE && quantity > 0;
    }

    public void decreaseQuantity() {
        if (quantity > 0) {
            quantity--;
            if (quantity == 0) {
                status = ProductStatus.OUT_OF_STOCK;
            }
        }
    }
} 