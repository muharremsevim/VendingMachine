package com.aselsan.VendingMachine.Controller;

import com.aselsan.VendingMachine.Domain.Product;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/vending-machine")
@RequiredArgsConstructor
@Tag(name = "Vending Machine", description = "Vending Machine management APIs")
public class VendingMachineController {

    @Operation(
            summary = "Query available items",
            description = "Returns a list of all available items in the vending machine"
    )
    @GetMapping("/items")
    public ResponseEntity<List<Product>> queryItems() {
        return ResponseEntity.ok(List.of());
    }

    @Operation(
            summary = "Get item details",
            description = "Returns details of a specific item"
    )
    @GetMapping("/item/{id}")
    public ResponseEntity<Product> getItem(
            @Parameter(description = "Item ID") @PathVariable String id) {
        return ResponseEntity.ok(null);
    }

    @Operation(
            summary = "Purchase item",
            description = "Purchase and retrieve an item from the vending machine"
    )
    @PostMapping("/purchase/{id}")
    public ResponseEntity<Void> retrieveItem(
            @Parameter(description = "Item ID") @PathVariable String id,
            @Parameter(description = "Payment details") @RequestBody Object paymentDetails) {
        return ResponseEntity.ok().build();
    }

    @Operation(
            summary = "Install inventory",
            description = "Install or update inventory in the vending machine",
            security = @SecurityRequirement(name = "basicAuth")
    )
    @PostMapping("/inventory/install")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> installInventory(
            @Parameter(description = "Inventory details") @RequestBody Object inventoryDetails) {
        return ResponseEntity.ok().build();
    }

    @Operation(
            summary = "Perform maintenance",
            description = "Perform maintenance operations on the vending machine",
            security = @SecurityRequirement(name = "basicAuth")
    )
    @PostMapping("/maintenance")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> doMaintenance(
            @Parameter(description = "Maintenance details") @RequestBody Object maintenanceDetails) {
        return ResponseEntity.ok().build();
    }
} 