package com.aselsan.VendingMachine.Controller;

import com.aselsan.VendingMachine.Domain.Event.MoneyInsertedEvent;
import com.aselsan.VendingMachine.Domain.Model.Money;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class VendingMachineControllerTest {

    @Mock
    private ApplicationEventPublisher eventPublisher;

    @InjectMocks
    private VendingMachineController controller;

    @Test
    void insertMoney_ShouldPublishEventAndReturnAccepted() throws Exception {
        // Arrange
        Long machineId = 1L;
        Money money = Money.TEN;

        // Act
        ResponseEntity<Double> response = controller.insertMoney(machineId, money).get();

        // Assert
        assertEquals(HttpStatus.ACCEPTED, response.getStatusCode());

        // Verify event was published with correct data
        ArgumentCaptor<MoneyInsertedEvent> eventCaptor = ArgumentCaptor.forClass(MoneyInsertedEvent.class);
        verify(eventPublisher).publishEvent(eventCaptor.capture());

        MoneyInsertedEvent capturedEvent = eventCaptor.getValue();
        assertEquals(machineId, capturedEvent.getMachineId());
        assertEquals(money, capturedEvent.getMoney());
    }
} 