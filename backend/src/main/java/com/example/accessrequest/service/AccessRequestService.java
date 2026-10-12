package com.example.accessrequest.service;

import com.example.accessrequest.dto.AccessRequestView;
import com.example.accessrequest.dto.CreateAccessRequestDto;
import com.example.accessrequest.dto.CreateAccessRequestResponse;
import com.example.accessrequest.mapper.AccessRequestMapper;
import com.example.accessrequest.model.AccessRequest;
import com.example.accessrequest.repository.AccessRequestRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class AccessRequestService {

    private final AccessRequestRepository repository;
    private final AccessRequestMapper mapper;

    public AccessRequestService(AccessRequestRepository repository, AccessRequestMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Transactional
    public CreateAccessRequestResponse create(CreateAccessRequestDto dto) {
        AccessRequest saved = repository.save(mapper.toEntity(dto));
        return mapper.toCreateResponse(saved);
    }

    @Transactional(readOnly = true)
    public List<AccessRequestView> findAll() {
        return repository.findAllByOrderByCreatedAtDescIdDesc().stream()
                .map(mapper::toView)
                .toList();
    }
}
