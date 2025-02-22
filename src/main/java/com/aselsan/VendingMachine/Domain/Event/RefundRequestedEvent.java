package com.aselsan.VendingMachine.Domain.Event;

import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
public class RefundRequestedEvent extends ApplicationEvent {
    private final Long machineId;

    public RefundRequestedEvent(Object source, Long machineId) {
        super(source);
        this.machineId = machineId;
    }
}
