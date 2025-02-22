package com.aselsan.VendingMachine.Application.Service;

import com.aselsan.VendingMachine.Application.Service.Impl.VendingMachineServiceImpl;
import com.aselsan.VendingMachine.Domain.Event.MoneyInsertedEvent;
import com.aselsan.VendingMachine.Domain.Model.Money;
import com.aselsan.VendingMachine.Domain.Model.VendingMachine;
import com.aselsan.VendingMachine.Domain.Model.VendingMachineStatus;
import com.aselsan.VendingMachine.Domain.Port.VendingMachineStore;
import com.aselsan.VendingMachine.Exception.VendingMachineNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.ApplicationEventPublisher;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class VendingMachineServiceImplTest {

    @Mock
    private VendingMachineStore vendingMachineStore;

    @Mock
    private ApplicationEventPublisher eventPublisher;

    private VendingMachineServiceImpl vendingMachineService;

    @BeforeEach
    void setUp() {
        vendingMachineService = new VendingMachineServiceImpl(
                vendingMachineStore,
                null,
                null
        );
    }

    @Test
    void insertMoney_ShouldPublishEventAndReturnUpdatedBalance() {
        // Arrange
        Long machineId = 1L;
        Money money = Money.TEN;
        Double initialBalance = 20.0;

        VendingMachine mockMachine = VendingMachine.builder()
                .id(machineId)
                .currentBalance(initialBalance)
                .status(VendingMachineStatus.RUNNING)
                .build();

        when(vendingMachineStore.findById(machineId)).thenReturn(Optional.of(mockMachine));

        // Act
        Double result = vendingMachineService.insertMoney(machineId, money);

        // Assert
        assertEquals(initialBalance + money.getValue(), result);

        // Verify event was published
        ArgumentCaptor<MoneyInsertedEvent> eventCaptor = ArgumentCaptor.forClass(MoneyInsertedEvent.class);
        verify(eventPublisher).publishEvent(eventCaptor.capture());

        MoneyInsertedEvent capturedEvent = eventCaptor.getValue();
        assertEquals(machineId, capturedEvent.getMachineId());
        assertEquals(money, capturedEvent.getMoney());
    }

    @Test
    void insertMoney_ShouldThrowException_WhenMachineNotFound() {
        // Arrange
        Long machineId = 1L;
        when(vendingMachineStore.findById(machineId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(VendingMachineNotFoundException.class,
                () -> vendingMachineService.insertMoney(machineId, Money.TEN));

        // Verify no event was published
        verify(eventPublisher, never()).publishEvent(any());
    }

    @Test
    void updateBalance_ShouldUpdateMachineBalance() {
        // Arrange
        Long machineId = 1L;
        VendingMachine mockMachine = VendingMachine.builder()
                .id(machineId)
                .currentBalance(20.0)
                .status(VendingMachineStatus.RUNNING)
                .build();

        when(vendingMachineStore.findById(machineId)).thenReturn(Optional.of(mockMachine));
        when(vendingMachineStore.store(any(VendingMachine.class))).thenReturn(mockMachine);

        // Act
        vendingMachineService.insertMoney(machineId, Money.TEN);

        // Assert
        verify(vendingMachineStore).store(any(VendingMachine.class));
    }
} 