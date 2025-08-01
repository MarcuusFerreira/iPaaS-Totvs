package com.totvs.ipaas.backend.infra.persistence.repositories;

import com.totvs.ipaas.backend.infra.persistence.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface UserRepositoryJpa extends JpaRepository<UserEntity, UUID> {

    boolean existsByEmail(String email);

}
