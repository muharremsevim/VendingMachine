package com.aselsan.VendingMachine.Domain.Model;

import com.aselsan.VendingMachine.Domain.Annotation.ValueObject;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Getter
@ValueObject(description = "Represents monetary denominations with immutable values")
@NoArgsConstructor(force = true)
@RequiredArgsConstructor
public enum Money {
    ONE(1.0),
    FIVE(5.0),
    TEN(10.0),
    TWENTY(20.0),
    FIFTY(50.0),
    HUNDRED(100.0);

    private final Double value;

    public static Money valueOf(Double amount) {
        for (Money money : Money.values()) {
            if (money.getValue().equals(amount)) {
                return money;
            }
        }
        throw new IllegalArgumentException("Invalid money amount: " + amount);
    }
}