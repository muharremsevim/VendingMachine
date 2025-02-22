package com.aselsan.VendingMachine.Application.EventHandler;

import com.aselsan.VendingMachine.Application.Dto.VendingMachineDto;
import com.aselsan.VendingMachine.Application.Service.VendingMachineService;
import com.aselsan.VendingMachine.Domain.Event.AllVendingMachinesRequestedEvent;
import com.aselsan.VendingMachine.Domain.Event.AllVendingMachinesResponseEvent;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class AllVendingMachinesRequestedEventHandler {
    private final Logger logger = LoggerFactory.getLogger(AllVendingMachinesRequestedEventHandler.class);
    private final VendingMachineService vendingMachineService;
    private final ApplicationEventPublisher eventPublisher;

    @EventListener
    public void handleAllVendingMachinesRequested(AllVendingMachinesRequestedEvent event) {
        try {
            List<VendingMachineDto> machines = vendingMachineService.getAllMachines();
            eventPublisher.publishEvent(new AllVendingMachinesResponseEvent(this, machines));
        } catch (Exception e) {
            logger.error("Error fetching all machines: {}", e.getMessage());
            throw e;
        }
    }
}
