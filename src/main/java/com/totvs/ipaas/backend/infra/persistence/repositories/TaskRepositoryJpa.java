package com.totvs.ipaas.backend.infra.persistence.repositories;

import com.totvs.ipaas.backend.infra.persistence.entities.task.TaskEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface TaskRepositoryJpa extends JpaRepository<TaskEntity, UUID> {

    boolean existsById(UUID id);

    Page<TaskEntity> findAll(Specification<TaskEntity> spec, Pageable pageable);

}
