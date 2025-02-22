package com.aselsan.VendingMachine.Application.EventHandler;

import com.aselsan.VendingMachine.Application.Service.VendingMachineService;
import com.aselsan.VendingMachine.Domain.Event.MoneyInsertedEvent;
import com.aselsan.VendingMachine.Domain.Model.Money;
import com.aselsan.VendingMachine.Exception.VendingMachineNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MoneyInsertedEventHandlerTest {

    @Mock
    private VendingMachineService vendingMachineService;

    @InjectMocks
    private MoneyInsertedEventHandler eventHandler;

    @Test
    void handleMoneyInserted_ShouldCallService() {
        // Arrange
        Long machineId = 1L;
        Money money = Money.TEN;
        MoneyInsertedEvent event = new MoneyInsertedEvent(this, machineId, money);

        when(vendingMachineService.insertMoney(machineId, money)).thenReturn(10.0);

        // Act
        eventHandler.handleMoneyInserted(event);

        // Assert
        verify(vendingMachineService).insertMoney(machineId, money);
    }

    @Test
    void handleMoneyInserted_ShouldPropagateException() {
        // Arrange
        Long machineId = 1L;
        Money money = Money.TEN;
        MoneyInsertedEvent event = new MoneyInsertedEvent(this, machineId, money);

        when(vendingMachineService.insertMoney(machineId, money))
            .thenThrow(new VendingMachineNotFoundException(machineId));

        // Act & Assert
        assertThrows(VendingMachineNotFoundException.class, 
            () -> eventHandler.handleMoneyInserted(event));
    }
} 