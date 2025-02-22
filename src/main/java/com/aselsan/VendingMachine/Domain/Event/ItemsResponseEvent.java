package com.aselsan.VendingMachine.Domain.Event;

import com.aselsan.VendingMachine.Application.Dto.ProductDto;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

import java.util.List;

@Getter
public class ItemsResponseEvent extends ApplicationEvent {
    private final List<ProductDto> items;
    private final Long machineId;

    public ItemsResponseEvent(Object source, Long machineId, List<ProductDto> items) {
        super(source);
        this.machineId = machineId;
        this.items = items;
    }
}
