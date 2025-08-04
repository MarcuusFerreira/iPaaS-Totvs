package com.totvs.ipaas.backend.application.usecase.subtask;

import com.totvs.ipaas.backend.application.gateways.SubTaskRepositoryInterface;
import com.totvs.ipaas.backend.application.usecases.implementations.subtask.UpdateSubTaskStatusImpl;
import com.totvs.ipaas.backend.application.usecases.interfaces.subtask.UpdateSubTaskStatus;
import com.totvs.ipaas.backend.domain.exception.ResourceNotFoundException;
import com.totvs.ipaas.backend.domain.exception.ValidationException;
import com.totvs.ipaas.backend.domain.models.subtask.StatusSubTask;
import com.totvs.ipaas.backend.domain.models.subtask.SubTask;
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
public class UpdateSubTaskStatusTest {

    @Mock
    private SubTaskRepositoryInterface subTaskRepository;

    private UpdateSubTaskStatus updateSubTaskStatus;

    @BeforeEach
    public void setup() {
        updateSubTaskStatus = new UpdateSubTaskStatusImpl(subTaskRepository);
    }

    @Test
    @DisplayName("Should update sub task")
    public void test01() {
        UUID taskId = UUID.randomUUID();
        UUID subTaskId = UUID.randomUUID();
        SubTask subTask = new SubTask("Sub Task 1", "Description 1", taskId);
        subTask.setStatus(StatusSubTask.IN_PROGRESS);

        when(subTaskRepository.findById(subTaskId)).thenReturn(Optional.of(subTask));

        updateSubTaskStatus.execute(subTaskId);

        ArgumentCaptor<SubTask> captor = ArgumentCaptor.forClass(SubTask.class);
        verify(subTaskRepository).save(captor.capture());
        SubTask saved = captor.getValue();

        assertEquals(StatusSubTask.COMPLETED, saved.getStatus());
        assertNotNull(saved.getCompletedDate());
    }

    @Test
    @DisplayName("Should not update sub task when status is COMPLETED")
    public void test02() {
        UUID taskId = UUID.randomUUID();
        UUID subTaskId = UUID.randomUUID();
        SubTask subTask = new SubTask("Sub Task 1", "Description 1", taskId);
        subTask.setStatus(StatusSubTask.COMPLETED);
        subTask.setCompletedDate(OffsetDateTime.now());

        when(subTaskRepository.findById(subTaskId)).thenReturn(Optional.of(subTask));

        ValidationException exception = assertThrows(ValidationException.class, () -> {
            updateSubTaskStatus.execute(subTaskId);
        });
        assertEquals("Cannot update status COMPLETED.", exception.getMessage());
    }


    @Test
    @DisplayName("Should not update sub task when task id not exist")
    public void test03() {
        UUID taskId = UUID.randomUUID();
        UUID subTaskId = UUID.randomUUID();
        SubTask subTask = new SubTask("Sub Task 1", "Description 1", taskId);
        subTask.setStatus(StatusSubTask.COMPLETED);
        subTask.setCompletedDate(OffsetDateTime.now());

        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () -> {
            updateSubTaskStatus.execute(subTaskId);
        });
        assertEquals(String.format("SubTask with id %s not found", subTaskId), exception.getMessage());

    }

}
