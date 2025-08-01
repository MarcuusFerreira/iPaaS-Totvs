package com.totvs.ipaas.backend.infra.rest.advice;

public enum ErrorType {

    UNINTELLIGIBLE_MESSAGE("Unintelligible message"),
    INVALID_PARAMETER("Invalid parameter"),
    INVALID_DATA("Invalid data"),
    RESOURCE_NOT_FOUND("Resource not found"),
    BUSINESS("Business rule violated"),
    SYSTEM_ERROR("System error");

    private final String value;

    ErrorType(String value) {
        this.value = value;
    }

    public String getValue() {
        return this.value;
    }

}
