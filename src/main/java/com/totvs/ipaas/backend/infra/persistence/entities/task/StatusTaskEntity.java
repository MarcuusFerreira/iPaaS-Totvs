package com.totvs.ipaas.backend.infra.persistence.entities.task;

public enum StatusTaskEntity {

    PENDING("PENDING"),
    IN_PROGRESS("IN_PROGRESS"),
    COMPLETED("COMPLETED");

    private final String value;

    StatusTaskEntity(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

}
