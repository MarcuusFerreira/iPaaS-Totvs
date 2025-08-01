package com.totvs.ipaas.backend.application.usecases.interfaces;

import com.totvs.ipaas.backend.application.command.CreateUserCommand;
import com.totvs.ipaas.backend.domain.models.User;

public interface SaveUser {

    User execute(CreateUserCommand command);

}
