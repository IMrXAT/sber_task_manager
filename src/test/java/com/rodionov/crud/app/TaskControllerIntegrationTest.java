package com.rodionov.crud.app;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.nullValue;
import static org.hamcrest.Matchers.not;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class TaskControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void testCreateTask() throws Exception {
        mockMvc.perform(post("/api/v1/tasks/new")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"title\":\"title\",\"description\":\"description\"}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.title").value("title"))
                .andExpect(jsonPath("$.description").value("description"));
    }

    @Test
    void testGetAllTasks() throws Exception {
        mockMvc.perform(get("/api/v1/tasks/all")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray());
    }

    @Test
    void testGetTaskById() throws Exception {
        mockMvc.perform(get("/api/v1/tasks/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L));
    }

    @Test
    void testUpdateTask() throws Exception {
        mockMvc.perform(post("/api/v1/tasks/new")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"title\":\"title\",\"description\":\"description\"}"))
                .andExpect(status().isCreated());

        mockMvc.perform(put("/api/v1/tasks/update/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"title\":\"new title\",\"description\":\"new description\"}"))
                .andExpect(status().isAccepted())
                .andExpect(jsonPath("$.title").value("new title"))
                .andExpect(jsonPath("$.description").value("new description"));
    }

    @Test
    void testUpdateTaskStatus() throws Exception {
        mockMvc.perform(post("/api/v1/tasks/new")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"title\":\"title\",\"description\":\"description\"}"))
                .andExpect(status().isCreated());

        // Обновляем статус задачи
        mockMvc.perform(patch("/api/v1/tasks/status/{id}", 1L)
                        .param("status", "IN_PROGRESS")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isAccepted())
                .andExpect(jsonPath("$.status").value("IN_PROGRESS"));
    }

    @Test
    void testSetStatusCompletedShouldSetFinishedAt() throws Exception{
        mockMvc.perform(post("/api/v1/tasks/new")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"title\":\"title\",\"description\":\"description\"}"))
                .andExpect(status().isCreated());

        mockMvc.perform(patch("/api/v1/tasks/status/{id}", 1L)
                        .param("status", "IN_PROGRESS")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isAccepted())
                .andExpect(jsonPath("$.finishedAt").doesNotExist());

        mockMvc.perform(patch("/api/v1/tasks/status/{id}", 1L)
                        .param("status", "COMPLETED")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isAccepted())
                .andExpect(jsonPath("$.finishedAt", not(nullValue())));}
}
