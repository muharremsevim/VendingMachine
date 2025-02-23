package com.aselsan.VendingMachine.Application.EventHandler;

import com.aselsan.VendingMachine.Application.Dto.VendingMachineDto;
import com.aselsan.VendingMachine.Application.Service.VendingMachineService;
import com.aselsan.VendingMachine.Domain.Event.AllVendingMachinesRequestedEvent;
import com.aselsan.VendingMachine.Domain.Event.AllVendingMachinesResponseEvent;
import com.aselsan.VendingMachine.Domain.Event.VendingMachineRequestedEvent;
import com.aselsan.VendingMachine.Domain.Event.VendingMachineResponseEvent;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;

@Component
@RequiredArgsConstructor
public class VendingMachineEventsHandler {

    private final ApplicationEventPublisher eventPublisher;
    private final VendingMachineService vendingMachineService;
    private final Logger logger = LoggerFactory.getLogger(VendingMachineEventsHandler.class);

    private final Map<Long, CompletableFuture<VendingMachineDto>> responseFutures = new ConcurrentHashMap<>();
    private final List<CompletableFuture<List<VendingMachineDto>>> responseFuturesAll = new ArrayList<>();

    public void registerFuture(Long machineId, CompletableFuture<VendingMachineDto> future) {
        responseFutures.put(machineId, future);
    }

    public void registerFutureForAll(CompletableFuture<List<VendingMachineDto>> future) {
        responseFuturesAll.add(future);
    }

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

    @EventListener
    public void handleVendingMachineResponse(VendingMachineResponseEvent event) {
        CompletableFuture<VendingMachineDto> future = responseFutures.remove(event.getMachineId());
        if (future != null) {
            future.complete(event.getMachine());
        }
    }

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

    @EventListener
    public void handleAllVendingMachinesResponse(AllVendingMachinesResponseEvent event) {
        if (!responseFuturesAll.isEmpty()) {
            CompletableFuture<List<VendingMachineDto>> future = responseFuturesAll.remove(0);
            future.complete(event.getMachines());
        }
    }
}
