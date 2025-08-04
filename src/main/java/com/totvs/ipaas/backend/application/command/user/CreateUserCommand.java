package com.totvs.ipaas.backend.application.command.user;

public record CreateUserCommand(
        String name,
        String email
) {
}
