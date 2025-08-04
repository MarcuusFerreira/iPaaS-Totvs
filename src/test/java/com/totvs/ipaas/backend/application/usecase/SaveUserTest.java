package com.totvs.ipaas.backend.application.usecase;

import com.totvs.ipaas.backend.application.command.user.CreateUserCommand;
import com.totvs.ipaas.backend.application.gateways.UserRepositoryInterface;
import com.totvs.ipaas.backend.application.usecases.implementations.user.SaveUserImpl;
import com.totvs.ipaas.backend.application.validator.UserValidator;
import com.totvs.ipaas.backend.domain.exception.EmailAlreadyExistsException;
import com.totvs.ipaas.backend.domain.models.user.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class SaveUserTest {

    @Mock
    private UserRepositoryInterface repository;

    @Mock
    private UserValidator validator;

    private SaveUserImpl saveUser;
    private UUID userId;
    private CreateUserCommand createUserCommand;
    private User user;

    @BeforeEach
    public void setup() {
        saveUser = new SaveUserImpl(repository, validator);
        userId = UUID.randomUUID();
        createUserCommand = new CreateUserCommand("TEST USER", "TEST@EMAIL.COM");
        user = new User(userId, "test user", "test@email.com");
    }

    @Test
    @DisplayName("Should save user")
    public void test01() {
        doNothing().when(validator).existsByEmail(anyString());
        when(repository.save(any(User.class))).thenReturn(user);

        User savedUser = saveUser.execute(createUserCommand);

        assertNotNull(savedUser);
        assertEquals("test user",  savedUser.getName());
        assertEquals("test@email.com",  savedUser.getEmail());
    }

    @Test
    @DisplayName("Should not save user")
    public void test02() {
        String message = "Email not available";
        doThrow(new EmailAlreadyExistsException(message)).when(validator).existsByEmail(anyString());
        EmailAlreadyExistsException exception = assertThrows(EmailAlreadyExistsException.class, () -> {
            saveUser.execute(createUserCommand);
        });
        assertEquals(message, exception.getMessage());
    }

}
