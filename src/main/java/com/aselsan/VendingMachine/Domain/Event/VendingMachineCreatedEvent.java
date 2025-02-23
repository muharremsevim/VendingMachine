package com.aselsan.VendingMachine.Domain.Event;

import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
public class VendingMachineCreatedEvent extends ApplicationEvent {
    private final String serialNumber;

    public VendingMachineCreatedEvent(Object source, String serialNumber) {
        super(source);
        this.serialNumber = serialNumber;
    }
}
