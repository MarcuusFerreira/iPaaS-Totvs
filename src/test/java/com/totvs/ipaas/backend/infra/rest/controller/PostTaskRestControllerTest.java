package com.totvs.ipaas.backend.infra.rest.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.totvs.ipaas.backend.application.command.task.CreateTaskCommand;
import com.totvs.ipaas.backend.application.usecases.interfaces.task.SaveTask;
import com.totvs.ipaas.backend.domain.exception.ValidationException;
import com.totvs.ipaas.backend.domain.models.task.StatusTask;
import com.totvs.ipaas.backend.domain.models.task.Task;
import com.totvs.ipaas.backend.infra.dtos.request.TaskRequestDTO;
import com.totvs.ipaas.backend.infra.dtos.response.task.TaskResponseDTO;
import com.totvs.ipaas.backend.infra.mappers.TaskMapper;
import com.totvs.ipaas.backend.infra.rest.advice.ErrorType;
import com.totvs.ipaas.backend.infra.rest.advice.RestExceptionHandler;
import com.totvs.ipaas.backend.infra.rest.controller.task.PostTaskRestController;
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
@WebMvcTest(controllers = PostTaskRestController.class)
public class PostTaskRestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private TaskMapper taskMapper;

    @MockitoBean
    private SaveTask saveTask;

    private String title;
    private String description;
    private UUID userId;
    private TaskRequestDTO requestDTO;

    @BeforeEach
    public void setup() {
        title = "Test Title";
        description = "Test Description";
        userId = UUID.randomUUID();
        requestDTO = new TaskRequestDTO(title, description, userId);
    }

    @Test
    @DisplayName("Should return 201 and saved task")
    public void test01() throws Exception {
        Task domainTask = new Task(title.toLowerCase(), description.toLowerCase(), userId);
        TaskResponseDTO responseDTO = new TaskResponseDTO(
                domainTask.getId().toString(),
                domainTask.getTitle(),
                domainTask.getDescription(),
                domainTask.getStatus().getValue(),
                domainTask.getCreationDate(),
                domainTask.getCompletedDate(),
                domainTask.getUserId().toString()
        );

        when(saveTask.execute(any(CreateTaskCommand.class))).thenReturn(domainTask);
        when(taskMapper.toResponse(domainTask)).thenReturn(responseDTO);

        mockMvc.perform(post("/tasks")
                    .contentType(MediaType.APPLICATION_JSON_VALUE)
                    .content(objectMapper.writeValueAsString(requestDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", is(domainTask.getId().toString())))
                .andExpect(jsonPath("$.title", is(domainTask.getTitle())))
                .andExpect(jsonPath("$.description", is(domainTask.getDescription())))
                .andExpect(jsonPath("$.status", is(StatusTask.PENDING.getValue())))
                .andExpect(jsonPath("$.creationDate", is(domainTask.getCreationDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ssXXX")))))
                .andExpect(jsonPath("$.userId", is(userId.toString())));
    }

    @Test
    @DisplayName("Should return 400 when user id not exist")
    public void test02() throws Exception {
        when(saveTask.execute(any(CreateTaskCommand.class))).thenThrow(
                new ValidationException(String.format("User with id %s not exist. Please check the input data", userId))
        );

        mockMvc.perform(post("/tasks")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(requestDTO)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status", is(400)))
                .andExpect(jsonPath("$.title", is(ErrorType.BUSINESS.getValue())))
                .andExpect(jsonPath("$.detail", is("User with id " + userId + " not exist. Please check the input data")))
                .andExpect(jsonPath("$.timestamp", is(notNullValue())))
                .andExpect(jsonPath("$.path", is("/tasks")));
    }

}
