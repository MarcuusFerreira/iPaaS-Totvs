package com.totvs.ipaas.backend.infra.rest.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.totvs.ipaas.backend.application.command.CreateSubTaskCommand;
import com.totvs.ipaas.backend.application.usecases.interfaces.SaveSubTask;
import com.totvs.ipaas.backend.domain.exception.ValidationException;
import com.totvs.ipaas.backend.domain.models.StatusSubTask;
import com.totvs.ipaas.backend.domain.models.SubTask;
import com.totvs.ipaas.backend.infra.dtos.request.SubTaskRequestDTO;
import com.totvs.ipaas.backend.infra.dtos.response.SubTaskResponseDTO;
import com.totvs.ipaas.backend.infra.mappers.SubTaskMapper;
import com.totvs.ipaas.backend.infra.rest.advice.ErrorType;
import com.totvs.ipaas.backend.infra.rest.advice.RestExceptionHandler;
import com.totvs.ipaas.backend.infra.rest.controller.subtask.PostSubTaskRestController;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.format.DateTimeFormatter;
import java.util.UUID;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Import(RestExceptionHandler.class)
@WebMvcTest(PostSubTaskRestController.class)
public class PostSubTaskRestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private SubTaskMapper subTaskMapper;

    @MockitoBean
    private SaveSubTask saveSubTask;

    private UUID taskId;
    String title;
    String description;
    private SubTaskRequestDTO requestDTO;

    @BeforeEach
    public void setup() {
        taskId = UUID.randomUUID();
        title = "title";
        description = "description";
        requestDTO = new SubTaskRequestDTO(title, description);
    }

    @Test
    @DisplayName("Should return 201 and saved sub task")
    public void test01() throws Exception {
        SubTask subTask = new SubTask(title, description, taskId);
        SubTaskResponseDTO responseDTO = new SubTaskResponseDTO(
                subTask.getId().toString(),
                subTask.getTitle(),
                subTask.getDescription(),
                subTask.getStatus().getValue(),
                subTask.getCreationDate(),
                subTask.getCompletedDate(),
                subTask.getTaskId().toString()
        );

        when(saveSubTask.execute(any(CreateSubTaskCommand.class))).thenReturn(subTask);
        when(subTaskMapper.toResponse(subTask)).thenReturn(responseDTO);

        mockMvc.perform(post("/tasks/{taskId}/subtasks", taskId)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(objectMapper.writeValueAsString(requestDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", is(subTask.getId().toString())))
                .andExpect(jsonPath("$.title", is(subTask.getTitle())))
                .andExpect(jsonPath("$.description", is(subTask.getDescription())))
                .andExpect(jsonPath("$.status", is(StatusSubTask.PENDING.getValue())))
                .andExpect(jsonPath("$.creationDate", is(subTask.getCreationDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ssXXX")))))
                .andExpect(jsonPath("$.taskId", is(subTask.getTaskId().toString())));
    }

    @Test
    @DisplayName("Should return 400 when task id not exist")
    public void test02() throws Exception {
        String message = String.format("Task with id %s does not exist. Please check the input data",  taskId);
        when(saveSubTask.execute(any(CreateSubTaskCommand.class))).thenThrow(
                new ValidationException(message)
        );

        mockMvc.perform(post("/tasks/{taskId}/subtasks", taskId)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(objectMapper.writeValueAsString(requestDTO)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status", is(400)))
                .andExpect(jsonPath("$.title", is(ErrorType.BUSINESS.getValue())))
                .andExpect(jsonPath("$.detail", is(message)))
                .andExpect(jsonPath("$.timestamp", is(notNullValue())))
                .andExpect(jsonPath("$.path", is(String.format("/tasks/%s/subtasks", taskId))));
    }

}
