package com.rodionov.crud.core.task;

import com.rodionov.crud.core.task.dto.TaskUpdateDto;
import com.rodionov.crud.domain.Task;
import com.rodionov.crud.domain.TaskStatusEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Slf4j
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;
    private final TaskMapper taskMapper;

    public TaskServiceImpl(TaskRepository taskRepository, TaskMapper taskMapper) {
        this.taskRepository = taskRepository;
        this.taskMapper = taskMapper;
    }

    @Override
    public List<Task> findAllTasks() {
        log.info("Получение всех задач из репозитория");
        List<Task> tasks = taskRepository.findAll();
        log.info("Найдено {} задач", tasks.size());
        return tasks;
    }

    @Override
    public Task findTaskById(Long id) {
        log.info("Поиск задачи с id {}", id);
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("Задача с id {} не найдена", id);
                    return new IllegalStateException("Задача с id " + id + " не найдена");
                });
        log.info("Найдена задача: {}", task);
        return task;
    }

    @Override
    public Task createTask(Task task) {
        log.info("Создание задачи: {}", task);
        Task createdTask = taskRepository.save(task);
        log.info("Создана задача: {}", createdTask);
        return createdTask;
    }

    @Override
    @Transactional
    public Task updateTask(TaskUpdateDto taskDto, Long id) {
        log.info("Обновление задачи с id {} с использованием данных: {}", id, taskDto);
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("Невозможно обновить задачу с id {}, так как она не существует", id);
                    return new IllegalStateException("Невозможно обновить задачу с id " + id + ", так как она не существует");
                });

        taskMapper.update(taskDto, task);
        updateFinishedAt(task.getStatus(), task);
        log.info("Обновленная задача: {}", task);
        return taskRepository.save(task);
    }

    @Override
    public Task updateStatus(Long id, TaskStatusEnum status) {
        log.info("Обновление статуса задачи с id {} на {}", id, status);
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("Невозможно обновить статус задачи с id {}, так как она не существует", id);
                    return new IllegalStateException("Невозможно обновить статус задачи с id " + id + ", так как она не существует");
                });
        task.setStatus(status);
        updateFinishedAt(status, task);
        log.info("Статус задачи обновлен: {}", task);
        return taskRepository.save(task);
    }

    @Override
    public void deleteTaskById(Long id) {
        log.info("Удаление задачи с id {}", id);
        taskRepository.deleteById(id);
        log.info("Задача с id {} удалена", id);
    }

    private void updateFinishedAt(TaskStatusEnum status, Task task) {
        if (status == TaskStatusEnum.COMPLETED) {
            task.setFinishedAt(LocalDateTime.now());
            log.info("Задача завершена: установлено время завершения {}", task.getFinishedAt());
        } else {
            task.setFinishedAt(null);
            log.info("Задача не завершена: время завершения установлено в null");
        }
    }
}
