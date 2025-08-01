package com.totvs.ipaas.backend.infra.rest.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.totvs.ipaas.backend.application.command.CreateUserCommand;
import com.totvs.ipaas.backend.application.usecases.interfaces.SaveUser;
import com.totvs.ipaas.backend.domain.exception.EmailAlreadyExistsException;
import com.totvs.ipaas.backend.domain.models.User;
import com.totvs.ipaas.backend.infra.dtos.request.UserRequestDTO;
import com.totvs.ipaas.backend.infra.dtos.response.UserResponseDTO;
import com.totvs.ipaas.backend.infra.mappers.UserMapper;
import com.totvs.ipaas.backend.infra.rest.advice.ErrorType;
import com.totvs.ipaas.backend.infra.rest.advice.RestExceptionHandler;
import com.totvs.ipaas.backend.infra.rest.controller.user.PostUserRestController;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.UUID;

import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Import(RestExceptionHandler.class)
@WebMvcTest(controllers = PostUserRestController.class)
public class PostUserRestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private SaveUser saveUser;

    @MockitoBean
    private UserMapper userMapper;

    private UUID userId;
    private String name;
    private String email;
    private UserRequestDTO requestDTO;

    @BeforeEach
    public void setup() {
        userId = UUID.randomUUID();
        name = "Test User";
        email = "test@email.com";
        requestDTO = new UserRequestDTO(name, email);
    }

    @Test
    @DisplayName("Should save user and return 201")
    public void test01() throws Exception {
        User domainUser = new User(userId, name.toLowerCase(), email.toLowerCase());
        UserResponseDTO responseDTO = new UserResponseDTO(userId.toString(), domainUser.getName(), domainUser.getEmail());

        when(saveUser.execute(any(CreateUserCommand.class))).thenReturn(domainUser);
        when(userMapper.toResponse(domainUser)).thenReturn(responseDTO);

        mockMvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(requestDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", is(domainUser.getId().toString())))
                .andExpect(jsonPath("$.name", is(domainUser.getName())))
                .andExpect(jsonPath("$.email", is(domainUser.getEmail())));
    }

    @Test
    @DisplayName("Should return 400 when email already exists")
    public void test02() throws Exception {
        when(saveUser.execute(any(CreateUserCommand.class)))
                .thenThrow(new EmailAlreadyExistsException("Email not available"));

        mockMvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON.toString())
                        .content(objectMapper.writeValueAsString(requestDTO)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status", is(400)))
                .andExpect(jsonPath("$.title", is(ErrorType.BUSINESS.getValue())))
                .andExpect(jsonPath("$.detail", is("Email not available")))
                .andExpect(jsonPath("$.timestamp", notNullValue()))
                .andExpect(jsonPath("$.path", is("/users")));
    }

    @Test
    @DisplayName("Should return 415 when the media type is not supported")
    public void test03() throws Exception {
        mockMvc.perform(post("/users")
                .contentType(MediaType.APPLICATION_XML)
                .content(objectMapper.writeValueAsString(requestDTO)))
                .andExpect(status().isUnsupportedMediaType())
                .andExpect(jsonPath("$.status", is(415)))
                .andExpect(jsonPath("$.title", is(ErrorType.UNINTELLIGIBLE_MESSAGE.getValue())))
                .andExpect(jsonPath("$.detail", is("Content-Type 'application/xml' is not supported")))
                .andExpect(jsonPath("$.timestamp", notNullValue()))
                .andExpect(jsonPath("$.path", is("/users")));

    }

    @Test
    @DisplayName("Should return 400 when input data is invalid")
    public void test04() throws Exception {
        UserRequestDTO invalidRequest = new  UserRequestDTO(null, null);
        mockMvc.perform(post("/users")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(objectMapper.writeValueAsString(invalidRequest)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status", is(400)))
                .andExpect(jsonPath("$.title", is(ErrorType.INVALID_DATA.getValue())))
                .andExpect(jsonPath("$.detail", is("Provided data is invalid")))
                .andExpect(jsonPath("$.timestamp", notNullValue()))
                .andExpect(jsonPath("$.path", is("/users")))
                .andExpect(jsonPath("$.fields", hasSize(greaterThanOrEqualTo(1))))
                .andExpect(jsonPath("$.fields[?(@.name=='email')].message", contains("Email cannot be blank")))
                .andExpect(jsonPath("$.fields[?(@.name=='name')].message", contains("Name cannot be blank")));
    }

    @Test
    @DisplayName("Should return 400 when email is malformed")
    public void test05() throws Exception {
        UserRequestDTO invalidRequest = new  UserRequestDTO(null, "testeemail.com");
        mockMvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(invalidRequest)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status", is(400)))
                .andExpect(jsonPath("$.title", is(ErrorType.INVALID_DATA.getValue())))
                .andExpect(jsonPath("$.detail", is("Provided data is invalid")))
                .andExpect(jsonPath("$.timestamp", notNullValue()))
                .andExpect(jsonPath("$.path", is("/users")))
                .andExpect(jsonPath("$.fields", hasSize(greaterThanOrEqualTo(1))))
                .andExpect(jsonPath("$.fields[?(@.name=='email')].message", contains("Email is invalid")));
    }

}
