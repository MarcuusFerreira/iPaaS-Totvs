package com.totvs.ipaas.backend.infra.rest.controller.user;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.totvs.ipaas.backend.application.usecases.interfaces.user.FindUserById;
import com.totvs.ipaas.backend.domain.exception.ResourceNotFoundException;
import com.totvs.ipaas.backend.domain.models.user.User;
import com.totvs.ipaas.backend.infra.dtos.response.user.UserResponseDTO;
import com.totvs.ipaas.backend.infra.mappers.UserMapper;
import com.totvs.ipaas.backend.infra.rest.advice.ErrorType;
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
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Import(RestExceptionHandler.class)
@WebMvcTest(controllers = GetUserRestController.class)
public class GetUserRestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private UserMapper userMapper;

    @MockitoBean
    private FindUserById findUserById;

    private UUID id;
    private String name;
    private String email;
    private User domainUser;


    @BeforeEach
    public void setup() {
        id = UUID.randomUUID();
        name = "test user";
        email = "test@email.com";
        domainUser = new User(id, name, email);
    }

    @Test
    @DisplayName("Should return 200 with user founded")
    public void test01() throws Exception {
        UserResponseDTO responseDTO = new UserResponseDTO(domainUser.getId().toString(), domainUser.getName(), domainUser.getEmail());
        when(findUserById.execute(any(UUID.class))).thenReturn(domainUser);
        when(userMapper.toResponse(domainUser)).thenReturn(responseDTO);

        mockMvc.perform(get("/users/{id}", id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(domainUser.getId().toString())))
                .andExpect(jsonPath("$.name", is(domainUser.getName())))
                .andExpect(jsonPath("$.email", is(domainUser.getEmail())));
    }

    @Test
    @DisplayName("Should return 404 when id not exist")
    public void test02() throws Exception {
        when(findUserById.execute(id)).thenThrow(new ResourceNotFoundException(
                String.format("User with id %s not found", id)
        ));
        mockMvc.perform(get("/users/{id}", id))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.status", is(404)))
                .andExpect(jsonPath("$.title", is(ErrorType.RESOURCE_NOT_FOUND.getValue())))
                .andExpect(jsonPath("$.detail", is("User with id " + id + " not found")))
                .andExpect(jsonPath("$.timestamp", notNullValue()))
                .andExpect(jsonPath("$.path", is(String.format("/users/%s", id))));;
    }

    @Test
    @DisplayName("Should return 400 when parameter is invalid")
    public void test03() throws Exception {
        int invalidId = 1;
        mockMvc.perform(get("/users/{id}", invalidId))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status", is(400)))
                .andExpect(jsonPath("$.title", is(ErrorType.INVALID_PATH.getValue())))
                .andExpect(jsonPath("$.detail", is("Parameter 'id' with value '1' cannot be converter in type 'UUID'")))
                .andExpect(jsonPath("$.timestamp", notNullValue()))
                .andExpect(jsonPath("$.path", is(String.format("/users/%s", invalidId))));
    }


}
