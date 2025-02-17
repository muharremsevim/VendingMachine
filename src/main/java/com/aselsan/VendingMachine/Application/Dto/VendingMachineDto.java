package com.aselsan.VendingMachine.Application.Dto;

import com.aselsan.VendingMachine.Domain.Model.VendingMachineStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VendingMachineDto {
    private Long id;
    private String serialNumber;
    private VendingMachineStatus status;
    private Double currentBalance;
    private List<ProductDto> products;

    public Long getId() {
        return id;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public VendingMachineStatus getStatus() {
        return status;
    }

    public Double getCurrentBalance() {
        return currentBalance;
    }

    public List<ProductDto> getProducts() {
        return products;
    }
}
