package com.aselsan.VendingMachine.Domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "vending_machine_slot")
public class VendingMachineSlot extends BaseAuditableEntity {

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "vending_machine_id", nullable = false)
    private VendingMachine vendingMachine;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product product;

    @NotNull
    @Column(name = "slot_number", nullable = false)
    private String slotNumber;

    @NotNull
    @Min(value = 0)
    @Column(name = "capacity", nullable = false)
    private Integer capacity;

    @NotNull
    @Min(value = 0)
    @Column(name = "quantity", nullable = false)
    private Integer quantity = 0;

    @Column(name = "is_active")
    private boolean isActive = true;
} 