package com.totvs.ipaas.backend.infra.persistence.repositories;

import com.totvs.ipaas.backend.infra.persistence.entities.TaskEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface TaskRepositoryJpa extends JpaRepository<TaskEntity, UUID> {

    boolean existsById(UUID id);

}
