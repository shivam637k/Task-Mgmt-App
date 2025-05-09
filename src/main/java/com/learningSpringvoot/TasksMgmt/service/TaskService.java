package com.learningSpringvoot.TasksMgmt.service;

import com.learningSpringvoot.TasksMgmt.dto.TaskRequestDTO;
import com.learningSpringvoot.TasksMgmt.dto.TasksResponseDTO;
import com.learningSpringvoot.TasksMgmt.model.TaskStatus;

import java.time.LocalDate;
import java.util.List;

public interface TaskService {
    public List<TasksResponseDTO> getAllTasks();

    public TasksResponseDTO addTask(TaskRequestDTO request);

    public void deleteTask(Long taskId);

    public TasksResponseDTO updateTask(TaskRequestDTO request, long taskId);

    public List<TasksResponseDTO> filterTasks(TaskRequestDTO request);

    public List<TasksResponseDTO> filterTasksByStatus(TaskStatus status);

    public List<TasksResponseDTO> filterTasksByDueDate(LocalDate dueDate);
}
