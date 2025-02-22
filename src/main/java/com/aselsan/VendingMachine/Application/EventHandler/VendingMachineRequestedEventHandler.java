package com.aselsan.VendingMachine.Application.EventHandler;

import com.aselsan.VendingMachine.Application.Dto.VendingMachineDto;
import com.aselsan.VendingMachine.Application.Service.VendingMachineService;
import com.aselsan.VendingMachine.Domain.Event.VendingMachineRequestedEvent;
import com.aselsan.VendingMachine.Domain.Event.VendingMachineResponseEvent;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class VendingMachineRequestedEventHandler {
    private final Logger logger = LoggerFactory.getLogger(VendingMachineRequestedEventHandler.class);
    private final VendingMachineService vendingMachineService;
    private final ApplicationEventPublisher eventPublisher;

    @EventListener
    public void handleVendingMachineRequested(VendingMachineRequestedEvent event) {
        try {
            VendingMachineDto machine = vendingMachineService.getMachine(event.getMachineId());
            eventPublisher.publishEvent(new VendingMachineResponseEvent(this, machine, event.getMachineId()));
        } catch (Exception e) {
            logger.error("Error fetching machine {}: {}",
                    event.getMachineId(),
                    e.getMessage());
            throw e;
        }
    }
}
