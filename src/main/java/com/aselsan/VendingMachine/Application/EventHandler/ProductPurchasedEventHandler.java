package com.aselsan.VendingMachine.Application.EventHandler;

import com.aselsan.VendingMachine.Application.Dto.ProductDto;
import com.aselsan.VendingMachine.Application.Service.VendingMachineService;
import com.aselsan.VendingMachine.Domain.Event.ProductPurchasedEvent;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class ProductPurchasedEventHandler {
    private final Logger logger = LoggerFactory.getLogger(ProductPurchasedEventHandler.class);
    private final VendingMachineService vendingMachineService;

    @EventListener
    @Transactional
    public ProductDto handleProductPurchased(ProductPurchasedEvent event) {
        try {
            return vendingMachineService.dispenseProduct(event.getMachineId(), event.getProductId());
        } catch (Exception e) {
            logger.error("Error processing dispense product {} for machine {}: {}",
                    event.getProductId(),
                    event.getMachineId(),
                    e.getMessage()
            );
            throw e;
        }
    }

}
