package com.learningSpringvoot.TasksMgmt.dto;

import com.learningSpringvoot.TasksMgmt.model.TaskStatus;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class TasksResponseDTO {
    private String title;
    private String description;
    private LocalDate dueDate;
    private TaskStatus status;
}
