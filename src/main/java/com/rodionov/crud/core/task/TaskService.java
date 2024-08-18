package com.rodionov.crud.core.task;

import com.rodionov.crud.core.task.dto.TaskUpdateDto;
import com.rodionov.crud.domain.Task;
import com.rodionov.crud.domain.TaskStatusEnum;

import java.util.List;

/**
 * Author: Dmitriy Rodionov
 * Date: 17.08.2024
 */

public interface TaskService {

    List<Task> findAllTasks();
    Task findTaskById(Long id);
    Task createTask(Task task);
    Task updateTask(TaskUpdateDto task, Long id);

    Task updateStatus(Long id, TaskStatusEnum status);

    void deleteTaskById(Long id);
}
