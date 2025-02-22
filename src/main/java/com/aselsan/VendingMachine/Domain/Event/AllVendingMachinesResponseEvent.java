package com.aselsan.VendingMachine.Domain.Event;

import com.aselsan.VendingMachine.Application.Dto.VendingMachineDto;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

import java.util.List;

@Getter
public class AllVendingMachinesResponseEvent extends ApplicationEvent {
    private final List<VendingMachineDto> machines;

    public AllVendingMachinesResponseEvent(Object source, List<VendingMachineDto> machines) {
        super(source);
        this.machines = machines;
    }
}
