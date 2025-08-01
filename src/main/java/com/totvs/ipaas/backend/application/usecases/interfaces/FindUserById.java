package com.totvs.ipaas.backend.application.usecases.interfaces;

import com.totvs.ipaas.backend.domain.models.User;

import java.util.UUID;

public interface FindUserById {

    User execute(UUID id);

}
