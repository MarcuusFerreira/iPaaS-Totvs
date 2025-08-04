package com.totvs.ipaas.backend.config;

import com.totvs.ipaas.backend.application.gateways.SubTaskRepositoryInterface;
import com.totvs.ipaas.backend.application.usecases.implementations.ListSubTaskImpl;
import com.totvs.ipaas.backend.application.usecases.implementations.SaveSubTaskImpl;
import com.totvs.ipaas.backend.application.usecases.implementations.UpdateSubTaskStatusImpl;
import com.totvs.ipaas.backend.application.usecases.interfaces.ListSubTask;
import com.totvs.ipaas.backend.application.usecases.interfaces.SaveSubTask;
import com.totvs.ipaas.backend.application.usecases.interfaces.UpdateSubTaskStatus;
import com.totvs.ipaas.backend.application.validator.TaskValidator;
import com.totvs.ipaas.backend.infra.gateways.SubTaskRepositoryImpl;
import com.totvs.ipaas.backend.infra.mappers.SubTaskMapper;
import com.totvs.ipaas.backend.infra.persistence.repositories.SubTaskRepositoryJpa;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SubTaskConfig {

    @Bean
    public SubTaskMapper subTaskMapper() {
        return new SubTaskMapper();
    }

    @Bean
    public SubTaskRepositoryInterface subTaskRepositoryInterface(SubTaskRepositoryJpa  subTaskRepositoryJpa, SubTaskMapper subTaskMapper) {
        return new SubTaskRepositoryImpl(subTaskRepositoryJpa, subTaskMapper);
    }

    @Bean
    public SaveSubTask saveSubTask(SubTaskRepositoryInterface subTaskRepository, TaskValidator validator) {
        return new SaveSubTaskImpl(subTaskRepository, validator);
    }

    @Bean
    public UpdateSubTaskStatus updateSubTaskStatus(SubTaskRepositoryInterface subTaskRepository) {
        return new UpdateSubTaskStatusImpl(subTaskRepository);
    }

    @Bean
    public ListSubTask listSubTask(SubTaskRepositoryInterface subTaskRepository) {
        return new ListSubTaskImpl(subTaskRepository);
    }

}
