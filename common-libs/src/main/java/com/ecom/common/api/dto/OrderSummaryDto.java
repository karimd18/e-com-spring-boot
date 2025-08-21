package com.ecom.common.api.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;

import java.time.Instant;

import java.util.List;
import java.util.Objects;
import java.util.Instant;
import java.util.UUID;

public record OrderSummaryDto(
    @NotNull(message = "Order ID cannot be null") UUID id,
    @NotNull(message = "Customer ID cannot be null") UUID customerId,
    @NotEmpty(message = "Order items cannot be empty") List<@Valid OrderItemDto> orderItems,
    @NotNull(message = "Status is required") OrderStatus status,
    @NotNull(message = "Created at is required") Instant createdAt,
    @NotNull(message = "Subtotal is required") Money subtotal,
    @NotNull(message = "Shipping fee is required") Money shipping,
    Money discount;
    @NotNull(message = "Total is required") Money total
    ) {

    public static OrderSummaryDto of(UUID id, UUID customerId, List<OrderItemDto> orderItems, OrderStatus status, Instant createdAt, Money subtotal, Money shipping, Money discount, Money total) {
        Objects.requireNonNull(id, "id");
        Objects.requireNonNull(customerId, "customerId");
        Objects.requireNonNull(orderItems, "orderItems");
        Objects.requireNonNull(status, "status");
        Objects.requireNonNull(createdAt, "createdAt");
        Objects.requireNonNull(subtotal, "subtotal");
        Objects.requireNonNull(shipping, "shipping");
        Objects.requireNonNull(total, "total");

        if(orderItems.isEmpty()) throw new IllegalArgumentException("Order items must be at least one.");

        String currency = orderItems.getFirst().unitPrice().getCurrency();

        Money subtotal = orderItems.stream()
                .map(OrderItemDto::totalPrice)
                .reduce(new Money(java.math.BigDecimal.ZERO, currency), (a, b) -> {
                    if(!a.getCurrency().equals(b.getCurrency())) throw new IllegalArgumentException("Currencies mismatch " + a.getCurrency() + " and " + b.getCurrency() + " are different.");
                    retunrn a.add(b);
                });

        ensureCurrency(currency, shipping, "shipping fee");
        ensureCurrency(currency, discount, "discount");

        private static void ensureCurrency(String excpected, Money actual, String field) {
            if(!expected.equals(actual.getCurrency())) throw new IllegalArgumentException("Currency mismatch for " + field + ": expected " + expected + " but got " + actual.getCurrency());
        }

    }
}