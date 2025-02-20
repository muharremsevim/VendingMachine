package com.aselsan.VendingMachine.Domain.Model;

import com.aselsan.VendingMachine.Domain.Annotation.AggregateRoot;
import com.aselsan.VendingMachine.Exception.BalanceNotEnoughException;
import com.aselsan.VendingMachine.Exception.ProductNotFoundException;
import com.aselsan.VendingMachine.Exception.VendingMachineOutOfOrderException;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
//@NoArgsConstructor
//@AllArgsConstructor
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

    public void startMaintenance() {
        status = VendingMachineStatus.OUT_OF_ORDER;
    }

    public void finishMaintenance() {
        status = VendingMachineStatus.RUNNING;
    }

    public Product getProduct(Long productId) {
        return products.stream()
                .filter(p -> p.getId().equals(productId))
                .findFirst()
                .orElseThrow(() -> new ProductNotFoundException("Product has unexpected errors"));
    }

    public Product dispenseProduct(Long productId) {
        validateOperational();

        Product product = getProduct(productId);

        if (!product.isAvailable()) {
            throw new ProductNotFoundException("Product is not available");
        }

        if (currentBalance < product.getPrice()) {
            throw new BalanceNotEnoughException("Insufficient balance");
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
            throw new VendingMachineOutOfOrderException(getId());
        }
    }
}