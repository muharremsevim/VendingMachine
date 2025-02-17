package com.aselsan.VendingMachine.Domain.Model;

import com.aselsan.VendingMachine.Domain.Annotation.ValueObject;

@ValueObject(description = "Represents the possible states of a vending machine")
public enum VendingMachineStatus {
    OUT_OF_ORDER,
    RUNNING,
    NO_CHANGE_AVAILABLE;
}