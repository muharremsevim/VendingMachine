package com.aselsan.VendingMachine.Config.Initializer;

import com.aselsan.VendingMachine.Domain.Model.Product;
import com.aselsan.VendingMachine.Domain.Model.ProductStatus;
import com.aselsan.VendingMachine.Domain.Model.VendingMachine;
import com.aselsan.VendingMachine.Domain.Model.VendingMachineStatus;
import com.aselsan.VendingMachine.Domain.Port.VendingMachineStore;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;
import java.util.List;

@Configuration
@RequiredArgsConstructor
public class DataInitializer {

    private final VendingMachineStore vendingMachineStore;

    @Bean
    public CommandLineRunner initializeData() {
        return args -> {
            // Check if data already exists
            if (vendingMachineStore.findBySerialNumber("S123456").isPresent()) {
                return; // Data already initialized
            }

            // Create products
            List<Product> products = Arrays.asList(
                    Product.builder()
                            .name("Water")
                            .price(25.0)
                            .quantity(10)
                            .status(ProductStatus.AVAILABLE)
                            .build(),
                    Product.builder()
                            .name("Coke")
                            .price(35.0)
                            .quantity(13)
                            .status(ProductStatus.AVAILABLE)
                            .build(),
                    Product.builder()
                            .name("Soda")
                            .price(45.0)
                            .quantity(11)
                            .status(ProductStatus.AVAILABLE)
                            .build(),
                    Product.builder()
                            .name("Snickers")
                            .price(50.0)
                            .quantity(7)
                            .status(ProductStatus.AVAILABLE)
                            .build(),
                    Product.builder()
                            .name("Chips")
                            .price(40.0)
                            .quantity(5)
                            .status(ProductStatus.AVAILABLE)
                            .build(),
                    Product.builder()
                            .name("Candy Bar")
                            .price(30.0)
                            .quantity(20)
                            .status(ProductStatus.AVAILABLE)
                            .build(),
                    Product.builder()
                            .name("Energy Drink")
                            .price(60.0)
                            .quantity(0)
                            .status(ProductStatus.OUT_OF_STOCK)
                            .build(),
                    Product.builder()
                            .name("Juice Box")
                            .price(55.0)
                            .quantity(8)
                            .status(ProductStatus.AVAILABLE)
                            .build(),
                    Product.builder()
                            .name("Protein Bar")
                            .price(45.0)
                            .quantity(9)
                            .status(ProductStatus.AVAILABLE)
                            .build(),
                    Product.builder()
                            .name("Gum")
                            .price(20.0)
                            .quantity(12)
                            .status(ProductStatus.AVAILABLE)
                            .build()
            );

            // Create vending machine
            VendingMachine vendingMachine = VendingMachine.builder()
                    .serialNumber("S123456")
                    .products(products)
                    .currentBalance(0.0)
                    .status(VendingMachineStatus.RUNNING)
                    .build();

            // Save to database
            vendingMachineStore.store(vendingMachine);
        };
    }
} 