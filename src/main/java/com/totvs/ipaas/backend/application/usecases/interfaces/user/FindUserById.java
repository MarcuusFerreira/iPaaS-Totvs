package com.totvs.ipaas.backend.application.usecases.interfaces.user;

import com.totvs.ipaas.backend.domain.models.user.User;

import java.util.UUID;

public interface FindUserById {

    User execute(UUID id);

}
