package com.aselsan.VendingMachine.Controller;

import com.aselsan.VendingMachine.Application.Dto.ProductDto;
import com.aselsan.VendingMachine.Application.Dto.VendingMachineDto;
import com.aselsan.VendingMachine.Application.EventHandler.*;
import com.aselsan.VendingMachine.Domain.Event.*;
import com.aselsan.VendingMachine.Domain.Model.Money;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executors;

@RestController
@RequestMapping("/api/v1/vending-machine")
@RequiredArgsConstructor
@Tag(name = "Vending Machine", description = "Vending Machine management APIs")
public class VendingMachineController {
    private final ApplicationEventPublisher eventPublisher;
    private final VendingMachineEventsHandler vendingMachineEventsHandler;
    private final ItemsRequestedEventHandler itemsRequestedEventHandler;
    private final MoneyInsertedEventHandler moneyInsertedEventHandler;
    private final RefundRequestedEventHandler refundRequestedEventHandler;
    private final ProductPurchasedEventHandler productPurchasedEventHandler;

    // First retrieve all vending machines without their products
    @Async
    @GetMapping("/all")
    @Operation(
            summary = "Get all vending machines",
            description = "Vending machines return without their products")
    public CompletableFuture<ResponseEntity<List<VendingMachineDto>>> getAllMachines() {
        CompletableFuture<List<VendingMachineDto>> future = new CompletableFuture<>();
        vendingMachineEventsHandler.registerFutureForAll(future);

        eventPublisher.publishEvent(new AllVendingMachinesRequestedEvent(this));

        return future.thenApply(ResponseEntity::ok);
    }

    // Unlike queryItems, this methods returns information about vending machine together with its products
    @Async
    @GetMapping("/{machineId}")
    @Operation(summary = "Get a vending machine by Id")
    public CompletableFuture<ResponseEntity<VendingMachineDto>> getMachine(
            @Parameter(description = "Vending machine Id") @PathVariable Long machineId) {
        CompletableFuture<VendingMachineDto> future = new CompletableFuture<>();
        vendingMachineEventsHandler.registerFuture(machineId, future);

        eventPublisher.publishEvent(new VendingMachineRequestedEvent(this, machineId));

        return future.thenApply(ResponseEntity::ok);
    }

    // Retrieve all products in a given vending machine, see names and prices of the products
    @Async
    @Operation(
            summary = "Query available items",
            description = "Returns a list of all available items in the vending machine"
    )
    @GetMapping("/{machineId}/items")
    public CompletableFuture<ResponseEntity<List<ProductDto>>> queryItems(
            @Parameter(description = "Vending machine Id", required = true)
            @PathVariable Long machineId) {
        CompletableFuture<List<ProductDto>> future = new CompletableFuture<>();
        itemsRequestedEventHandler.registerFuture(machineId, future);

        eventPublisher.publishEvent(new ItemsRequestedEvent(this, machineId));

        return future.thenApply(ResponseEntity::ok);
    }

    // Insert necessary amount to the vending machine
    @Async
    @PostMapping("/{machineId}/insert-money")
    @Operation(summary = "Insert money into the vending machine")
    public CompletableFuture<ResponseEntity<Double>> insertMoney(
            @Parameter(description = "Vending machine ID") @PathVariable Long machineId,
            @Parameter(description = "Money denomination") @RequestParam Money money) {

        CompletableFuture<Double> future = new CompletableFuture<>();
        moneyInsertedEventHandler.registerFuture(machineId, future);

        eventPublisher.publishEvent(new MoneyInsertedEvent(this, machineId, money));

        return future.thenApply(ResponseEntity::ok);
    }

    // After inserting money into the vending machine, get the product, if amount is sufficient
    @Async
    @Operation(
            summary = "Dispense a specific item",
            description = "Returns the specific item, if the balance is sufficient"
    )
    @PostMapping("{machineId}/purchase/{productId}")
    public CompletableFuture<ResponseEntity<ProductDto>> purchaseItem(
            @Parameter(description = "Vending machine Id", required = true)
            @PathVariable Long machineId,
            @Parameter(description = "Item Id", required = true)
            @PathVariable Long productId) {
        CompletableFuture<ProductDto> future = new CompletableFuture<>();
        productPurchasedEventHandler.registerFuture(machineId, future);

        eventPublisher.publishEvent(new ProductPurchasedEvent(this, machineId, productId));

        return future.thenApply(ResponseEntity::ok);
    }

    // Refund clears the balance in the vending machine
    @Async
    @PostMapping("/{machineId}/refund")
    @Operation(summary = "Get the refund from given machine by Id")
    public CompletableFuture<ResponseEntity<Double>> refund(
            @Parameter(description = "Vending machine Id") @PathVariable Long machineId) {
        CompletableFuture<Double> future = new CompletableFuture<>();
        refundRequestedEventHandler.registerFuture(machineId, future);

        eventPublisher.publishEvent(new RefundRequestedEvent(this, machineId));

        return future.thenApply(ResponseEntity::ok);
    }

    // ADMIN OPERATIONS

    // Only Admin user can add inventory to the given vending machine
    @Operation(
            summary = "Install inventory",
            description = "Install or update inventory in the vending machine",
            security = @SecurityRequirement(name = "basicAuth")
    )
    @PostMapping("{machineId}/inventory/install")
    @PreAuthorize("hasRole('ADMIN')")
    public CompletableFuture<ResponseEntity<Void>> installInventory(
            @Parameter(description = "Vending machine Id", required = true)
            @PathVariable Long machineId,
            @Parameter(description = "Inventory details")
            @RequestBody List<ProductDto> productList) {

        return CompletableFuture.supplyAsync(() -> {
            eventPublisher.publishEvent(new InventoryInstalledEvent(this, machineId, productList));
            return ResponseEntity.accepted().build();
        }, Executors.newSingleThreadExecutor());
    }

    // Only Admin user can de/activate vending machine
    @Operation(
            summary = "Perform maintenance",
            description = "Perform maintenance operations on the vending machine",
            security = @SecurityRequirement(name = "basicAuth")
    )
    @PostMapping("{machineId}/maintenance")
    @PreAuthorize("hasRole('ADMIN')")
    public CompletableFuture<ResponseEntity<Void>> doMaintenance(
            @Parameter(description = "Vending machine Id", required = true)
            @PathVariable Long machineId,
            @Parameter(description = "De/Activate Machine")
            @RequestParam boolean isRunning) {

        return CompletableFuture.supplyAsync(() -> {
            eventPublisher.publishEvent(new MaintenanceEvent(this, machineId, isRunning));
            return ResponseEntity.accepted().build();
        }, Executors.newSingleThreadExecutor());
    }
} 