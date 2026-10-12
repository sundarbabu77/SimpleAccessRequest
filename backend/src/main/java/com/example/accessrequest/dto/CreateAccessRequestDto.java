package com.example.accessrequest.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/** Incoming payload for creating an access request. */
public record CreateAccessRequestDto(
        @NotBlank(message = "account is required")
        @Size(max = 100, message = "account must be at most 100 characters")
        String account,

        @NotBlank(message = "entitlement is required")
        @Size(max = 100, message = "entitlement must be at most 100 characters")
        String entitlement,

        @Size(max = 1000, message = "justification must be at most 1000 characters")
        String justification) {
}
