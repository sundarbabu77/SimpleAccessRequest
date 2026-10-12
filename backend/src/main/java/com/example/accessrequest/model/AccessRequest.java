package com.example.accessrequest.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@Entity
@Table(name = "access_request")
public class AccessRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String account;

    @Column(nullable = false, length = 100)
    private String entitlement;

    @Column(length = 1000)
    private String justification;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private AccessRequestStatus status;

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    protected AccessRequest() {
        // required by JPA
    }

    public AccessRequest(String account, String entitlement, String justification) {
        this.account = account;
        this.entitlement = entitlement;
        this.justification = justification;
        this.status = AccessRequestStatus.CREATED;
    }

    @PrePersist
    void onCreate() {
        if (createdAt == null) {
            createdAt = LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS);
        }
    }

    public Long getId() { return id; }
    public String getAccount() { return account; }
    public String getEntitlement() { return entitlement; }
    public String getJustification() { return justification; }
    public AccessRequestStatus getStatus() { return status; }
    public LocalDateTime getCreatedAt() { return createdAt; }
}
