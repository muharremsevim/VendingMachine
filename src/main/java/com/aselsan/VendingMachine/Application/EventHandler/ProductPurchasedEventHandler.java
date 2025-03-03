package com.aselsan.VendingMachine.Application.EventHandler;

import com.aselsan.VendingMachine.Application.Dto.ProductDto;
import com.aselsan.VendingMachine.Application.Service.VendingMachineService;
import com.aselsan.VendingMachine.Domain.Event.ProductPurchasedEvent;
import com.aselsan.VendingMachine.Domain.Event.ProductPurchasedResponseEvent;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;

@Component
@RequiredArgsConstructor
public class ProductPurchasedEventHandler {
    private final Logger logger = LoggerFactory.getLogger(ProductPurchasedEventHandler.class);
    private final VendingMachineService vendingMachineService;
    private final ApplicationEventPublisher eventPublisher;

    private final Map<Long, CompletableFuture<ProductDto>> responseFutures = new ConcurrentHashMap<>();

    public void registerFuture(Long machineId, CompletableFuture<ProductDto> future) {
        responseFutures.put(machineId, future);
    }

    @EventListener
    @Transactional
    public void handleProductPurchased(ProductPurchasedEvent event) {
        try {
            ProductDto product = vendingMachineService.dispenseProduct(event.getMachineId(), event.getProductId());
            eventPublisher.publishEvent(new ProductPurchasedResponseEvent(this, event.getMachineId(), product));
        } catch (Exception e) {
            logger.error("Error processing dispense product {} for machine {}: {}",
                    event.getProductId(),
                    event.getMachineId(),
                    e.getMessage()
            );
            throw e;
        }
    }

    @EventListener
    public void handleProductPurchasedResponse(ProductPurchasedResponseEvent event) {
        if (!responseFutures.isEmpty()) {
            CompletableFuture<ProductDto> future = responseFutures.remove(event.getMachineId());
            future.complete(event.getProduct());
        }
    }
}
