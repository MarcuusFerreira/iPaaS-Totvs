package com.totvs.ipaas.backend.application.usecase;

import com.totvs.ipaas.backend.application.gateways.UserRepositoryInterface;
import com.totvs.ipaas.backend.application.usecases.implementations.FindUserByIdImpl;
import com.totvs.ipaas.backend.domain.exception.ResourceNotFoundException;
import com.totvs.ipaas.backend.domain.models.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class FindUserByIdTest {

    @Mock
    private UserRepositoryInterface repository;

    private FindUserByIdImpl findUserById;
    private User domainUser;

    @BeforeEach
    public void setup() {
        findUserById = new FindUserByIdImpl(repository);
        domainUser = new User(UUID.randomUUID(), "test user", "test@email.com");
    }

    @Test
    @DisplayName("Should return user")
    public void test01() {
        when(repository.findById(any(UUID.class))).thenReturn(Optional.of(domainUser));

        User user = findUserById.execute(UUID.randomUUID());

        assertNotNull(user);
        assertEquals(user.getId(), domainUser.getId());
        assertEquals(user.getEmail(), domainUser.getEmail());
        assertEquals(user.getEmail(), domainUser.getEmail());
    }

    @Test
    @DisplayName("Should throw ResourceNotFoundException")
    public void test02() {
        UUID id = UUID.randomUUID();
        when(repository.findById(any(UUID.class))).thenReturn(Optional.empty());
        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () -> {
            findUserById.execute(id);
        });
        assertEquals(String.format("User with id %s not found", id), exception.getMessage());
    }

}
