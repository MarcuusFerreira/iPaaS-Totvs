package com.totvs.ipaas.backend.application.gateways;

import com.totvs.ipaas.backend.domain.models.user.User;

import java.util.Optional;
import java.util.UUID;

public interface UserRepositoryInterface {

    User save(User user);

    boolean existsByEmail(String email);

    boolean existsById(UUID id);

    Optional<User> findById(UUID id);

}
