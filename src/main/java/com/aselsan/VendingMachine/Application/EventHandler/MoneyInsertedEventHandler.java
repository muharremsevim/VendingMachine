package com.aselsan.VendingMachine.Application.EventHandler;

import com.aselsan.VendingMachine.Application.Service.VendingMachineService;
import com.aselsan.VendingMachine.Domain.Event.MoneyInsertedEvent;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class MoneyInsertedEventHandler {
    private final Logger logger = LoggerFactory.getLogger(MoneyInsertedEventHandler.class);
    private final VendingMachineService vendingMachineService;

    @EventListener
    @Transactional
    public void handleMoneyInserted(MoneyInsertedEvent event) {
        try {
            vendingMachineService.insertMoney(event.getMachineId(), event.getMoney());
        } catch (Exception e) {
            logger.error("Error processing money insertion for machine {}: {}",
                    event.getMachineId(),
                    e.getMessage()
            );
            throw e;
        }
    }
}
