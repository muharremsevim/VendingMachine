package com.aselsan.VendingMachine.Domain.Model;

import com.aselsan.VendingMachine.Domain.Annotation.AggregateRoot;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@AggregateRoot(description = "Represents a vending machine that manages products and transactions")
public class VendingMachine {
    private Long id;
    private String serialNumber;
    private VendingMachineStatus status;
    private Double currentBalance;
    @Builder.Default
    private List<Product> products = new ArrayList<>();

    public static VendingMachine createNew(String serialNumber) {
        return VendingMachine.builder()
                .serialNumber(serialNumber)
                .status(VendingMachineStatus.RUNNING)
                .currentBalance(Double.valueOf(0))
                .products(new ArrayList<>())
                .build();
    }

    public void insertMoney(Money money) {
        validateOperational();
        currentBalance += money.getValue();
    }

    public Product dispenseProduct(Long productId) {
        validateOperational();

        Product product = products.stream()
                .filter(p -> p.getId().equals(productId))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Product not found"));

        if (!product.isAvailable()) {
            throw new IllegalStateException("Product is not available");
        }

        if (currentBalance < product.getPrice()) {
            throw new IllegalStateException("Insufficient balance");
        }

        currentBalance -= product.getPrice();
        product.decreaseQuantity();
        return product;
    }

    public Double refund() {
        Double refundAmount = currentBalance;
        currentBalance = Double.valueOf(0);
        return refundAmount;
    }

    private void validateOperational() {
        if (status != VendingMachineStatus.RUNNING) {
            throw new IllegalStateException("Vending machine is not operational");
        }
    }
}