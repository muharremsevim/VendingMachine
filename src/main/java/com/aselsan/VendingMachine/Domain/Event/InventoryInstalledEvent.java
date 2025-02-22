package com.aselsan.VendingMachine.Domain.Event;

import com.aselsan.VendingMachine.Application.Dto.ProductDto;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

import java.util.List;

@Getter
public class InventoryInstalledEvent extends ApplicationEvent {
    private final Long machineId;
    private final List<ProductDto> productList;

    public InventoryInstalledEvent(Object source, Long machineId, List<ProductDto> productList) {
        super(source);
        this.machineId = machineId;
        this.productList = productList;
    }
}
