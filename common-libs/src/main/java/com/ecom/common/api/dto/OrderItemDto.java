package com.ecom.common.api.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.UUID;

public record OrderItemDto(
        @NotNull(message = "Order item ID cannot be null")
        UUID id,

        @NotNull(message = "Product ID cannot be null")
        UUID productId,

        @NotNull(message = "Order ID cannot be null")
        UUID orderId,

        @NotBlank(message = "Product name cannot be blank")
        @Size(min = 3, max = 100, message = "Product name must be between 3 and 100 characters")
        String productName,

        @NotNull(message = "Unit price cannot be null")
        String unitPrice,

        @NotNull(message = "Quantity must be provided")
        @Min(value = 1, message = "Quantity must be at least 1")
        Integer quantity
) {
    public Money totalPrice() {
        return unitPrice.multiply(quantity);
    }
}