package com.aselsan.VendingMachine.Domain.Port;

import com.aselsan.VendingMachine.Domain.Model.VendingMachine;

import java.util.List;
import java.util.Optional;

public interface VendingMachineStore {
    Optional<VendingMachine> findById(Long id);

    List<VendingMachine> findAll();

    VendingMachine store(VendingMachine vendingMachine);
}