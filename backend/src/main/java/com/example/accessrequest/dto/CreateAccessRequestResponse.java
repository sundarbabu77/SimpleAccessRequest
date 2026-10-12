package com.example.accessrequest.dto;

import com.example.accessrequest.model.AccessRequestStatus;

import java.time.LocalDateTime;

/** Response of POST /api/request. */
public record CreateAccessRequestResponse(Long id, AccessRequestStatus status, LocalDateTime createdAt) {
}
