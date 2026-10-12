package com.example.accessrequest.mapper;

import com.example.accessrequest.dto.AccessRequestView;
import com.example.accessrequest.dto.CreateAccessRequestDto;
import com.example.accessrequest.dto.CreateAccessRequestResponse;
import com.example.accessrequest.model.AccessRequest;
import org.springframework.stereotype.Component;

/** Keeps entity <-> DTO conversion out of the service and controller. */
@Component
public class AccessRequestMapper {

    public AccessRequest toEntity(CreateAccessRequestDto dto) {
        String justification = dto.justification() == null ? null : dto.justification().trim();
        if (justification != null && justification.isEmpty()) {
            justification = null;
        }
        return new AccessRequest(dto.account().trim(), dto.entitlement().trim(), justification);
    }

    public CreateAccessRequestResponse toCreateResponse(AccessRequest entity) {
        return new CreateAccessRequestResponse(entity.getId(), entity.getStatus(), entity.getCreatedAt());
    }

    public AccessRequestView toView(AccessRequest entity) {
        return new AccessRequestView(
                entity.getId(),
                entity.getAccount(),
                entity.getEntitlement(),
                entity.getJustification(),
                entity.getStatus(),
                entity.getCreatedAt());
    }
}
