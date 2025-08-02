package com.totvs.ipaas.backend.infra.persistence.repositories;

import com.totvs.ipaas.backend.infra.persistence.entities.SubTaskEntity;
import com.totvs.ipaas.backend.infra.persistence.enums.StatusSubTaskEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface SubTaskRepositoryJpa extends JpaRepository<SubTaskEntity, UUID> {

    Optional<SubTaskEntity> findById(UUID subTaskId);

    boolean existsById(UUID id);

    boolean existsByStatusNotAndTaskEntityId(StatusSubTaskEntity statusTaskEntity, UUID taskId);

}
