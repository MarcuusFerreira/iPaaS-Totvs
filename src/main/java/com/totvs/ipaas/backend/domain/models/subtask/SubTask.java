package com.totvs.ipaas.backend.domain.models.subtask;

import java.time.OffsetDateTime;
import java.util.UUID;

public class SubTask {

    private UUID id;
    private String title;
    private String description;
    private StatusSubTask status;
    private OffsetDateTime creationDate;
    private OffsetDateTime completedDate;
    private UUID taskId;

    public SubTask(String title, String description, UUID taskId) {
        this.id = UUID.randomUUID();
        this.title = title;
        this.description = description;
        this.status = StatusSubTask.PENDING;
        this.creationDate = OffsetDateTime.now();
        this.taskId = taskId;
    }

    public SubTask(UUID id, String title, String description, String status,  OffsetDateTime creationDate, OffsetDateTime completedDate, UUID taskId) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.status = StatusSubTask.valueOf(status);
        this.creationDate = creationDate;
        this.completedDate = completedDate;
        this.taskId = taskId;
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

    public StatusSubTask getStatus() {
        return status;
    }

    public void setStatus(StatusSubTask status) {
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

    public UUID getTaskId() {
        return taskId;
    }

    public void setTaskId(UUID taskId) {
        this.taskId = taskId;
    }
}
