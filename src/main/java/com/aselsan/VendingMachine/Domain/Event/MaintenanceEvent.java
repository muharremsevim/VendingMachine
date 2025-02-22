package com.aselsan.VendingMachine.Domain.Event;

import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
public class MaintenanceEvent extends ApplicationEvent {
    private final Long machineId;
    private final boolean activate;

    public MaintenanceEvent(Object source, Long machineId, boolean activate) {
        super(source);
        this.machineId = machineId;
        this.activate = activate;
    }
}
