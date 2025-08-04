package com.totvs.ipaas.backend.infra.persistence.specifications;

import com.totvs.ipaas.backend.infra.persistence.entities.SubTaskEntity;
import com.totvs.ipaas.backend.infra.persistence.enums.StatusSubTaskEntity;
import org.springframework.data.jpa.domain.Specification;

import java.util.UUID;

public class SubTaskSpecification {

    public static Specification<SubTaskEntity> hasTaskId(UUID taskId) {
        return (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.equal(root.join("taskEntity").get("id"), taskId);
    }

    public static Specification<SubTaskEntity> hasStatus(StatusSubTaskEntity status) {
        return (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.equal(root.get("status"), status);
    }

}
