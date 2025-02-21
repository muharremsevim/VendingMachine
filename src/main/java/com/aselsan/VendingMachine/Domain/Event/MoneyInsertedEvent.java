package com.aselsan.VendingMachine.Domain.Event;

import com.aselsan.VendingMachine.Domain.Model.Money;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
public class MoneyInsertedEvent extends ApplicationEvent {
    private final Long machineId;
    private final Money money;

    public MoneyInsertedEvent(Object source, Long machineId, Money money) {
        super(source);
        this.machineId = machineId;
        this.money = money;
    }
}
