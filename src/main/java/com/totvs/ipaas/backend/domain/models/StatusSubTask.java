package com.totvs.ipaas.backend.domain.models;

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

}
