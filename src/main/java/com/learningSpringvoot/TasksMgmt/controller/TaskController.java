package com.learningSpringvoot.TasksMgmt.controller;

import com.learningSpringvoot.TasksMgmt.dto.TaskRequestDTO;
import com.learningSpringvoot.TasksMgmt.dto.TasksResponseDTO;
import com.learningSpringvoot.TasksMgmt.model.TaskStatus;
import com.learningSpringvoot.TasksMgmt.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
public class TaskController {

    @Autowired
    private TaskService taskService;

    @GetMapping("/tasks")
    public List<TasksResponseDTO> getAllTasks() {
        return taskService.getAllTasks();
    }

    @PostMapping("tasks/add")
    public TasksResponseDTO addTask(@RequestBody TaskRequestDTO request) {
        return taskService.addTask(request);
    }

    @PutMapping("tasks/update/{id}")
    public TasksResponseDTO updateTask(@RequestBody TaskRequestDTO request, @PathVariable("id") Long taskId) {
        return taskService.updateTask(request, taskId);
    }

    @DeleteMapping("tasks/delete/{id}")
    public String deleteTask(@PathVariable("id") Long taskId) {
        taskService.deleteTask(taskId);
        return "Task deleted successfully";
    }


    @GetMapping("/tasks/filter")
    public List<TasksResponseDTO> filterTasks(@ModelAttribute TaskRequestDTO request) {
        return taskService.filterTasks(request);
    }

    @GetMapping("/tasks/filter/status/{status}")
    public List<TasksResponseDTO> filterTasksByStatus(@PathVariable TaskStatus status) {
        return taskService.filterTasksByStatus(status);
    }

    @GetMapping("/tasks/filter/dueDate/{dueDate}")
    public List<TasksResponseDTO> filterTasksByDueDate(@PathVariable LocalDate dueDate) {
        return taskService.filterTasksByDueDate(dueDate);
    }
}
