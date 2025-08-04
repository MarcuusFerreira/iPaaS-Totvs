package com.totvs.ipaas.backend.infra.mappers;

import com.totvs.ipaas.backend.domain.models.user.User;
import com.totvs.ipaas.backend.infra.dtos.response.user.UserResponseDTO;
import com.totvs.ipaas.backend.infra.persistence.entities.user.UserEntity;

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
