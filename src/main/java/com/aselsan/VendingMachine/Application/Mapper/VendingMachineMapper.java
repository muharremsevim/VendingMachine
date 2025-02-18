package com.aselsan.VendingMachine.Application.Mapper;

import com.aselsan.VendingMachine.Application.Dto.VendingMachineDto;
import com.aselsan.VendingMachine.Domain.Model.VendingMachine;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring", uses = {ProductMapper.class})
public interface VendingMachineMapper {
    VendingMachineMapper INSTANCE = Mappers.getMapper(VendingMachineMapper.class);

    VendingMachineDto toDto(VendingMachine vendingMachine);

    VendingMachine toEntity(VendingMachineDto vendingMachineDto);

    // Prevent returning vending machines together with products
    default List<VendingMachineDto> toDtoList(List<VendingMachine> machines) {
        if (machines == null) {
            return null;
        }
        return machines.stream()
                .map(machine -> VendingMachineDto.builder()
                        .id(machine.getId())
                        .serialNumber(machine.getSerialNumber())
                        .status(machine.getStatus())
                        .currentBalance(machine.getCurrentBalance())
                        .build())
                .collect(Collectors.toList());
    }

    List<VendingMachine> toEntityList(List<VendingMachineDto> vendingMachineDtoList);
}