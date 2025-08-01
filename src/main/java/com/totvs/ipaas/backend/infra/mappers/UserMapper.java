package com.totvs.ipaas.backend.infra.mappers;

import com.totvs.ipaas.backend.domain.models.User;
import com.totvs.ipaas.backend.infra.dtos.response.UserResponseDTO;
import com.totvs.ipaas.backend.infra.persistence.entities.UserEntity;

public class UserMapper {

    public UserEntity toEntity(User user) {
        return new UserEntity(user.getId(), user.getName(), user.getEmail());
    }

    public User toDomain(UserEntity userEntity) {
        return new User(userEntity.getId(), userEntity.getName(), userEntity.getEmail());
    }

    public UserResponseDTO toResponse(User user) {
        return new UserResponseDTO(user.getId().toString(), user.getName(), user.getEmail());
    }

}
