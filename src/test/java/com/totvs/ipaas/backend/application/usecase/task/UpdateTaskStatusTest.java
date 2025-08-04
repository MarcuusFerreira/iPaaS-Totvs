package com.totvs.ipaas.backend.application.usecase.task;

import com.totvs.ipaas.backend.application.gateways.SubTaskRepositoryInterface;
import com.totvs.ipaas.backend.application.gateways.TaskRepositoryInterface;
import com.totvs.ipaas.backend.application.usecases.implementations.subtask.UpdateSubTaskStatusImpl;
import com.totvs.ipaas.backend.application.usecases.implementations.task.UpdateTaskStatusImpl;
import com.totvs.ipaas.backend.application.usecases.interfaces.subtask.UpdateSubTaskStatus;
import com.totvs.ipaas.backend.application.usecases.interfaces.task.UpdateTaskStatus;
import com.totvs.ipaas.backend.application.validator.TaskValidator;
import com.totvs.ipaas.backend.domain.exception.ResourceNotFoundException;
import com.totvs.ipaas.backend.domain.exception.ValidationException;
import com.totvs.ipaas.backend.domain.models.task.StatusTask;
import com.totvs.ipaas.backend.domain.models.task.Task;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.OffsetDateTime;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UpdateTaskStatusTest {

    @Mock
    private TaskRepositoryInterface taskRepository;

    @Mock
    private TaskValidator taskValidator;

    private UpdateTaskStatus updateTaskStatus;

    @BeforeEach
    public void setup() {
        updateTaskStatus = new UpdateTaskStatusImpl(taskRepository, taskValidator);
    }

    @Test
    @DisplayName("Should update task")
    public void test01() {
        UUID userId = UUID.randomUUID();
        UUID taskId = UUID.randomUUID();
        Task task = new Task("Task 1", "Description 1", userId);
        task.setStatus(StatusTask.IN_PROGRESS);

        when(taskRepository.findById(taskId)).thenReturn(Optional.of(task));
        doNothing().when(taskValidator).existOpenSubTasks(taskId);

        updateTaskStatus.execute(taskId);

        ArgumentCaptor<Task> captor = ArgumentCaptor.forClass(Task.class);
        verify(taskRepository).save(captor.capture());
        Task saved =  captor.getValue();

        assertEquals(StatusTask.COMPLETED, saved.getStatus());
        assertNotNull(saved.getCompletedDate());
    }

    @Test
    @DisplayName("Should not update task when status is COMPLETED")
    public void test02() {
        UUID userId = UUID.randomUUID();
        UUID taskId = UUID.randomUUID();
        Task task = new Task("Task 1", "Description 1", userId);
        task.setStatus(StatusTask.COMPLETED);
        task.setCompletedDate(OffsetDateTime.now());

        when(taskRepository.findById(taskId)).thenReturn(Optional.of(task));

        ValidationException exception = assertThrows(ValidationException.class, () -> {
            updateTaskStatus.execute(taskId);
        });
        assertEquals("Cannot update status COMPLETED.", exception.getMessage());
    }


    @Test
    @DisplayName("Should not update task when task id not exist")
    public void test03() {
        UUID userId = UUID.randomUUID();
        UUID taskId = UUID.randomUUID();
        Task task = new Task("Task 1", "Description 1", userId);
        task.setStatus(StatusTask.COMPLETED);
        task.setCompletedDate(OffsetDateTime.now());

        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () -> {
            updateTaskStatus.execute(taskId);
        });
        assertEquals(String.format("Task with id %s not found", taskId),  exception.getMessage());
    }

    @Test
    @DisplayName("Should throw Validation exception because have sub tasks open")
    public void test04() {
        UUID userId = UUID.randomUUID();
        UUID taskId = UUID.randomUUID();
        Task task = new Task("Task 1", "Description 1", userId);
        task.setStatus(StatusTask.IN_PROGRESS);

        when(taskRepository.findById(taskId)).thenReturn(Optional.of(task));
        doThrow(new ValidationException(String.format("There are open subtasks for the task with id %s", taskId))).when(taskValidator).existOpenSubTasks(taskId);

        ValidationException exception = assertThrows(ValidationException.class, () -> {
            updateTaskStatus.execute(taskId);
        });
        assertEquals(String.format("There are open subtasks for the task with id %s", taskId), exception.getMessage());
    }

}
