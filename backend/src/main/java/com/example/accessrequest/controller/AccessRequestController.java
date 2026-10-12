package com.example.accessrequest.controller;

import com.example.accessrequest.dto.AccessRequestView;
import com.example.accessrequest.dto.CreateAccessRequestDto;
import com.example.accessrequest.dto.CreateAccessRequestResponse;
import com.example.accessrequest.service.AccessRequestService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
public class AccessRequestController {

    private final AccessRequestService service;

    public AccessRequestController(AccessRequestService service) {
        this.service = service;
    }

    @PostMapping("/request")
    @ResponseStatus(HttpStatus.CREATED)
    public CreateAccessRequestResponse create(@Valid @RequestBody CreateAccessRequestDto dto) {
        return service.create(dto);
    }

    @GetMapping("/requests")
    public List<AccessRequestView> findAll() {
        return service.findAll();
    }
}
