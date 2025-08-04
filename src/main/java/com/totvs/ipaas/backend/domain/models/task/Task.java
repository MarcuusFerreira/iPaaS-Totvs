package com.totvs.ipaas.backend.domain.models.task;

import java.time.OffsetDateTime;
import java.util.UUID;

public class Task {

    private UUID id;
    private String title;
    private String description;
    private StatusTask status;
    private OffsetDateTime creationDate;
    private OffsetDateTime completedDate;
    private UUID userId;

    public Task(String title, String description, UUID userId) {
        this.id = UUID.randomUUID();
        this.title = title;
        this.description = description;
        this.status = StatusTask.PENDING;
        this.creationDate = OffsetDateTime.now();
        this.userId = userId;
    }

    public Task(UUID id, String title, String description, String status, OffsetDateTime creationDate, OffsetDateTime completedDate, UUID userId) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.status = StatusTask.valueOf(status);
        this.creationDate = creationDate;
        this.completedDate = completedDate;
        this.userId = userId;
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

    public StatusTask getStatus() {
        return status;
    }

    public void setStatus(StatusTask status) {
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

    public UUID getUserId() {
        return userId;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }
}
