package com.totvs.ipaas.backend.infra.rest.controller.task;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.totvs.ipaas.backend.application.usecases.interfaces.task.UpdateTaskStatus;
import com.totvs.ipaas.backend.domain.exception.ResourceNotFoundException;
import com.totvs.ipaas.backend.infra.rest.advice.RestExceptionHandler;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.UUID;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Import(RestExceptionHandler.class)
@WebMvcTest(controllers = PatchTaskRestController.class)
public class PatchTaskRestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private UpdateTaskStatus updateTaskStatus;

    @Test
    @DisplayName("Should return 204")
    public void test01() throws Exception {
        UUID id = UUID.randomUUID();
        doNothing().when(updateTaskStatus).execute(id);

        mockMvc.perform(patch("/tasks/{id}/status", id.toString()))
                .andExpect(status().isNoContent());
    }

    @Test
    @DisplayName("Should return 400")
    public void test02() throws Exception {
        UUID nonExistTaskId = UUID.randomUUID();

        doThrow(new ResourceNotFoundException("Task not found")).when(updateTaskStatus).execute(nonExistTaskId);

        mockMvc.perform(patch("/tasks/{id}/status", nonExistTaskId.toString()))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.status", is(404)))
                .andExpect(jsonPath("$.title", is(notNullValue())))
                .andExpect(jsonPath("$.detail", is(notNullValue())))
                .andExpect(jsonPath("$.path", is(String.format("/tasks/%s/status", nonExistTaskId))));

    }

}
