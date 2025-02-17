package com.aselsan.VendingMachine.Application.Mapper;

import com.aselsan.VendingMachine.Application.Dto.VendingMachineDto;
import com.aselsan.VendingMachine.Domain.Model.VendingMachine;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring", uses = {ProductMapper.class})
public interface VendingMachineMapper {
    VendingMachineMapper INSTANCE = Mappers.getMapper(VendingMachineMapper.class);

    VendingMachineDto toDto(VendingMachine vendingMachine);

    VendingMachine toEntity(VendingMachineDto vendingMachineDto);

    List<VendingMachineDto> toDTOList(List<VendingMachine> vendingMachineList);

    List<VendingMachine> toEntityList(List<VendingMachineDto> vendingMachineDtoList);
}