package com.totvs.ipaas.backend.domain.exception;

public class EmailAlreadyExistsException extends ValidationException {

    public EmailAlreadyExistsException(String message) {
        super(message);
    }

}
