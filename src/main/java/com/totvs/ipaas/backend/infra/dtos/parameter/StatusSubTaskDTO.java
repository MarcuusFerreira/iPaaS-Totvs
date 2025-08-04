package com.totvs.ipaas.backend.infra.dtos.parameter;

public enum StatusSubTaskDTO {

    PENDING("PENDING"),
    IN_PROGRESS("IN_PROGRESS"),
    COMPLETED("COMPLETED");

    private final String value;

    StatusSubTaskDTO(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

}
