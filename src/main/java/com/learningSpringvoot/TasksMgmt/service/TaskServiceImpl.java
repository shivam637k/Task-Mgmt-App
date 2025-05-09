package com.learningSpringvoot.TasksMgmt.service;

import com.learningSpringvoot.TasksMgmt.dto.TaskRequestDTO;
import com.learningSpringvoot.TasksMgmt.dto.TasksResponseDTO;
import com.learningSpringvoot.TasksMgmt.mapper.TaskMapper;
import com.learningSpringvoot.TasksMgmt.model.TaskStatus;
import com.learningSpringvoot.TasksMgmt.model.Tasks;
import com.learningSpringvoot.TasksMgmt.model.User;
import com.learningSpringvoot.TasksMgmt.repository.TaskRepository;
import com.learningSpringvoot.TasksMgmt.repository.UserRepository;
import com.learningSpringvoot.TasksMgmt.specification.TaskSpecification;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TaskServiceImpl implements TaskService {

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private UserRepository userRepository;

//    public List<TasksResponseDTO> getAllTasks() {
//        return taskRepository.findAll()
//                .stream()
//                .map(TaskMapper::convertEntityToDto)
//                .collect(Collectors.toList());
//    }

//    public List<Tasks> getAllTasks() {
//        String username = SecurityContextHolder.getContext().getAuthentication().getName();
//        User user = userRepository.findByEmail(username)
//                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
//        return taskRepository.findByUser(user);
//    }

//    public TasksResponseDTO addTask(TaskRequestDTO request) {
//        Tasks newTask = new Tasks();
//
//        newTask.setTaskTitle(request.getTitle());
//        newTask.setTaskDescription(request.getDescription());
//        newTask.setTaskStatus(request.getStatus());
//        newTask.setDueDate(request.getDueDate());
//
//        taskRepository.save(newTask);
//        return TaskMapper.convertEntityToDto(newTask);
//    }

//    @Override
//    public List<TasksResponseDTO> getAllTasks() {
//        String username = SecurityContextHolder.getContext().getAuthentication().getName();
//        User user = userRepository.findByEmail(username)
//                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
//        List<Tasks> tasks = taskRepository.findByUser(user);
//
//        // Convert List<Tasks> to List<TasksResponseDTO>
//        return tasks.stream()
//                .map(task -> {
//                    TasksResponseDTO dto = new TasksResponseDTO();
//                    dto.setTitle(task.getTitle());
//                    dto.setDescription(task.getDescription());
//                    dto.setDueDate(task.getDueDate());
//                    dto.setStatus(task.getStatus());
//                    return dto;
//                })
//                .collect(Collectors.toList());
//    }

    @Override
    public List<TasksResponseDTO> getAllTasks() {
        // Get the authenticated username (email)
        String username = SecurityContextHolder.getContext().getAuthentication().getName();

        // Find the user by their email
        User user = userRepository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        // Find tasks by the authenticated user
        return taskRepository.findByUser(user)
                .stream()
                .map(TaskMapper::convertEntityToDto)  // Map each task to its corresponding DTO
                .collect(Collectors.toList());
    }

    @Override
    public TasksResponseDTO addTask(TaskRequestDTO request) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        Tasks newTask = new Tasks();
        newTask.setTaskTitle(request.getTitle());
        newTask.setTaskDescription(request.getDescription());
        newTask.setTaskStatus(request.getStatus());
        newTask.setDueDate(request.getDueDate());
        newTask.setUser(user);  // Link task to the authenticated user

        taskRepository.save(newTask);
        return TaskMapper.convertEntityToDto(newTask);
    }

//    @Transactional
//    public TasksResponseDTO updateTask(TaskRequestDTO request, long taskId) {
//        Tasks task = taskRepository.findById(taskId)
//                .orElseThrow( () -> new RuntimeException("Task with id: " + taskId + " not found") );
//
//        if(request.getTitle() != null && !request.getTitle().trim().isEmpty()) task.setTaskTitle(request.getTitle());
//        if(request.getDescription() != null) task.setTaskDescription(request.getDescription());
//        if(request.getStatus() != null) task.setTaskStatus(request.getStatus());
//        if(request.getDueDate() != null) task.setDueDate(request.getDueDate());
//
//        taskRepository.save(task);
//        return TaskMapper.convertEntityToDto(task);
//    }

    @Transactional
    public TasksResponseDTO updateTask(TaskRequestDTO request, long taskId) {
        Tasks task = taskRepository.findById(taskId)
                .orElseThrow(() -> new RuntimeException("Task with id: " + taskId + " not found"));

        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User currentUser = userRepository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        if (!task.getUser().equals(currentUser)) {
            throw new RuntimeException("You can only modify your own tasks");
        }

        if (request.getTitle() != null && !request.getTitle().trim().isEmpty()) task.setTaskTitle(request.getTitle());
        if (request.getDescription() != null) task.setTaskDescription(request.getDescription());
        if (request.getStatus() != null) task.setTaskStatus(request.getStatus());
        if (request.getDueDate() != null) task.setDueDate(request.getDueDate());

        taskRepository.save(task);
        return TaskMapper.convertEntityToDto(task);
    }

