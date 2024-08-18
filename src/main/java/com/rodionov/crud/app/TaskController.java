package com.rodionov.crud.app;

import com.rodionov.crud.core.task.TaskMapper;
import com.rodionov.crud.core.task.TaskServiceImpl;
import com.rodionov.crud.core.task.dto.TaskCreationDto;
import com.rodionov.crud.core.task.dto.TaskDto;
import com.rodionov.crud.core.task.dto.TaskUpdateDto;
import com.rodionov.crud.domain.Task;
import com.rodionov.crud.domain.TaskStatusEnum;
import jakarta.validation.Valid;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/tasks")
@Slf4j
public class TaskController {

    private final TaskServiceImpl taskService;
    private final TaskMapper taskMapper;

    public TaskController(TaskServiceImpl taskService, TaskMapper taskMapper) {
        this.taskService = taskService;
        this.taskMapper = taskMapper;
    }

    @GetMapping("/all")
    @ResponseStatus(HttpStatus.OK)
    public List<TaskDto> getAll() {
        log.info("Запрос на получение всех задач");
        return taskService.findAllTasks()
                .stream()
                .map(taskMapper::toDto)
                .toList();
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public TaskDto getById(@PathVariable @NonNull Long id) {
        log.info("Запрос на получение задачи с id {}", id);
        Task task = taskService.findTaskById(id);
        return taskMapper.toDto(task);
    }

    @PostMapping("/new")
    @ResponseStatus(HttpStatus.CREATED)
    public TaskDto newTask(@Valid @RequestBody TaskCreationDto taskDto) {
        log.info("Запрос на создание новой задачи с данными: {}", taskDto);
        Task task = taskMapper.toEntity(taskDto);
        taskService.createTask(task);
        return taskMapper.toDto(task);
    }

    @PutMapping("/update/{id}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    @Valid
    public TaskDto updateTask(@PathVariable Long id, @RequestBody TaskUpdateDto taskDto) {
        log.info("Запрос на обновление задачи с id {} с использованием данных: {}", id, taskDto);
        Task task = taskService.updateTask(taskDto, id);
        return taskMapper.toDto(task);
    }

    @PatchMapping("/status/{id}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    @Valid
    public TaskDto updateTaskStatus(@PathVariable Long id, @RequestParam TaskStatusEnum status) {
        log.info("Запрос на обновление статуса задачи с id {} на {}", id, status);
        Task task = taskService.updateStatus(id, status);
        return taskMapper.toDto(task);
    }

    @DeleteMapping("delete/{id}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void deleteTask(@PathVariable Long id) {
        log.info("Запрос на удаление задачи с id {}", id);
        taskService.deleteTaskById(id);
    }
}
