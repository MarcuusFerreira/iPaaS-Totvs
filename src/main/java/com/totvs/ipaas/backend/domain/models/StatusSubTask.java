package com.totvs.ipaas.backend.domain.models;

import com.totvs.ipaas.backend.domain.exception.ValidationException;

public enum StatusSubTask {

    PENDING("PENDING"),
    IN_PROGRESS("IN_PROGRESS"),
    COMPLETED("COMPLETED");

    private final String value;

    StatusSubTask(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public StatusSubTask getNextStatus() {
        return switch (this) {
            case PENDING -> IN_PROGRESS;
            case IN_PROGRESS ->  COMPLETED;
            case COMPLETED -> throw new ValidationException(String.format("Cannot update status COMPLETED."));
        };
    }

}