//    public void deleteTask(Long taskId) {
//        Tasks task = taskRepository.findById(taskId)
//                .orElseThrow( () -> new RuntimeException("Task with id: " + taskId + " not found") );
//
//        taskRepository.delete(task);
//    }

    public void deleteTask(Long taskId) {
        Tasks task = taskRepository.findById(taskId)
                .orElseThrow(() -> new RuntimeException("Task with id: " + taskId + " not found"));

        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User currentUser = userRepository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        if (!task.getUser().equals(currentUser)) {
            throw new RuntimeException("You can only delete your own tasks");
        }

        taskRepository.delete(task);
    }


//    public List<TasksResponseDTO> filterTasks(TaskRequestDTO request) {
//        Specification<Tasks> spec = Specification.where(null);
//
//        if(request.getTitle() != null && !request.getTitle().trim().isEmpty()) {
//            spec = spec.and(TaskSpecification.hasTitle((request.getTitle())));
//        }
//        if(request.getDescription() != null && !request.getDescription().trim().isEmpty()) {
//            spec = spec.and(TaskSpecification.hasDescription(request.getDescription()));
//        }
//        if(request.getStatus() != null) {
//            spec = spec.and(TaskSpecification.hasStatus(request.getStatus()));
//        }
//        if(request.getDueDate() != null) {
//            spec = spec.and(TaskSpecification.hasDueDate(request.getDueDate()));
//        }
//
//        return taskRepository.findAll(spec)
//                .stream()
//                .map(TaskMapper::convertEntityToDto)
//                .collect(Collectors.toList());
//    }
//
//    public List<TasksResponseDTO> filterTasksByStatus(TaskStatus status) {
//        Specification<Tasks> spec = TaskSpecification.hasStatus(status);
//
//        return taskRepository.findAll(spec)
//                .stream()
//                .map(TaskMapper::convertEntityToDto)
//                .collect(Collectors.toList());
//    }
//
//    public List<TasksResponseDTO> filterTasksByDueDate(LocalDate dueDate) {
//        Specification<Tasks> spec = TaskSpecification.hasDueDate(dueDate);
//
//        return taskRepository.findAll(spec)
//                .stream()
//                .map(TaskMapper::convertEntityToDto)
//                .collect(Collectors.toList());
//    }

    public List<TasksResponseDTO> filterTasks(TaskRequestDTO request) {
        Specification<Tasks> spec = Specification.where(null);

        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User currentUser = userRepository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        spec = spec.and(TaskSpecification.hasUser(currentUser));  // Ensure filtering by user

        if (request.getTitle() != null && !request.getTitle().trim().isEmpty()) {
            spec = spec.and(TaskSpecification.hasTitle(request.getTitle()));
        }
        if (request.getDescription() != null && !request.getDescription().trim().isEmpty()) {
            spec = spec.and(TaskSpecification.hasDescription(request.getDescription()));
        }
        if (request.getStatus() != null) {
            spec = spec.and(TaskSpecification.hasStatus(request.getStatus()));
        }
        if (request.getDueDate() != null) {
            spec = spec.and(TaskSpecification.hasDueDate(request.getDueDate()));
        }

        return taskRepository.findAll(spec)
                .stream()
                .map(TaskMapper::convertEntityToDto)
                .collect(Collectors.toList());
    }

    public List<TasksResponseDTO> filterTasksByStatus(TaskStatus status) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User currentUser = userRepository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        Specification<Tasks> spec = TaskSpecification.hasUser(currentUser)  // Ensure filtering by user
                .and(TaskSpecification.hasStatus(status));

        return taskRepository.findAll(spec)
                .stream()
                .map(TaskMapper::convertEntityToDto)
                .collect(Collectors.toList());
    }

    public List<TasksResponseDTO> filterTasksByDueDate(LocalDate dueDate) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User currentUser = userRepository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        Specification<Tasks> spec = TaskSpecification.hasUser(currentUser)  // Ensure filtering by user
                .and(TaskSpecification.hasDueDate(dueDate));

        return taskRepository.findAll(spec)
                .stream()
                .map(TaskMapper::convertEntityToDto)
                .collect(Collectors.toList());
    }
}
