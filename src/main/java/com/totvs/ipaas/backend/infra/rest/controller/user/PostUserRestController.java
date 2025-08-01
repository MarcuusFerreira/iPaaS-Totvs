package com.totvs.ipaas.backend.infra.rest.controller.user;

import com.totvs.ipaas.backend.application.command.CreateUserCommand;
import com.totvs.ipaas.backend.application.usecases.interfaces.SaveUser;
import com.totvs.ipaas.backend.domain.models.User;
import com.totvs.ipaas.backend.infra.dtos.request.UserRequestDTO;
import com.totvs.ipaas.backend.infra.dtos.response.UserResponseDTO;
import com.totvs.ipaas.backend.infra.mappers.UserMapper;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "users", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
public class PostUserRestController {

    private final SaveUser saveUser;
    private final UserMapper userMapper;

    public PostUserRestController(SaveUser saveUser, UserMapper userMapper) {
        this.saveUser = saveUser;
        this.userMapper = userMapper;
    }

    @PostMapping
    public ResponseEntity<UserResponseDTO> postUser(@Valid @RequestBody UserRequestDTO userRequestDTO) {
        CreateUserCommand command = new CreateUserCommand(userRequestDTO.name(), userRequestDTO.email());
        User user = saveUser.execute(command);
        UserResponseDTO responseDTO = userMapper.toResponse(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseDTO);
    }

}
