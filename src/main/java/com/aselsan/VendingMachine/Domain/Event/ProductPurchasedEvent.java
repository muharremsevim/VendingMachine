package com.aselsan.VendingMachine.Domain.Event;

import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
public class ProductPurchasedEvent extends ApplicationEvent {
    private final Long machineId;
    private final Long productId;

    public ProductPurchasedEvent(Object source, Long machineId, Long productId) {
        super(source);
        this.machineId = machineId;
        this.productId = productId;
    }
}
