package com.aselsan.VendingMachine.Application.EventHandler;

import com.aselsan.VendingMachine.Application.Service.VendingMachineService;
import com.aselsan.VendingMachine.Domain.Event.InventoryInstalledEvent;
import com.aselsan.VendingMachine.Domain.Event.MaintenanceEvent;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MaintenanceEventHandler {
    private final Logger logger = LoggerFactory.getLogger(MaintenanceEventHandler.class);
    private final VendingMachineService vendingMachineService;

    @EventListener
    public void handleMaintenance(MaintenanceEvent event) {
        try {
            vendingMachineService.updateMachineStatus(event.getMachineId(), event.isActivate());
        } catch (Exception e) {
            logger.error("Error processing maintenance for machine {}: {}",
                    event.getMachineId(),
                    e.getMessage()
            );
            throw e;
        }
    }
}
