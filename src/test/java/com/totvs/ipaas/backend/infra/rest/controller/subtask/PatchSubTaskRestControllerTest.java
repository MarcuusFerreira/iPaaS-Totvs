package com.totvs.ipaas.backend.infra.rest.controller.subtask;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.totvs.ipaas.backend.application.usecases.interfaces.subtask.UpdateSubTaskStatus;
import com.totvs.ipaas.backend.domain.exception.ResourceNotFoundException;
import com.totvs.ipaas.backend.infra.rest.advice.RestExceptionHandler;
import org.junit.jupiter.api.BeforeEach;
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
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Import(RestExceptionHandler.class)
@WebMvcTest(controllers = PatchSubTaskRestController.class)
public class PatchSubTaskRestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private UpdateSubTaskStatus updateSubTaskStatus;

    @BeforeEach
    public void setup() {

    }

    @Test
    @DisplayName("Should return 204")
    public void test01() throws Exception {
        UUID subTaskId = UUID.randomUUID();
        doNothing().when(updateSubTaskStatus).execute(subTaskId);

        mockMvc.perform(patch("/subtasks/{id}/status", subTaskId.toString()))
                .andExpect(status().isNoContent());
    }

    @Test
    @DisplayName("Should return 404")
    public void test02() throws Exception {
        UUID nonExistSubTaskId = UUID.randomUUID();

        doThrow(new ResourceNotFoundException("Sub task not found")).when(updateSubTaskStatus).execute(nonExistSubTaskId);

        mockMvc.perform(patch("/subtasks/{id}/status", nonExistSubTaskId.toString()))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.status", is(404)))
                .andExpect(jsonPath("$.title", is(notNullValue())))
                .andExpect(jsonPath("$.detail", is(notNullValue())))
                .andExpect(jsonPath("$.path", is(String.format("/subtasks/%s/status", nonExistSubTaskId))));
    }

}
