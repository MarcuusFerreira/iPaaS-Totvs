package com.totvs.ipaas.backend.infra.rest.controller.user;

import com.totvs.ipaas.backend.application.usecases.interfaces.user.FindUserById;
import com.totvs.ipaas.backend.domain.models.user.User;
import com.totvs.ipaas.backend.infra.dtos.response.error.ApiErrorDTO;
import com.totvs.ipaas.backend.infra.dtos.response.user.UserResponseDTO;
import com.totvs.ipaas.backend.infra.mappers.UserMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping(value = "users", produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "Users", description = "Operations related to the user resource")
public class GetUserRestController {

    private final FindUserById findUserById;
    private final UserMapper userMapper;

    public GetUserRestController(FindUserById findUserById, UserMapper userMapper) {
        this.findUserById = findUserById;
        this.userMapper = userMapper;
    }

    @Operation(summary = "Find user by id", description = "Returns the user by id")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "User found",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = UserResponseDTO.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "User not found",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ApiErrorDTO.class)
                    )
            )
    })
    @GetMapping("{id}")
    public ResponseEntity<UserResponseDTO> getUser(@PathVariable("id") UUID id) {
        User user = findUserById.execute(id);
        return ResponseEntity.ok(userMapper.toResponse(user));
    }
}
