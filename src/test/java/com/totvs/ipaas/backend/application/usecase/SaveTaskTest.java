package com.totvs.ipaas.backend.application.usecase;

import com.totvs.ipaas.backend.application.command.CreateTaskCommand;
import com.totvs.ipaas.backend.application.gateways.TaskRepositoryInterface;
import com.totvs.ipaas.backend.application.usecases.implementations.SaveTaskImpl;
import com.totvs.ipaas.backend.application.usecases.interfaces.SaveTask;
import com.totvs.ipaas.backend.application.validator.UserValidator;
import com.totvs.ipaas.backend.domain.exception.ValidationException;
import com.totvs.ipaas.backend.domain.models.Task;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class SaveTaskTest {

    @Mock
    private TaskRepositoryInterface taskRepository;

    @Mock
    private UserValidator  userValidator;

    private UUID userId;
    private SaveTask saveTask;
    private CreateTaskCommand command;
    private Task task;

    @BeforeEach
    public void setup() {
        userId = UUID.randomUUID();
        String title = "title";
        String description = "description";
        saveTask = new SaveTaskImpl(taskRepository, userValidator);
        command = new CreateTaskCommand(title, description, userId);
        task = new Task(title, description, userId);
    }

    @Test
    @DisplayName("Should save task")
    public void test01() {
        doNothing().when(userValidator).existsByUserId(any(UUID.class));
        when(taskRepository.save(any(Task.class))).thenReturn(task);

        Task savedTask = saveTask.execute(command);

        assertNotNull(savedTask);
        assertEquals("title", savedTask.getTitle());
        assertEquals("description", savedTask.getDescription());
    }

    @Test
    @DisplayName("Should not save user")
    public void test02() {
        String message = String.format("User with id %s not exist. Please check the input data", userId);
        doThrow(new ValidationException(message)).when(userValidator).existsByUserId(any(UUID.class));
        ValidationException exception = assertThrows(ValidationException.class, () -> {
            saveTask.execute(command);
        });
        assertEquals(message, exception.getMessage());
    }

}
