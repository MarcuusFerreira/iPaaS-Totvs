package com.totvs.ipaas.backend.application.gateways;

import com.totvs.ipaas.backend.domain.models.User;

import java.util.Optional;
import java.util.UUID;

public interface UserRepositoryInterface {

    User save(User user);

    boolean existsByEmail(String email);

    Optional<User> findById(UUID id);

}
