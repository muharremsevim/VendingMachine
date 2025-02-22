package com.aselsan.VendingMachine.Application.EventHandler;

import com.aselsan.VendingMachine.Application.Service.VendingMachineService;
import com.aselsan.VendingMachine.Domain.Event.InventoryInstalledEvent;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class InventoryInstalledEventHandler {
    private final Logger logger = LoggerFactory.getLogger(InventoryInstalledEventHandler.class);
    private final VendingMachineService vendingMachineService;

    @EventListener
    @Transactional
    public void handleInventoryInstalled(InventoryInstalledEvent event) {
        try {
            vendingMachineService.installInventory(event.getMachineId(), event.getProductList());
        } catch (Exception e) {
            logger.error("Error processing install inventory for machine {}: {}",
                    event.getMachineId(),
                    e.getMessage()
            );
            throw e;
        }
    }
}
