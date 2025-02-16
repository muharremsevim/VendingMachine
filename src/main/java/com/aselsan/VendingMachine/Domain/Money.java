package com.aselsan.VendingMachine.Domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Getter
@Setter
@Table(name = "money")
public class Money extends BaseAuditableEntity {

    @NotNull
    @Column(name = "amount", nullable = false)
    @Min(value = 0)
    private BigDecimal amount;

    // We can accept Coin or Bill
    @Column(name = "is_coin")
    private boolean isCoin;

    @Column(name = "is_active")
    private boolean isActive = true;
} 