package com.totvs.ipaas.backend.infra.persistence.entities.subtask;

import com.totvs.ipaas.backend.infra.persistence.entities.task.TaskEntity;
import jakarta.persistence.*;

import java.time.OffsetDateTime;
import java.util.UUID;

@Entity
@Table(name = "subtask")
public class SubTaskEntity {

    @Id
    private UUID id;
    private String title;
    private String description;
    @Enumerated(EnumType.ORDINAL)
    private StatusSubTaskEntity status;
    private OffsetDateTime creationDate;
    private OffsetDateTime completedDate;
    @ManyToOne
    @JoinColumn(
            name = "task_id",
            referencedColumnName = "id",
            nullable = false,
            foreignKey = @ForeignKey(name = "fk_subtask_task_id")
    )
    private TaskEntity taskEntity;

    public SubTaskEntity() {}

    public SubTaskEntity(UUID id, String title, String description, String status,  OffsetDateTime creationDate, OffsetDateTime completedDate,  UUID taskId) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.status = StatusSubTaskEntity.valueOf(status);
        this.creationDate =  creationDate;
        this.completedDate = completedDate;
        this.taskEntity = new TaskEntity(taskId);
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

    public StatusSubTaskEntity getStatus() {
        return status;
    }

    public void setStatus(StatusSubTaskEntity status) {
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

    public TaskEntity getTaskEntity() {
        return taskEntity;
    }

    public void setTaskEntity(TaskEntity taskEntity) {
        this.taskEntity = taskEntity;
    }
}
