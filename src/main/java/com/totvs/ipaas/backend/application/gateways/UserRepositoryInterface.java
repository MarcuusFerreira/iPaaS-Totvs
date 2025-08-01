package com.totvs.ipaas.backend.application.gateways;

import com.totvs.ipaas.backend.domain.models.User;

public interface UserRepositoryInterface {

    User save(User user);

    boolean existsByEmail(String email);

}
