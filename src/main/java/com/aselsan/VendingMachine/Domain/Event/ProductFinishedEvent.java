package com.aselsan.VendingMachine.Domain.Event;

import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
public class ProductFinishedEvent extends ApplicationEvent {
    private final Long machineId;
    private final Long productId;

    public ProductFinishedEvent(Object source, Long machineId, Long productId) {
        super(source);
        this.machineId = machineId;
        this.productId = productId;
    }
}
