package com.rodionov.crud.core.task.dto;

import com.rodionov.crud.domain.TaskStatusEnum;

import java.time.LocalDateTime;

/**
 * Author: Dmitriy Rodionov
 * Date: 17.08.2024
 */

public record TaskDto(Long id,
                      String title,
                      String description,
                      TaskStatusEnum status,
                      LocalDateTime createdAt,
                      LocalDateTime finishedAt) {}
