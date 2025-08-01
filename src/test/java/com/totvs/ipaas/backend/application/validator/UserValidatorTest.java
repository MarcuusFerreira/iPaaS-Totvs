package com.totvs.ipaas.backend.application.validator;

import com.totvs.ipaas.backend.application.gateways.UserRepositoryInterface;
import com.totvs.ipaas.backend.domain.exception.EmailAlreadyExistsException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserValidatorTest {

    @Mock
    private UserRepositoryInterface repository;

    private UserValidator userValidator;
    private String email;

    @BeforeEach
    public void setUp() {
        userValidator = new UserValidator(repository);
        email = "test@email.com";
    }

    @Test
    @DisplayName("Should not throw EmailAlreadyExistsException")
    public void test01() {
        when(repository.existsByEmail(email)).thenReturn(false);
        assertDoesNotThrow(() -> userValidator.existsByEmail(email));
    }

    @Test
    @DisplayName("Should throw EmailAlreadyExistsException")
    public void test02() {
        when(repository.existsByEmail(email)).thenReturn(true);
        EmailAlreadyExistsException exception = assertThrows(EmailAlreadyExistsException.class, () -> userValidator.existsByEmail(email));
        assertEquals("Email not available", exception.getMessage());
    }
}
