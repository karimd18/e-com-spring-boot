package com.ecom.common.api.dto;

public enum OrderStatus {
    CREATED,
    PAYMENT_PENDING,
    PAID,
    FULFILLING,
    SHIPPED,
    COMPLETED,
    CANCELED
}