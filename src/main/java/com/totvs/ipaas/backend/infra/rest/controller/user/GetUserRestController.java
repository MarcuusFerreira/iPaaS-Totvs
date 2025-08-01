package com.totvs.ipaas.backend.infra.rest.controller.user;

import com.totvs.ipaas.backend.application.usecases.interfaces.FindUserById;
import com.totvs.ipaas.backend.domain.models.User;
import com.totvs.ipaas.backend.infra.dtos.response.UserResponseDTO;
import com.totvs.ipaas.backend.infra.mappers.UserMapper;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping(value = "users", produces = MediaType.APPLICATION_JSON_VALUE)
public class GetUserRestController {

    private final FindUserById findUserById;
    private final UserMapper userMapper;

    public GetUserRestController(FindUserById findUserById, UserMapper userMapper) {
        this.findUserById = findUserById;
        this.userMapper = userMapper;
    }

    @GetMapping("{id}")
    public ResponseEntity<UserResponseDTO> getUser(@PathVariable("id") UUID id) {
        User user =  findUserById.execute(id);
        return ResponseEntity.ok(userMapper.toResponse(user));
    }
}
