package com.aselsan.VendingMachine.Application.EventHandler;

import com.aselsan.VendingMachine.Application.Dto.VendingMachineDto;
import com.aselsan.VendingMachine.Domain.Event.AllVendingMachinesResponseEvent;
import com.aselsan.VendingMachine.Domain.Event.VendingMachineResponseEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class VendingMachineResponseEventListener {

    private final Map<Long, CompletableFuture<VendingMachineDto>> responseFutures = new ConcurrentHashMap<>();
    private final List<CompletableFuture<List<VendingMachineDto>>> responseFuturesAll = new ArrayList<>();

    public void registerFuture(Long machineId, CompletableFuture<VendingMachineDto> future) {
        responseFutures.put(machineId, future);
    }

    public void registerFutureForAll(CompletableFuture<List<VendingMachineDto>> future) {
        responseFuturesAll.add(future);
    }

    @EventListener
    public void handleVendingMachineResponse(VendingMachineResponseEvent event) {
        CompletableFuture<VendingMachineDto> future = responseFutures.remove(event.getMachineId());
        if (future != null) {
            future.complete(event.getMachine());
        }
    }

    @EventListener
    public void handleAllVendingMachinesResponse(AllVendingMachinesResponseEvent event) {
        if (!responseFuturesAll.isEmpty()) {
            CompletableFuture<List<VendingMachineDto>> future = responseFuturesAll.remove(0);
            future.complete(event.getMachines());
        }
    }
}
