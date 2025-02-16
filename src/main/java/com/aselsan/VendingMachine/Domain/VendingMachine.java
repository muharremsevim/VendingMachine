package com.aselsan.VendingMachine.Domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Getter
@Setter
@Table(name = "vending_machine")
public class VendingMachine extends BaseAuditableEntity {

    @NotNull
    @Column(name = "total_money", nullable = false)
    private BigDecimal totalMoney = BigDecimal.ZERO;

    @Enumerated(EnumType.ORDINAL)
    @Column(name = "status", nullable = false)
    private VendingMachineStatus status = VendingMachineStatus.RUNNING;

    @Column(name = "is_active")
    private boolean isActive = true;
} 