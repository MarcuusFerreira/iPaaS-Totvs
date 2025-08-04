package com.totvs.ipaas.backend.infra.persistence.entities.task;

import com.totvs.ipaas.backend.infra.persistence.entities.user.UserEntity;
import jakarta.persistence.*;

import java.time.OffsetDateTime;
import java.util.UUID;

@Entity
@Table(name = "task")
public class TaskEntity {

    @Id
    private UUID id;
    private String title;
    private String description;
    @Enumerated(EnumType.ORDINAL)
    private StatusTaskEntity status;
    private OffsetDateTime creationDate;
    private OffsetDateTime completedDate;
    @ManyToOne
    @JoinColumn(
            name = "user_id",
            referencedColumnName = "id",
            nullable = false,
            foreignKey = @ForeignKey(name = "fk_task_user_id")
    )
    private UserEntity userEntity;

    public TaskEntity() {}

    public TaskEntity(UUID id) {
        this.id = id;
    }

    public TaskEntity(UUID id, String title, String description, String status, OffsetDateTime creationDate, OffsetDateTime completedDate, UUID userId) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.status = StatusTaskEntity.valueOf(status);
        this.creationDate = creationDate;
        this.completedDate = completedDate;
        this.userEntity = new UserEntity(userId);
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public StatusTaskEntity getStatus() {
        return status;
    }

    public void setStatus(StatusTaskEntity status) {
        this.status = status;
    }

    public OffsetDateTime getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(OffsetDateTime creationDate) {
        this.creationDate = creationDate;
    }

    public OffsetDateTime getCompletedDate() {
        return completedDate;
    }

    public void setCompletedDate(OffsetDateTime completedDate) {
        this.completedDate = completedDate;
    }

    public UserEntity getUserEntity() {
        return userEntity;
    }

    public void setUserEntity(UserEntity userEntity) {
        this.userEntity = userEntity;
    }
}
