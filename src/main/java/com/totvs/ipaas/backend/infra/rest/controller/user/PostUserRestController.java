package com.totvs.ipaas.backend.infra.rest.controller.user;

import com.totvs.ipaas.backend.application.command.user.CreateUserCommand;
import com.totvs.ipaas.backend.application.usecases.interfaces.user.SaveUser;
import com.totvs.ipaas.backend.domain.models.user.User;
import com.totvs.ipaas.backend.infra.dtos.request.UserRequestDTO;
import com.totvs.ipaas.backend.infra.dtos.response.error.ApiErrorDTO;
import com.totvs.ipaas.backend.infra.dtos.response.user.UserResponseDTO;
import com.totvs.ipaas.backend.infra.mappers.UserMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "Users", description = "Operations related to the user resource")
public class PostUserRestController {

    private final SaveUser saveUser;
    private final UserMapper userMapper;

    public PostUserRestController(SaveUser saveUser, UserMapper userMapper) {
        this.saveUser = saveUser;
        this.userMapper = userMapper;
    }

    @Operation(summary = "Save user", description = "Returns the saved user")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "User created",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = UserResponseDTO.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Error in the data sent by the client",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ApiErrorDTO.class)
                    )
            )
    })
    @PostMapping
    public ResponseEntity<UserResponseDTO> postUser(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(content = @Content(schema = @Schema(implementation = UserRequestDTO.class)))
            @Valid @RequestBody UserRequestDTO userRequestDTO
    ) {
        CreateUserCommand command = new CreateUserCommand(userRequestDTO.name(), userRequestDTO.email());
        User user = saveUser.execute(command);
        UserResponseDTO responseDTO = userMapper.toResponse(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseDTO);
    }

}
