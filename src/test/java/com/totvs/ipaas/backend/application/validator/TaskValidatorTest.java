package com.totvs.ipaas.backend.application.validator;

import com.totvs.ipaas.backend.application.gateways.SubTaskRepositoryInterface;
import com.totvs.ipaas.backend.application.gateways.TaskRepositoryInterface;
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
public class TaskValidatorTest {

    @Mock
    private TaskRepositoryInterface taskRepository;

    @Mock
    private SubTaskRepositoryInterface subTaskRepository;

    private TaskValidator taskValidator;

    @BeforeEach
    public void setup() {
        taskValidator = new TaskValidator(taskRepository, subTaskRepository);
    }

    @Test
    @DisplayName("Should not throw ValidationException on existsByTaskId")
    public void test01() {
        UUID taskId = UUID.randomUUID();
        when(taskRepository.existsById(taskId)).thenReturn(true);

        assertThatCode(() -> taskValidator.existsByTaskId(taskId)).doesNotThrowAnyException();
    }

    @Test
    @DisplayName("Should throw ValidationException on existsByTaskId")
    public void test02() {
        UUID taskId = UUID.randomUUID();
        when(taskRepository.existsById(taskId)).thenReturn(false);

        ValidationException exception = assertThrows(ValidationException.class, () -> {
            taskValidator.existsByTaskId(taskId);
        });

        assertEquals("Task with id " + taskId + " does not exist. Please check the input data",  exception.getMessage());
    }

    @Test
    @DisplayName("Should not throw ValidationException on existOpenSubTasks")
    public void test03() {
        UUID taskId = UUID.randomUUID();
        when(subTaskRepository.existsOpenSubTasks(taskId)).thenReturn(false);

        assertThatCode(() -> taskValidator.existOpenSubTasks(taskId)).doesNotThrowAnyException();
    }

    @Test
    @DisplayName("Should throw ValidationException on existOpenSubTasks")
    public void test04() {
        UUID taskId = UUID.randomUUID();
        when(subTaskRepository.existsOpenSubTasks(taskId)).thenReturn(true);

        ValidationException exception = assertThrows(ValidationException.class, () -> {
            taskValidator.existOpenSubTasks(taskId);
        });

        assertEquals("There are open subtasks for the task with id " + taskId, exception.getMessage());
    }

}
