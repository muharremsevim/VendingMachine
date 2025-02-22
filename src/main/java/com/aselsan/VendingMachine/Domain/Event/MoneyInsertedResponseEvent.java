package com.aselsan.VendingMachine.Domain.Event;

import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
public class MoneyInsertedResponseEvent extends ApplicationEvent {
    private final Long machineId;
    private final double balance;

    public MoneyInsertedResponseEvent(Object source, Long machineId, double balance) {
        super(source);
        this.machineId = machineId;
        this.balance = balance;
    }
}
