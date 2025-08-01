package com.totvs.ipaas.backend.domain.exception;

public class ResourceNotFoundException extends ApplicationException {

    public ResourceNotFoundException(String message) {
        super(message);
    }

}
