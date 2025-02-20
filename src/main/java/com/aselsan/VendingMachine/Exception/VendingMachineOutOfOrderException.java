package com.aselsan.VendingMachine.Exception;

public class VendingMachineOutOfOrderException extends RuntimeException {
    public VendingMachineOutOfOrderException(Long machineId) {
        super(String.valueOf(machineId));
    }
}
