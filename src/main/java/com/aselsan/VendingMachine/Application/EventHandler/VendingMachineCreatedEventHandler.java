package com.aselsan.VendingMachine.Application.EventHandler;

import com.aselsan.VendingMachine.Application.Service.VendingMachineService;
import com.aselsan.VendingMachine.Domain.Event.VendingMachineCreatedEvent;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class VendingMachineCreatedEventHandler {
    private final Logger logger = LoggerFactory.getLogger(VendingMachineCreatedEventHandler.class);
    private final VendingMachineService vendingMachineService;

    @EventListener
    public void handleMachineCreated(VendingMachineCreatedEvent event) {
        try {
            vendingMachineService.createMachine(event.getSerialNumber());
        } catch (Exception e) {
            logger.error("Error processing create machine for serial number {}: {}",
                    event.getSerialNumber(),
                    e.getMessage()
            );
            throw e;
        }
    }
}
