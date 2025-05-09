package com.learningSpringvoot.TasksMgmt.mapper;

import com.learningSpringvoot.TasksMgmt.dto.TasksResponseDTO;
import com.learningSpringvoot.TasksMgmt.model.Tasks;

public class TaskMapper {
    public static TasksResponseDTO convertEntityToDto(Tasks task) {
        TasksResponseDTO getAllTasksResponseDTO = new TasksResponseDTO();

        getAllTasksResponseDTO.setTitle(task.getTaskTitle());
        getAllTasksResponseDTO.setDescription(task.getTaskDescription());
        getAllTasksResponseDTO.setStatus(task.getTaskStatus());
        getAllTasksResponseDTO.setDueDate(task.getDueDate());

        return getAllTasksResponseDTO;
    }
}
