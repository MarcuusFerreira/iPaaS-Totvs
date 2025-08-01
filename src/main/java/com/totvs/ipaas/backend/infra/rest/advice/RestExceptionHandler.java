package com.totvs.ipaas.backend.infra.rest.advice;

import com.totvs.ipaas.backend.domain.exception.ApplicationException;
import com.totvs.ipaas.backend.domain.exception.EmailAlreadyExistsException;
import com.totvs.ipaas.backend.domain.exception.ResourceNotFoundException;
import com.totvs.ipaas.backend.domain.exception.ValidationException;
import com.totvs.ipaas.backend.infra.dtos.response.ApiErrorDTO;
import com.totvs.ipaas.backend.infra.dtos.response.Field;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.validation.BindingResult;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.OffsetDateTime;
import java.util.List;

@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    protected ResponseEntity<Object> handleExceptionInternal(Exception e, @Nullable Object body, HttpHeaders headers, HttpStatus status, WebRequest request) {
        return super.handleExceptionInternal(e, body, headers, status, request);
    }

    @ExceptionHandler({ ApplicationException.class, RuntimeException.class })
    public ResponseEntity<?> handleDefaultException(ApplicationException e, WebRequest request) {
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
        ErrorType type = ErrorType.SYSTEM_ERROR;
        String message = "An internal application error occurred; please contact support.";
        ApiErrorDTO error = createApiError(status, type.getValue(), message, null, request);
        return handleExceptionInternal(e, error, new HttpHeaders(), status, request);
    }

    @Override
    protected ResponseEntity<Object> handleHttpMediaTypeNotSupported(HttpMediaTypeNotSupportedException e, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        ErrorType type = ErrorType.UNINTELLIGIBLE_MESSAGE;
        ApiErrorDTO error = createApiError(HttpStatus.valueOf(status.value()), type.getValue(), e.getMessage(), null, request);
        return handleExceptionInternal(e, error, headers, status, request);
    }

    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<?> handleValidationException(ValidationException e, WebRequest request) {
        HttpStatus status = HttpStatus.BAD_REQUEST;
        ErrorType type = ErrorType.BUSINESS;
        String message = "A business rule was violated. Please check the input data";
        ApiErrorDTO error = createApiError(status, type.getValue(), message, null, request);
        return handleExceptionInternal(e, error, new HttpHeaders(), status, request);
    }

    @ExceptionHandler(EmailAlreadyExistsException.class)
    public ResponseEntity<?>  handleEmailAlreadyExists(EmailAlreadyExistsException e, WebRequest request) {
        HttpStatus status = HttpStatus.BAD_REQUEST;
        ErrorType type = ErrorType.BUSINESS;
        ApiErrorDTO error = createApiError(status, type.getValue(), e.getMessage(), null, request);
        return handleExceptionInternal(e, error, new HttpHeaders(), status, request);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException e, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        ErrorType type = ErrorType.INVALID_DATA;
        BindingResult bindingResult = e.getBindingResult();
        String message = "Provided data is invalid";
        List<Field> fields = bindingResult.getFieldErrors().stream()
                .map(fieldError -> new Field(fieldError.getField(), fieldError.getDefaultMessage()))
                .toList();
        ApiErrorDTO error = createApiError(HttpStatus.valueOf(status.value()), type.getValue(), message, fields, request);
        return handleExceptionInternal(e, error, headers, status, request);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public  ResponseEntity<?> handleResourceNotFoundException(ResourceNotFoundException e, WebRequest request) {
        HttpStatus status = HttpStatus.NOT_FOUND;
        ErrorType type = ErrorType.RESOURCE_NOT_FOUND;
        ApiErrorDTO error = createApiError(status, type.getValue(), e.getMessage(), null, request);
        return handleExceptionInternal(e, error, new HttpHeaders(), status, request);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<?> handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException e, WebRequest request) {
        HttpStatus status = HttpStatus.BAD_REQUEST;
        ErrorType type = ErrorType.INVALID_PATH;
        String paramName = e.getName();
        String requiredType = e.getRequiredType() != null ? e.getRequiredType().getSimpleName() : "unknown";
        String value = e.getValue() != null ? e.getValue().toString() : null;
        String message = String.format("Parameter '%s' with value '%s' cannot be converter in type '%s'", paramName, value, requiredType);
        ApiErrorDTO error = createApiError(status, type.getValue(), message, null, request);
        return handleExceptionInternal(e, error, new HttpHeaders(), status, request);
    }

    private ApiErrorDTO createApiError(HttpStatus status, String title, String detail, List<Field> fields, WebRequest request) {
        return new ApiErrorDTO(
                status.value(),
                title,
                detail,
                OffsetDateTime.now(),
                replaceUri(request.getDescription(false)),
                fields
        );
    }

    private static String replaceUri(String uri) {
        return uri.replace("uri=", "");
    }

}
