package com.aselsan.VendingMachine.Infrastructure.Persistence.Entity;

import com.aselsan.VendingMachine.Domain.Model.VendingMachine;
import com.aselsan.VendingMachine.Domain.Model.VendingMachineStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Table(name = "vending_machine")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VendingMachineEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String serialNumber;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private VendingMachineStatus status;

    @Column(nullable = false)
    private Double currentBalance;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "vending_machine_id")
    private List<ProductEntity> products = new ArrayList<>();

    public static VendingMachineEntity fromDomain(VendingMachine machine) {
        return VendingMachineEntity.builder()
                .id(machine.getId())
                .serialNumber(machine.getSerialNumber())
                .status(machine.getStatus())
                .currentBalance(machine.getCurrentBalance())
                .products(machine.getProducts().stream()
                        .map(ProductEntity::fromDomain)
                        .collect(Collectors.toList()))
                .build();
    }

    public VendingMachine toDomain() {
        return VendingMachine.builder()
                .id(id)
                .serialNumber(serialNumber)
                .status(status)
                .currentBalance(currentBalance)
                .products(products.stream()
                        .map(ProductEntity::toDomain)
                        .collect(Collectors.toList()))
                .build();
    }
}