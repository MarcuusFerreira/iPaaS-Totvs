package com.totvs.ipaas.backend.infra.persistence.enums;

public enum StatusSubTaskEntity {

    PENDING("PENDING"),
    IN_PROGRESS("IN_PROGRESS"),
    COMPLETED("COMPLETED");

    private final String value;

    StatusSubTaskEntity(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

}
