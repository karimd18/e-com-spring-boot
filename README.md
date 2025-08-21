# e-com-spring-boot
Building a ecommerce backend service using spring boot and microservices architecture 

project structure

```e-com/
├── pom.xml                               # Parent aggregator (modules + dependency mgmt)
├── README.md
├── .gitignore
├── .editorconfig
├── .env.example
├── scripts/
│   ├── dev-up.sh                         # docker compose up + seed
│   ├── dev-down.sh
│   ├── wait-for-it.sh
│   └── generate-jwt-keys.sh
├── .github/
│   └── workflows/
│       ├── build.yml                     # mvn -T 1C verify, unit + IT + jacoco
│       └── docker-publish.yml            # build & push service images
│
├── infra/
│   ├── docker/
│   │   ├── docker-compose.yml            # postgres, kafka, zookeeper, redis, keycloak, minio, mailhog, grafana, prometheus, tempo/zipkin
│   │   ├── postgres/
│   │   │   └── init/                     # optional SQL seed for local dev
│   │   ├── keycloak/
│   │   │   ├── realm-export.json
│   │   │   └── Dockerfile
│   │   ├── grafana/                      # dashboards json
│   │   ├── prometheus/
│   │   │   └── prometheus.yml
│   │   └── kafka/
│   │       └── topics.sh                 # create topics script
│   ├── k8s/                              # raw manifests (if not using Helm)
│   │   ├── base/
│   │   │   ├── namespace.yaml
│   │   │   ├── postgres.yaml
│   │   │   ├── redis.yaml
│   │   │   ├── kafka.yaml
│   │   │   └── ingress.yaml
│   │   └── overlays/
│   │       ├── dev/
│   │       │   └── kustomization.yaml
│   │       └── prod/
│   │           └── kustomization.yaml
│   └── helm/
│       ├── Chart.yaml
│       ├── values.yaml
│       └── templates/
│           ├── _helpers.tpl
│           ├── configmap.yaml
│           ├── deployment.yaml
│           ├── service.yaml
│           ├── ingress.yaml
│           └── hpa.yaml
│
├── common-libs/
│   ├── pom.xml
│   └── src/
│       ├── main/java/com/ecom/common/
│       │   ├── api/
│       │   │   ├── dto/
│       │   │   │   ├── ProductDto.java
│       │   │   │   ├── OrderItemDto.java
│       │   │   │   ├── OrderSummaryDto.java
│       │   │   │   ├── Money.java
│       │   │   │   └── Address.java
│       │   │   ├── error/
│       │   │   │   ├── ApiError.java
│       │   │   │   ├── ErrorCode.java
│       │   │   │   └── DomainException.java
│       │   │   ├── pagination/
│       │   │   │   └── PageResponse.java
│       │   │   └── validation/
│       │   │       ├── SKU.java
│       │   │       ├── SKUValidator.java
│       │   │       ├── Phone.java
│       │   │       └── PhoneValidator.java
│       │   ├── events/
│       │   │   ├── EventEnvelope.java
│       │   │   ├── Topics.java
│       │   │   ├── order/                # event payloads
│       │   │   │   ├── OrderCreated.java
│       │   │   │   ├── OrderPaid.java
│       │   │   │   └── OrderCancelled.java
│       │   │   ├── payment/
│       │   │   │   ├── PaymentSucceeded.java
│       │   │   │   └── PaymentFailed.java
│       │   │   └── inventory/
│       │   │       ├── InventoryReserved.java
│       │   │       └── InventoryRejected.java
│       │   ├── json/
│       │   │   └── ObjectMapperConfig.java
│       │   ├── security/
│       │   │   ├── Roles.java
│       │   │   ├── Scopes.java
│       │   │   └── AuthenticatedUser.java
│       │   └── web/
│       │       ├── Headers.java
│       │       └── CorrelationIdFilter.java
│       └── test/java/com/ecom/common/
│           └── builders/                 # optional test fixtures (if using test-jar)
│
├── gateway/
│   ├── pom.xml
│   └── src/
│       ├── main/java/com/ecom/gateway/
│       │   ├── GatewayApplication.java
│       │   ├── config/
│       │   │   ├── SecurityConfig.java   # OAuth2 resource server; JWT validation
│       │   │   └── RateLimitConfig.java  # Redis rate limiter
│       │   └── filters/
│       │       └── CorrelationHeaderFilter.java
│       └── main/resources/
│           ├── application.yml           # route predicates, filters, CORS
│           └── logback.xml
│
├── config-server/
│   ├── pom.xml
│   └── src/
│       ├── main/java/com/ecom/configserver/ConfigServerApplication.java
│       └── main/resources/
│           ├── application.yml
│           └── config-repo/              # (dev) embedded git repo for configs
│               ├── gateway.yml
│               ├── catalog-service.yml
│               ├── order-service.yml
│               └── ...
│
├── auth-service/
│   ├── pom.xml
│   └── src/
│       ├── main/java/com/ecom/auth/
│       │   ├── AuthServiceApplication.java
│       │   ├── controller/               # /me, /introspect if rolling your own
│       │   ├── service/
│       │   └── config/
│       └── main/resources/
│           ├── application.yml
│           └── jwks/                     # if using self-hosted JWT keys for dev
│
├── catalog-service/
│   ├── pom.xml
│   └── src/
│       ├── main/java/com/ecom/catalog/
│       │   ├── CatalogServiceApplication.java
│       │   ├── controller/
│       │   │   └── ProductController.java
│       │   ├── service/
│       │   │   ├── ProductService.java
│       │   │   └── mapper/               # MapStruct mappers
│       │   ├── repository/
│       │   │   └── ProductRepository.java
│       │   ├── model/                    # JPA entities (Product, Category, Price)
│       │   ├── dto/                      # local read models if needed
│       │   ├── events/
│       │   │   └── ProductPriceChangedProducer.java
│       │   ├── config/
│       │   │   ├── KafkaConfig.java
│       │   │   ├── OpenApiConfig.java
│       │   │   └── SecurityConfig.java
│       │   └── exception/
│       │       └── RestExceptionHandler.java  # maps DomainException → ApiError
│       ├── main/resources/
│       │   ├── application.yml
│       │   └── db/migration/             # Flyway migrations V1__init.sql ...
│       └── test/java/com/ecom/catalog/
│           ├── ProductControllerTest.java   # @WebMvcTest
│           ├── ProductServiceTest.java
│           └── it/                          # integration tests (Testcontainers)
│               └── CatalogIT.java
│
├── inventory-service/
│   ├── pom.xml
│   └── src/
│       ├── main/java/com/ecom/inventory/
│       │   ├── InventoryServiceApplication.java
│       │   ├── controller/
│       │   ├── service/
│       │   │   └── ReservationService.java
│       │   ├── repository/
│       │   ├── model/
│       │   ├── messaging/
│       │   │   ├── InventoryEventsConsumer.java
│       │   │   └── InventoryEventsProducer.java
│       │   ├── outbox/                    # Outbox entity + publisher
│       │   └── config/
│       ├── main/resources/
│       │   ├── application.yml
│       │   └── db/migration/
│       └── test/java/com/ecom/inventory/
│
├── cart-service/
│   ├── pom.xml
│   └── src/
│       ├── main/java/com/ecom/cart/
│       │   ├── CartServiceApplication.java
│       │   ├── controller/
│       │   ├── service/
│       │   ├── repository/                # if persisting carts; else Redis only
│       │   ├── redis/
│       │   │   └── CartRedisConfig.java
│       │   └── config/
│       ├── main/resources/
│       │   └── application.yml
│       └── test/java/com/ecom/cart/
│
├── order-service/
│   ├── pom.xml
│   └── src/
│       ├── main/java/com/ecom/order/
│       │   ├── OrderServiceApplication.java
│       │   ├── controller/
│       │   │   └── OrderController.java
│       │   ├── service/
│       │   │   ├── OrderService.java
│       │   │   ├── saga/                  # orchestration/choreography helpers
│       │   │   └── idempotency/
│       │   │       └── IdempotencyService.java
│       │   ├── repository/
│       │   │   └── OrderRepository.java
│       │   ├── model/
│       │   │   ├── OrderEntity.java
│       │   │   └── OrderItemEntity.java
│       │   ├── client/                    # Feign/WebClient to other services
│       │   │   ├── InventoryClient.java
│       │   │   └── PaymentClient.java
│       │   ├── messaging/
│       │   │   ├── OrderEventsProducer.java
│       │   │   └── PaymentEventsConsumer.java
│       │   ├── outbox/
│       │   │   ├── OutboxEntity.java
│       │   │   ├── OutboxRepository.java
│       │   │   └── OutboxPublisher.java
│       │   ├── config/
│       │   │   ├── KafkaConfig.java
│       │   │   ├── SecurityConfig.java
│       │   │   └── OpenApiConfig.java
│       │   └── exception/
│       ├── main/resources/
│       │   ├── application.yml
│       │   └── db/migration/
│       └── test/java/com/ecom/order/
│
├── payment-service/
│   ├── pom.xml
│   └── src/
│       ├── main/java/com/ecom/payment/
│       │   ├── PaymentServiceApplication.java
│       │   ├── controller/
│       │   │   ├── PaymentController.java
│       │   │   └── WebhookController.java   # Stripe/Adyen webhooks
│       │   ├── service/
│       │   │   ├── PaymentService.java
│       │   │   └── provider/                # Stripe/Adyen adapters
│       │   ├── config/
│       │   │   ├── WebClientConfig.java
│       │   │   └── SecurityConfig.java
│       │   └── messaging/
│       │       └── PaymentEventsProducer.java
│       ├── main/resources/
│       │   └── application.yml
│       └── test/java/com/ecom/payment/
│
├── notification-service/
│   ├── pom.xml
│   └── src/
│       ├── main/java/com/ecom/notification/
│       │   ├── NotificationServiceApplication.java
│       │   ├── controller/
│       │   │   └── NotificationController.java
│       │   ├── service/
│       │   │   ├── EmailService.java
│       │   │   ├── SmsService.java
│       │   │   └── TemplateService.java
│       │   ├── config/
│       │   │   ├── MailConfig.java
│       │   │   └── KafkaConfig.java
│       │   └── messaging/
│       │       └── NotificationConsumer.java
│       ├── main/resources/
│       │   ├── application.yml
│       │   ├── templates/
│       │   │   ├── order-confirmation.html
│       │   │   ├── shipping-update.html
│       │   │   └── password-reset.html
│       │   └── mail/
│       │       └── mailhog.properties     # dev-only sample
│       └── test/java/com/ecom/notification/
│
├── search-service/
│   ├── pom.xml
│   └── src/
│       ├── main/java/com/ecom/search/
│       │   ├── SearchServiceApplication.java
│       │   ├── controller/
│       │   │   └── SearchController.java
│       │   ├── service/
│       │   │   ├── SearchService.java
│       │   │   └── indexing/
│       │   │       └── ProductIndexer.java
│       │   ├── config/
│       │   │   ├── OpenSearchConfig.java
│       │   │   └── SecurityConfig.java
│       │   └── messaging/
│       │       └── ProductEventsConsumer.java
│       ├── main/resources/
│       │   └── application.yml
│       └── test/java/com/ecom/search/
│
└── tools/                               # optional dev tools and ADRs
    ├── adr/                             # Architecture Decision Records
    └── postman/                         # Postman/Insomnia collections

`````
