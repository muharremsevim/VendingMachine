package com.aselsan.VendingMachine.Domain.Event;

import com.aselsan.VendingMachine.Application.Dto.ProductDto;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
public class ProductPurchasedResponseEvent extends ApplicationEvent {
    private final Long machineId;
    private final ProductDto product;

    public ProductPurchasedResponseEvent(Object source, Long machineId, ProductDto product) {
        super(source);
        this.machineId = machineId;
        this.product = product;
    }
}
