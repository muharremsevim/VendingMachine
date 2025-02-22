package com.aselsan.VendingMachine.Application.EventHandler;

import com.aselsan.VendingMachine.Application.Dto.ProductDto;
import com.aselsan.VendingMachine.Domain.Event.ItemsResponseEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class ItemsResponseEventListener {

    private final Map<Long, CompletableFuture<List<ProductDto>>> responseFutures = new ConcurrentHashMap<>();

    public void registerFuture(Long machineId, CompletableFuture<List<ProductDto>> future) {
        responseFutures.put(machineId, future);
    }

    @EventListener
    public void handleItemsResponse(ItemsResponseEvent event) {
        CompletableFuture<List<ProductDto>> future = responseFutures.remove(event.getMachineId());
        if (future != null) {
            future.complete(event.getItems());
        }
    }
}
