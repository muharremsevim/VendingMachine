package com.aselsan.VendingMachine.Application.EventHandler;

import com.aselsan.VendingMachine.Application.Dto.ProductDto;
import com.aselsan.VendingMachine.Application.Service.VendingMachineService;
import com.aselsan.VendingMachine.Domain.Event.ItemsRequestedEvent;
import com.aselsan.VendingMachine.Domain.Event.ItemsResponseEvent;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class ItemsRequestedEventHandler {
    private final Logger logger = LoggerFactory.getLogger(AllVendingMachinesRequestedEventHandler.class);
    private final VendingMachineService vendingMachineService;
    private final ApplicationEventPublisher eventPublisher;

    @EventListener
    public void handleItemsRequested(ItemsRequestedEvent event) {
        try {
            List<ProductDto> items = vendingMachineService.retrieveItems(event.getMachineId());
            eventPublisher.publishEvent(new ItemsResponseEvent(this, event.getMachineId(), items));
        } catch (Exception e) {
            logger.error("Error fetching items from machine {}:{} ",
                    event.getMachineId(),
                    e.getMessage());
            throw e;
        }
    }
}
