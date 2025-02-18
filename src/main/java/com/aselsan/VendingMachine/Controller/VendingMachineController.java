package com.aselsan.VendingMachine.Controller;

import com.aselsan.VendingMachine.Application.Dto.ProductDto;
import com.aselsan.VendingMachine.Application.Dto.VendingMachineDto;
import com.aselsan.VendingMachine.Application.Service.VendingMachineService;
import com.aselsan.VendingMachine.Domain.Model.Money;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/api/v1/vending-machine")
//@RequiredArgsConstructor
@Tag(name = "Vending Machine", description = "Vending Machine management APIs")
public class VendingMachineController {
    private final VendingMachineService vendingMachineService;

    public VendingMachineController(VendingMachineService vendingMachineService) {
        this.vendingMachineService = vendingMachineService;
    }

    @Async
    @Operation(
            summary = "Query available items",
            description = "Returns a list of all available items in the vending machine"
    )
    @GetMapping("/{machineId}/items")
    public CompletableFuture<ResponseEntity<List<ProductDto>>> queryItems(
            @Parameter(description = "Vending machine Id", required = true)
            @PathVariable Long machineId) {
        return CompletableFuture.supplyAsync(() -> ResponseEntity.ok(vendingMachineService.retrieveItems(machineId)));
    }

    @Async
    @Operation(
            summary = "Get item details",
            description = "Returns details of a specific item"
    )
    @GetMapping("{machineId}/item/{productId}")
    public CompletableFuture<ResponseEntity<ProductDto>> getItem(
            @Parameter(description = "Vending machine Id", required = true)
            @PathVariable Long machineId,
            @Parameter(description = "Item Id", required = true)
            @PathVariable Long productId) {
        return CompletableFuture.supplyAsync(() -> ResponseEntity.ok(vendingMachineService.retrieveItem(machineId, productId)));
    }

    @Async
    @PostMapping("/{machineId}/insert-money")
    @Operation(summary = "Insert money into the vending machine")
    public CompletableFuture<ResponseEntity<Double>> insertMoney(
            @Parameter(description = "Vending machine ID") @PathVariable Long machineId,
            @Parameter(description = "Money denomination") @RequestParam Money money) {
        return CompletableFuture.supplyAsync(() -> ResponseEntity.ok(vendingMachineService.insertMoney(machineId, money)));
    }

    @Async
    @GetMapping("/{machineId}")
    @Operation(summary = "Get a vending machine by ID")
    public CompletableFuture<ResponseEntity<VendingMachineDto>> getMachine(
            @Parameter(description = "Vending machine ID") @PathVariable Long machineId) {
        return CompletableFuture.supplyAsync(() -> ResponseEntity.ok(vendingMachineService.getMachine(machineId)));
    }

    @Async
    @GetMapping
    @Operation(
            summary = "Get all vending machines",
            description = "Vending machines return without their products")
    public CompletableFuture<ResponseEntity<List<VendingMachineDto>>> getAllMachines() {
        return CompletableFuture.supplyAsync(() -> ResponseEntity.ok(vendingMachineService.getAllMachines()));
    }

    @Async
    @Operation(
            summary = "Purchase item",
            description = "Purchase and retrieve an item from the vending machine"
    )
    @PostMapping("{machineId}/purchase/{id}")
    public CompletableFuture<ResponseEntity<Void>> retrieveItem(
            @Parameter(description = "Vending machine ID")
            @PathVariable Long machineId,
            @Parameter(description = "Item ID")
            @PathVariable String id,
            @Parameter(description = "Payment details")
            @RequestBody Object paymentDetails) {
        return CompletableFuture.supplyAsync(() -> ResponseEntity.ok().build());
    }

    @Async
    @Operation(
            summary = "Install inventory",
            description = "Install or update inventory in the vending machine",
            security = @SecurityRequirement(name = "basicAuth")
    )
    @PostMapping("/inventory/install")
    @PreAuthorize("hasRole('ADMIN')")
    public CompletableFuture<ResponseEntity<Void>> installInventory(
            @Parameter(description = "Inventory details") @RequestBody Object inventoryDetails) {
        return CompletableFuture.supplyAsync(() -> ResponseEntity.ok().build());
    }

    @Async
    @Operation(
            summary = "Perform maintenance",
            description = "Perform maintenance operations on the vending machine",
            security = @SecurityRequirement(name = "basicAuth")
    )
    @PostMapping("/maintenance")
    @PreAuthorize("hasRole('ADMIN')")
    public CompletableFuture<ResponseEntity<Void>> doMaintenance(
            @Parameter(description = "Maintenance details") @RequestBody Object maintenanceDetails) {
        return CompletableFuture.supplyAsync(() -> ResponseEntity.ok().build());
    }
} 