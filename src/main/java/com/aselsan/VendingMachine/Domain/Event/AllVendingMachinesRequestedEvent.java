package com.aselsan.VendingMachine.Domain.Event;

import org.springframework.context.ApplicationEvent;

public class AllVendingMachinesRequestedEvent extends ApplicationEvent {

    public AllVendingMachinesRequestedEvent(Object source) {
        super(source);
    }
}
