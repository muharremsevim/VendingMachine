package com.aselsan.VendingMachine.Application.EventHandler;

import com.aselsan.VendingMachine.Application.Service.VendingMachineService;
import com.aselsan.VendingMachine.Domain.Event.MoneyInsertedEvent;
import com.aselsan.VendingMachine.Domain.Event.MoneyInsertedResponseEvent;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;

@Component
@RequiredArgsConstructor
public class MoneyInsertedEventHandler {
    private final Logger logger = LoggerFactory.getLogger(MoneyInsertedEventHandler.class);
    private final VendingMachineService vendingMachineService;
    private final ApplicationEventPublisher eventPublisher;

    private final Map<Long, CompletableFuture<Double>> responseFutures = new ConcurrentHashMap<>();

    public void registerFuture(Long machineId, CompletableFuture<Double> future) {
        responseFutures.put(machineId, future);
    }

    @EventListener
    public void handleMoneyInserted(MoneyInsertedEvent event) {
        try {
            Double balance = vendingMachineService.insertMoney(event.getMachineId(), event.getMoney());
            eventPublisher.publishEvent(new MoneyInsertedResponseEvent(this, event.getMachineId(), balance));
        } catch (Exception e) {
            logger.error("Error processing money insertion for machine {}: {}",
                    event.getMachineId(),
                    e.getMessage()
            );
            throw e;
        }
    }

    @EventListener
    public void handleMoneyInsertedResponse(MoneyInsertedResponseEvent event) {
        if (!responseFutures.isEmpty()) {
            CompletableFuture<Double> future = responseFutures.remove(event.getMachineId());
            future.complete(event.getBalance());
        }
    }
}
