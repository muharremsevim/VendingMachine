package com.aselsan.VendingMachine.Application.EventHandler;

import com.aselsan.VendingMachine.Application.Service.VendingMachineService;
import com.aselsan.VendingMachine.Domain.Event.MoneyInsertedEvent;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MoneyInsertedEventHandler {
    private final Logger logger = LoggerFactory.getLogger(MoneyInsertedEventHandler.class);
    private final VendingMachineService vendingMachineService;

    // Balance updated
    @EventListener
    public void handleMoneyInserted(MoneyInsertedEvent event) {
        try {
            vendingMachineService.updateBalance(event.getMachineId(), event.getMoney().getValue());
        } catch (Exception e) {
            logger.error("Error while inserting money for machine {}: {}",
                    event.getMachineId(),
                    e.getMessage()
            );
        }
    }
}
