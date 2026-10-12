package com.example.accessrequest.dto;

import com.example.accessrequest.model.AccessRequestStatus;

import java.time.LocalDateTime;

/** Full representation of an access request, returned by GET /api/requests. */
public record AccessRequestView(
        Long id,
        String account,
        String entitlement,
        String justification,
        AccessRequestStatus status,
        LocalDateTime createdAt) {
}
