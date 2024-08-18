package com.rodionov.crud.core.task;

import com.rodionov.crud.core.task.dto.TaskCreationDto;
import com.rodionov.crud.core.task.dto.TaskDto;
import com.rodionov.crud.core.task.dto.TaskUpdateDto;
import com.rodionov.crud.domain.Task;
import org.mapstruct.*;
/**
 * Author: Dmitriy Rodionov
 * Date: 17.08.2024
 */

@Mapper(
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        componentModel = MappingConstants.ComponentModel.SPRING,
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public abstract class TaskMapper {

    @Mapping(target = "createdAt", expression = "java(LocalDateTime.now())")
    @Mapping(target = "status", expression = "java(com.rodionov.crud.domain.TaskStatusEnum.NOT_STARTED)")
    public abstract Task toEntity(TaskCreationDto dto);

    public abstract TaskDto toDto(Task task);

    abstract void update(TaskUpdateDto dto, @MappingTarget Task model);
}
