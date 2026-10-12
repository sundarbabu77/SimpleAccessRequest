package com.example.accessrequest.repository;

import com.example.accessrequest.model.AccessRequest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AccessRequestRepository extends JpaRepository<AccessRequest, Long> {

    List<AccessRequest> findAllByOrderByCreatedAtDescIdDesc();
}
