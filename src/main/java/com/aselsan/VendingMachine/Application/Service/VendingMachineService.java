package com.aselsan.VendingMachine.Application.Service;

import com.aselsan.VendingMachine.Application.Dto.ProductDto;
import com.aselsan.VendingMachine.Application.Dto.VendingMachineDto;
import com.aselsan.VendingMachine.Domain.Model.Money;

import java.util.List;

public interface VendingMachineService {
    VendingMachineDto createMachine(VendingMachineDto vendingMachineDto);

    void insertMoney(Long machineId, Money money);

    ProductDto dispenseProduct(Long machineId, Long productId);

    Double refund(Long machineId);

    List<ProductDto> retrieveItems(Long id);
}
