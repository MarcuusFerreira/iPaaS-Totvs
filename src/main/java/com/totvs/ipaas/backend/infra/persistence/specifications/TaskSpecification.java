package com.totvs.ipaas.backend.infra.persistence.specifications;

import com.totvs.ipaas.backend.infra.persistence.entities.task.TaskEntity;
import com.totvs.ipaas.backend.infra.persistence.entities.task.StatusTaskEntity;
import org.springframework.data.jpa.domain.Specification;

import java.util.UUID;

public class TaskSpecification {

    public static Specification<TaskEntity> hasStatus(StatusTaskEntity status) {
        return (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.equal(root.get("status"), status);
    }

    public static Specification<TaskEntity> hasUserId(UUID userId) {
        return (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.equal(root.join("userEntity").get("id"), userId);
    }

}
