package com.totvs.ipaas.backend.domain.models.task;

import com.totvs.ipaas.backend.domain.exception.ValidationException;

public enum StatusTask {

    PENDING("PENDING"),
    IN_PROGRESS("IN_PROGRESS"),
    COMPLETED("COMPLETED");

    private final String value;

    StatusTask(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public StatusTask getNextStatus() {
        return switch (this) {
            case PENDING -> IN_PROGRESS;
            case IN_PROGRESS ->  COMPLETED;
            case COMPLETED -> throw new ValidationException("Cannot update status COMPLETED.");
        };
    }

}
