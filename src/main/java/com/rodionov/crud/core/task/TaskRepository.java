package com.rodionov.crud.core.task;


import com.rodionov.crud.domain.Task;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Author: Dmitriy Rodionov
 * Date: 17.08.2024
 */

public interface TaskRepository extends JpaRepository<Task, Long> {
}
