package com.aselsan.VendingMachine.Exception;

public class VendingMachineNotFoundException extends RuntimeException {
    public VendingMachineNotFoundException(Long machineId) {
        super(String.valueOf(machineId));
    }
}
