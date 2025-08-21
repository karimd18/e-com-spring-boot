# e-com-spring-boot
Building a ecommerce backend service using spring boot and microservices architecture 

project structure

```common-libs/
└── src/main/java/com/ecom/common/
    ├── api/
    │   ├── dto/
    │   │   ├── ProductDto.java
    │   │   ├── OrderItemDto.java
    │   │   ├── OrderSummaryDto.java
    │   │   ├── OrderStatus.java
    │   │   ├── Address.java
    │   │   └── Money.java
    │   ├── error/
    │   │   ├── ApiError.java
    │   │   ├── ErrorCode.java
    │   │   └── DomainException.java
    │   ├── pagination/
    │   │   └── PageResponse.java
    │   └── validation/
    │       ├── SKU.java
    │       ├── SKUValidator.java
    │       └── Phone.java
    │
    ├── events/
    │   ├── EventEnvelope.java
    │   ├── order/
    │   │   ├── OrderCreated.java
    │   │   ├── OrderPaid.java
    │   │   └── OrderCancelled.java
    │   ├── inventory/
    │   │   ├── InventoryReserved.java
    │   │   └── InventoryRejected.java
    │   ├── payment/
    │   │   ├── PaymentSucceeded.java
    │   │   └── PaymentFailed.java
    │   ├── Topics.java
    │   └── serde/
    │       └── JsonSerdeFactory.java
    │
    ├── security/
    │   ├── Roles.java
    │   ├── Scopes.java
    │   └── AuthenticatedUser.java
    │
    ├── web/
    │   ├── Headers.java
    │   ├── CorrelationIdFilter.java
    │   └── RequestContext.java
    │
    ├── json/
    │   └── ObjectMapperConfig.java
    │
    └── util/
        └── Preconditions.java
`````
