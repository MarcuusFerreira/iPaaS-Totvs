package com.totvs.ipaas.backend.config;

import com.totvs.ipaas.backend.application.gateways.SubTaskRepositoryInterface;
import com.totvs.ipaas.backend.application.gateways.TaskRepositoryInterface;
import com.totvs.ipaas.backend.application.usecases.implementations.task.ListTasksImpl;
import com.totvs.ipaas.backend.application.usecases.implementations.task.SaveTaskImpl;
import com.totvs.ipaas.backend.application.usecases.implementations.task.UpdateTaskStatusImpl;
import com.totvs.ipaas.backend.application.usecases.interfaces.task.ListTasks;
import com.totvs.ipaas.backend.application.usecases.interfaces.task.SaveTask;
import com.totvs.ipaas.backend.application.usecases.interfaces.task.UpdateTaskStatus;
import com.totvs.ipaas.backend.application.validator.TaskValidator;
import com.totvs.ipaas.backend.application.validator.UserValidator;
import com.totvs.ipaas.backend.infra.gateways.TaskRepositoryImpl;
import com.totvs.ipaas.backend.infra.mappers.TaskMapper;
import com.totvs.ipaas.backend.infra.persistence.repositories.TaskRepositoryJpa;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TaskConfig {

    @Bean
    public TaskMapper taskMapper() {
        return new TaskMapper();
    }

    @Bean
    public TaskRepositoryInterface taskRepositoryInterface(TaskRepositoryJpa repository, TaskMapper mapper) {
        return new TaskRepositoryImpl(repository, mapper);
    }

    @Bean
    public SaveTask saveTask(TaskRepositoryInterface repository, UserValidator userValidator) {
        return new SaveTaskImpl(repository, userValidator);
    }

    @Bean
    public TaskValidator taskValidator(TaskRepositoryInterface taskRepository, SubTaskRepositoryInterface subTaskRepository) {
        return new TaskValidator(taskRepository, subTaskRepository);
    }

    @Bean
    public UpdateTaskStatus updateTaskStatus(TaskRepositoryInterface taskRepository, TaskValidator validator) {
        return new UpdateTaskStatusImpl(taskRepository, validator);
    }

    @Bean
    public ListTasks listTasks(TaskRepositoryInterface taskRepository) {
        return new ListTasksImpl(taskRepository);
    }
}
