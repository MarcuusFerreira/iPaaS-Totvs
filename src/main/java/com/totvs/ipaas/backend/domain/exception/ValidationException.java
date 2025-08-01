package com.totvs.ipaas.backend.domain.exception;

public class ValidationException extends ApplicationException {

    public ValidationException() {
        super();
    }

    public ValidationException(String message) {
        super(message);
    }

}
