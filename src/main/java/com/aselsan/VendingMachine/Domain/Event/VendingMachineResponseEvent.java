package com.aselsan.VendingMachine.Domain.Event;

import com.aselsan.VendingMachine.Application.Dto.VendingMachineDto;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
public class VendingMachineResponseEvent extends ApplicationEvent {
    private final VendingMachineDto machine;
    private final Long machineId;

    public VendingMachineResponseEvent(Object source, VendingMachineDto machine, Long machineId) {
        super(source);
        this.machine = machine;
        this.machineId = machineId;
    }
}
