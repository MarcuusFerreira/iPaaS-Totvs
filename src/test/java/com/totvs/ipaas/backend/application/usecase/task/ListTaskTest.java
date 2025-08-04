package com.totvs.ipaas.backend.application.usecase.task;

import com.totvs.ipaas.backend.application.command.task.ListTaskCommand;
import com.totvs.ipaas.backend.application.gateways.TaskRepositoryInterface;
import com.totvs.ipaas.backend.application.pagination.PagedResult;
import com.totvs.ipaas.backend.application.usecases.implementations.task.ListTasksImpl;
import com.totvs.ipaas.backend.application.usecases.interfaces.task.ListTasks;
import com.totvs.ipaas.backend.domain.exception.ResourceNotFoundException;
import com.totvs.ipaas.backend.domain.models.subtask.SubTask;
import com.totvs.ipaas.backend.domain.models.task.StatusTask;
import com.totvs.ipaas.backend.domain.models.task.Task;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ListTaskTest {

    @Mock
    private TaskRepositoryInterface taskRepository;

    private ListTasks listTasks;

    @BeforeEach
    public void setup() {
        listTasks = new ListTasksImpl(taskRepository);
    }

    @Test
    @DisplayName("Should return data")
    public void test01() {
        UUID userId = UUID.randomUUID();
        ListTaskCommand command = new ListTaskCommand(0, 5, StatusTask.PENDING.getValue(), userId);
        List<Task> items = List.of(
                new Task("Task 1", "Description 1", userId),
                new Task("Task 2", "Description 2", userId)
        );
        PagedResult<Task> expectedResult = new PagedResult<>(1, 10, 2L, 1, items);

        when(taskRepository.findByStatusAndUserId(StatusTask.PENDING, userId, 0, 5))
                .thenReturn(expectedResult);

        PagedResult<Task> result = listTasks.execute(command);

        assertNotNull(result);
        assertEquals(result.totalElements(), 2);
        assertEquals(result.data().get(0).getTitle(), "Task 1");
    }

    @Test
    @DisplayName("Should throw ResourceNotFound")
    public void test02() {
        UUID userId = UUID.randomUUID();
        ListTaskCommand command = new ListTaskCommand(0, 5, StatusTask.PENDING.getValue(), userId);

        doThrow(new ResourceNotFoundException("Resource not found")).when(taskRepository).findByStatusAndUserId(StatusTask.PENDING, userId, 0, 5);
        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () -> {
            listTasks.execute(command);
        });
        assertEquals("Resource not found", exception.getMessage());
    }

}
