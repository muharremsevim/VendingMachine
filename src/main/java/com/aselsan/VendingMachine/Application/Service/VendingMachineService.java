package com.aselsan.VendingMachine.Application.Service;

import com.aselsan.VendingMachine.Application.Dto.ProductDto;
import com.aselsan.VendingMachine.Application.Dto.VendingMachineDto;
import com.aselsan.VendingMachine.Domain.Model.Money;

import java.util.List;

public interface VendingMachineService {

    VendingMachineDto getMachine(Long machineId);

    List<VendingMachineDto> getAllMachines();

    VendingMachineDto createMachine(VendingMachineDto vendingMachineDto);

    Double insertMoney(Long machineId, Money money);

    ProductDto dispenseProduct(Long machineId, Long productId);

    Double refund(Long machineId);

    List<ProductDto> retrieveItems(Long machineId);

    ProductDto retrieveItem(Long machineId, Long productId);

    void updateMachineStatus(Long machineId, boolean isRunning);

    void installInventory(Long machineId, List<ProductDto> productDtoList);
}
