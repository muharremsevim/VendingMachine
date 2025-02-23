package com.aselsan.VendingMachine.Application.Service.Impl;

import com.aselsan.VendingMachine.Application.Dto.ProductDto;
import com.aselsan.VendingMachine.Application.Dto.VendingMachineDto;
import com.aselsan.VendingMachine.Application.Mapper.ProductMapper;
import com.aselsan.VendingMachine.Application.Mapper.VendingMachineMapper;
import com.aselsan.VendingMachine.Application.Service.VendingMachineService;
import com.aselsan.VendingMachine.Domain.Model.Money;
import com.aselsan.VendingMachine.Domain.Model.Product;
import com.aselsan.VendingMachine.Domain.Model.VendingMachine;
import com.aselsan.VendingMachine.Domain.Port.VendingMachineStore;
import com.aselsan.VendingMachine.Exception.VendingMachineNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class VendingMachineServiceImpl implements VendingMachineService {
    private final VendingMachineStore vendingMachineStore;
    private final ProductMapper productMapper;
    private final VendingMachineMapper vendingMachineMapper;

    @Override
    public VendingMachineDto getMachine(Long machineId) {
        VendingMachine machine = vendingMachineStore.findById(machineId)
                .orElseThrow(() -> new VendingMachineNotFoundException(machineId));
        return vendingMachineMapper.toDto(machine);
    }

    @Override
    public List<VendingMachineDto> getAllMachines() {
        return vendingMachineMapper.toDtoList(vendingMachineStore.findAll());
    }

    @Override
    public VendingMachineDto createMachine(VendingMachineDto vendingMachineDto) {
        VendingMachine machine = VendingMachine.createNew(vendingMachineDto.getSerialNumber());
        return vendingMachineMapper.toDto(vendingMachineStore.store(machine));
    }

    @Override
    @Transactional
    public Double insertMoney(Long machineId, Money money) {
        VendingMachine machine = vendingMachineStore.findById(machineId)
                .orElseThrow(() -> new VendingMachineNotFoundException(machineId));

        machine.insertMoney(money);
        Double currentBalance = machine.getCurrentBalance();

        vendingMachineStore.store(machine);
        return currentBalance;
    }

    @Override
    public ProductDto dispenseProduct(Long machineId, Long productId) {
        VendingMachine machine = vendingMachineStore.findById(machineId)
                .orElseThrow(() -> new VendingMachineNotFoundException(machineId));

        Product product = machine.dispenseProduct(productId);
        vendingMachineStore.store(machine);
        return productMapper.toDto(product);
    }

    @Override
    public Double refund(Long machineId) {
        VendingMachine machine = vendingMachineStore.findById(machineId)
                .orElseThrow(() -> new VendingMachineNotFoundException(machineId));

        Double refundAmount = machine.refund();
        vendingMachineStore.store(machine);

        return refundAmount;
    }

    @Override
    public List<ProductDto> retrieveItems(Long machineId) {
        VendingMachine machine = vendingMachineStore.findById(machineId)
                .orElseThrow(() -> new VendingMachineNotFoundException(machineId));

        List<Product> products = machine.getProducts();
        return productMapper.toDtoList(products);
    }

    @Override
    public ProductDto retrieveItem(Long machineId, Long productId) {
        VendingMachine machine = vendingMachineStore.findById(machineId)
                .orElseThrow(() -> new VendingMachineNotFoundException(machineId));

        return productMapper.toDto(machine.getProduct(productId));
    }

    @Override
    public void updateMachineStatus(Long machineId, boolean isRunning) {
        VendingMachine machine = vendingMachineStore.findById(machineId)
                .orElseThrow(() -> new VendingMachineNotFoundException(machineId));

        if (isRunning) {
            machine.finishMaintenance();
        } else {
            machine.startMaintenance();
        }
        vendingMachineStore.store(machine);
    }

    @Override
    @Transactional
    public void installInventory(Long machineId, List<ProductDto> productDtoList) {
        VendingMachine machine = vendingMachineStore.findById(machineId)
                .orElseThrow(() -> new VendingMachineNotFoundException(machineId));

        machine.startMaintenance();
        vendingMachineStore.store(machine);

        machine.getProducts().addAll(productMapper.toEntityList(productDtoList));

        machine.finishMaintenance();
        vendingMachineStore.store(machine);
    }
}