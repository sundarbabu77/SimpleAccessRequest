package com.example.accessrequest.model;

import com.fasterxml.jackson.annotation.JsonValue;

/** Lifecycle status of an access request. Serialised in lower case (e.g. "created"). */
public enum AccessRequestStatus {
    CREATED;

    @JsonValue
    public String toJson() {
        return name().toLowerCase();
    }
}
