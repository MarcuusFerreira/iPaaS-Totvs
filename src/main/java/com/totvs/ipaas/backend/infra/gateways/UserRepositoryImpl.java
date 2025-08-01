package com.totvs.ipaas.backend.infra.gateways;

import com.totvs.ipaas.backend.application.gateways.UserRepositoryInterface;
import com.totvs.ipaas.backend.domain.models.User;
import com.totvs.ipaas.backend.infra.mappers.UserMapper;
import com.totvs.ipaas.backend.infra.persistence.entities.UserEntity;
import com.totvs.ipaas.backend.infra.persistence.repositories.UserRepositoryJpa;

public class UserRepositoryImpl implements UserRepositoryInterface {

    private final UserRepositoryJpa repository;
    private final UserMapper mapper;

    public UserRepositoryImpl(UserRepositoryJpa repository, UserMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public User save(User user) {
        UserEntity entity = mapper.toEntity(user);
        return mapper.toDomain(repository.save(entity));
    }

    @Override
    public boolean existsByEmail(String email) {
        return repository.existsByEmail(email);
    }

}
