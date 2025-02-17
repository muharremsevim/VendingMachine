package com.aselsan.VendingMachine.Domain.Model;

import com.aselsan.VendingMachine.Domain.Annotation.ValueObject;

@ValueObject(description = "Represents the possible states of a product in the vending machine")
public enum ProductStatus {
    AVAILABLE,
    OUT_OF_STOCK
}