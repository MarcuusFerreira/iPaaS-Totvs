package com.totvs.ipaas.backend.domain.models;

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

}
