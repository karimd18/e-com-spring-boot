package com.ecom.common.api.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record Address (

        @NotBlank(message = "Full name is required")
        @Size(min = 3, max = 100, message = "Full name must be between 3 and 100 characters")
        String fullName,

        @NotBlank(message = "Address line 1 is required")
        @Size(min = 3, max = 100, message = "Address line 1 must be between 3 and 100 characters")
        String addressLine1,

       @Size(min = 3, max = 100, message = "Address line 2 must be between 3 and 100 characters")
       String addressLine2,

       @NotBlank(message = "City is required")
       @Size(min = 3, max = 100, message = "City must be between 3 and 100 characters")
       String city,

       @Size(min = 3, max = 100, message = "State must be between 3 and 100 characters")
       String state,

       @NotBlank(message = "Country is required")
       @Size(max = 20, message = "Postal/Zip code cannot exceed 20 characters")
       String postalCode,

       @NotBlank(message = "Country is required")
       @Size(min = 3, max = 100, message = "Country must be between 3 and 100 characters")
       String country
) {}