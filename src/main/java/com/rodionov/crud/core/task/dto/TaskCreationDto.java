package com.rodionov.crud.core.task.dto;

import jakarta.validation.constraints.NotBlank;

/**
 * Author: Dmitriy Rodionov
 * Date: 17.08.2024
 */

public record TaskCreationDto(@NotBlank String title, String description) {
}
