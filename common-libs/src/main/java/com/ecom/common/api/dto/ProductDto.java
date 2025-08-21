package com.ecom.common.api.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;
import java.util.UUID;

public record ProductDto(

        @NotNull(message = "Product ID cannot be null")
        UUID id,

        @NotBlank(message = "Product name cannot be blank")
        @Size(min = 3, max = 100, message = "Product name must be between 3 and 100 characters")
        String name,

        @Size(min = 3, max = 1000, message = "Product description must be between 3 and 1000 characters")
        String description,

        @NotNull(message = "Product price cannot be null")
        @Min(value = 0, message = "Product price cannot be negative")
        BigDecimal price

        @NotBlank(message = "Currency must be provided")
        @Size(min = 3, max = 3, message = "Currency must be ISO 4217 (3 letters) ")
        String currency,

        @NotNull(message = "Product quantity cannot be null")
        @Min(value = 0, message = "Product quantity cannot be negative")
        Integer availableQuantity,

        boolean active
) {}