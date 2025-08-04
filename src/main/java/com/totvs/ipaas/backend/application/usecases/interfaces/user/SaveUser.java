package com.totvs.ipaas.backend.application.usecases.interfaces.user;

import com.totvs.ipaas.backend.application.command.user.CreateUserCommand;
import com.totvs.ipaas.backend.domain.models.user.User;

public interface SaveUser {

    User execute(CreateUserCommand command);

}
