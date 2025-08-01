package com.totvs.ipaas.backend.application.command;

public record CreateUserCommand(
        String name,
        String email
) {
}
