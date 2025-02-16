package com.aselsan.VendingMachine.Domain;

public enum VendingMachineStatus {
    OUT_OF_ORDER(0),
    RUNNING(1),
    NO_CHANGE_AVAILABLE(2);

    private final int value;

    VendingMachineStatus(int value) {
        this.value = value;
    }
} 