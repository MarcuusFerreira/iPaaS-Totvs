package com.totvs.ipaas.backend.application.usecase;

import com.totvs.ipaas.backend.application.command.CreateSubTaskCommand;
import com.totvs.ipaas.backend.application.gateways.SubTaskRepositoryInterface;
import com.totvs.ipaas.backend.application.usecases.implementations.SaveSubTaskImpl;
import com.totvs.ipaas.backend.application.validator.TaskValidator;
import com.totvs.ipaas.backend.domain.exception.ValidationException;
import com.totvs.ipaas.backend.domain.models.SubTask;
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
public class SaveSubTaskTest {

    @Mock
    private SubTaskRepositoryInterface subTaskRepository;

    @Mock
    private TaskValidator validator;

    private UUID taskId;
    private SaveSubTaskImpl saveSubTask;
    private CreateSubTaskCommand command;
    private SubTask subTask;

    @BeforeEach
    public void setUp() {
        taskId = UUID.randomUUID();
        String title = "title";
        String description = "description";
        saveSubTask = new SaveSubTaskImpl(subTaskRepository, validator);
        command = new CreateSubTaskCommand(title, description, taskId);
        subTask = new SubTask(title, description, taskId);
    }

    @Test
    @DisplayName("Should save sub task")
    public void test01() {
        doNothing().when(validator).existsByTaskId(taskId);
        when(subTaskRepository.save(any(SubTask.class))).thenReturn(subTask);

        SubTask saved = saveSubTask.execute(command);

        assertNotNull(saved);
        assertEquals(subTask.getTitle(), saved.getTitle());
        assertEquals(subTask.getDescription(), saved.getDescription());
    }

    @Test
    @DisplayName("Should not save sub task")
    public void test02() {
        String message = String.format("Task with id %s does not exist. Please check the input data",  taskId);
        doThrow(new ValidationException(message)).when(validator).existsByTaskId(taskId);
        ValidationException exception = assertThrows(ValidationException.class, () -> {
            saveSubTask.execute(command);
        });
        assertEquals(message, exception.getMessage());
    }
}
