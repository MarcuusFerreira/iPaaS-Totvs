package com.totvs.ipaas.backend.infra.rest.controller.task;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.totvs.ipaas.backend.application.command.task.ListTaskCommand;
import com.totvs.ipaas.backend.application.pagination.PagedResult;
import com.totvs.ipaas.backend.application.usecases.interfaces.task.ListTasks;
import com.totvs.ipaas.backend.domain.exception.ResourceNotFoundException;
import com.totvs.ipaas.backend.domain.models.task.Task;
import com.totvs.ipaas.backend.infra.rest.advice.RestExceptionHandler;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.UUID;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Import(RestExceptionHandler.class)
@WebMvcTest(controllers = GetTaskRestController.class)
public class GetTaskRestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private ListTasks listTasks;

    @BeforeEach
    public void setup() {

    }

    @Test
    @DisplayName("Should return 200 with data")
    public void test01() throws Exception {
        UUID userId = UUID.randomUUID();
        Task task = new Task("Task 1", "Description 1", userId);
        List<Task> items = List.of(task);
        PagedResult<Task> paged = new PagedResult<>(
                0,
                5,
                1L,
                1,
                items
        );

        given(listTasks.execute(any(ListTaskCommand.class))).willReturn(paged);

        mockMvc.perform(get("/tasks").param("status", "PENDING"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.page", is(0)))
                .andExpect(jsonPath("$.pageSize", is(5)))
                .andExpect(jsonPath("$.totalElements", is(1)))
                .andExpect(jsonPath("$.totalPages", is(1)))
                .andExpect(jsonPath("$.data", is(notNullValue())));
    }

    @Test
    @DisplayName("Should return 404 when not found data")
    public void test02() throws Exception {
        doThrow(new ResourceNotFoundException("Task not found"))
                .when(listTasks).execute(any(ListTaskCommand.class));

        mockMvc.perform(get("/tasks").param("status", "PENDING"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.status", is(404)))
                .andExpect(jsonPath("$.title", is(notNullValue())))
                .andExpect(jsonPath("$.detail", is(notNullValue())))
                .andExpect(jsonPath("$.path", is("/tasks")));

    }

}
