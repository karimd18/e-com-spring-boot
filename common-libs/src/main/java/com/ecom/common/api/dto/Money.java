package com.ecom.common.api.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Objects;

public final class Money {

    private final BigDecimal amount;
    private final String currency;

    @JsonCreator
    public Money(@JsonProperty("amount") BigDecimal amount, @JsonProperty("currency") String currency) {
        this.amount = amount.setScale(2, RoundingMode.HALF_UP);
        this.currency = Objects.requireNonNull(currency);
    }

    public BigDecimal getAmount() {
        return this.amount;
    }

    public String getCurrency() {
        return this.currency;
    }

    public Money multiply(int factor) {
        return new Money(this.amount.multiply(BigDecimal.valueOf(factor)), this.currency);
    }

    public Money add(Money other) {
        if(!this.currency.equals(other.currency)) {
            throw new IllegalArgumentException("Currencies mismatch " + this.currency + " and " + other.currency + " are different.");
        }
        return new Money(this.amount.add(other.amount), this.currency);
    }

    @Override
    public String toString() {
        return this.amount + " " + this.currency;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if(!(o instanceof Money m)) return false;
        return amount.equals(m.amount) && currency.equals(m.currency);
    }

    @Override public int hashCode() {
        return Objects.hash(amount, currency);
    }

}
