package com.totvs.ipaas.backend.application.validator;

import com.totvs.ipaas.backend.application.gateways.SubTaskRepositoryInterface;
import com.totvs.ipaas.backend.domain.exception.ValidationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class SubTaskValidatorTest {

    @Mock
    private SubTaskRepositoryInterface subTaskRepository;

    private SubTaskValidator subTaskValidator;

    @BeforeEach
    public void setup() {
        subTaskValidator = new SubTaskValidator(subTaskRepository);
    }

    @Test
    @DisplayName("Should not throw ValidationException")
    public void test01() {
        UUID subTaskId = UUID.randomUUID();
        when(subTaskRepository.existsById(subTaskId)).thenReturn(true);

        assertThatCode(() -> subTaskValidator.existsById(subTaskId)).doesNotThrowAnyException();
    }

    @Test
    @DisplayName("Should throw ValidationException")
    public void test02() {
        UUID subTaskId = UUID.randomUUID();
        when(subTaskRepository.existsById(subTaskId)).thenReturn(false);

        ValidationException exception = assertThrows(ValidationException.class, () -> {
            subTaskValidator.existsById(subTaskId);
        });
        assertEquals("User with id " + subTaskId + " not exist. Please check the input data", exception.getMessage());
    }

}
