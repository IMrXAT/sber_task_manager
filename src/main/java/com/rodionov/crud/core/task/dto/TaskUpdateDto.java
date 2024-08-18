package com.rodionov.crud.core.task.dto;

import com.rodionov.crud.domain.TaskStatusEnum;
import jakarta.validation.constraints.NotBlank;

/**
 * Author: Dmitriy Rodionov
 * Date: 17.08.2024
 */

public record TaskUpdateDto(@NotBlank String title, String description, TaskStatusEnum status) {}
