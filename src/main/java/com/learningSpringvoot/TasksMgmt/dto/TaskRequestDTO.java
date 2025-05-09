package com.learningSpringvoot.TasksMgmt.dto;

import com.learningSpringvoot.TasksMgmt.model.TaskStatus;
import lombok.Data;

import java.time.LocalDate;

@Data
public class TaskRequestDTO {
    private String title;
    private String description;
    private LocalDate dueDate;
    private TaskStatus status;
}
