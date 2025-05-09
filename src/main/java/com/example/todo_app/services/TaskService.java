package com.example.todo_app.services;

import com.example.todo_app.exception.TaskNotFoundException;
import com.example.todo_app.exception.UnauthorizedAccessException;
import com.example.todo_app.models.Task;
import com.example.todo_app.models.TaskDto;
import com.example.todo_app.models.User;
import com.example.todo_app.repository.TaskRepository;
import com.example.todo_app.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TaskService {

    private final TaskRepository taskRepository;
    private final UserRepository userRepository;

    public TaskService(TaskRepository taskRepository, UserRepository userRepository) {
        this.taskRepository = taskRepository;
        this.userRepository = userRepository;
    }

    public Task createTask(TaskDto taskDto, User user) {

        // Associate the task with the authenticated user
        Task task = new Task();
        task.setCompleted(taskDto.getIsCompleted());
        task.setUrgent(taskDto.getIsUrgent());
        task.setDescription(taskDto.getDescription());
        task.setUser(user);

        task.setUser(user);
        return taskRepository.save(task);
    }

    public List<TaskDto> getTasks(User user) {
        List<Task> tasks = taskRepository.findByUserId(user.getId());
        return tasks.stream()
                .map(task -> new TaskDto(task.getId(), task.getCompleted(), task.getUrgent(), task.getDescription()))
                .collect(Collectors.toList());
    }

    public void deleteTask(Long id, Principal principal) {

        Optional<Task> optionalTask = taskRepository.findById(id);
        if (optionalTask.isEmpty()) {
            throw new TaskNotFoundException("Task not found with ID: " + id);
        }

        Task task = optionalTask.get();

        // Check if the authenticated user is the owner of the task
        String username = principal.getName();
        if (!task.getUser().getUsername().equals(username)) {
            throw new UnauthorizedAccessException("You are not allowed to delete this task");
        }

        // Delete the task
        taskRepository.delete(task);
    }


    public Task updateTask(Long taskId, Task updatedTask, Principal principal) {
        // Find the task by its ID
        Optional<Task> optionalTask = taskRepository.findById(taskId);
        if (optionalTask.isEmpty()) {
            throw new TaskNotFoundException("Task not found with ID: " + taskId);  // Custom exception
        }

        Task task = optionalTask.get();

        // Check if the authenticated user is the owner of the task
        String username = principal.getName();
        if (!task.getUser().getUsername().equals(username)) {
            throw new UnauthorizedAccessException("You are not allowed to update this task");  // Custom exception
        }

        // Update fields if provided
        if (updatedTask.getDescription() != null) {
            task.setDescription(updatedTask.getDescription());
        }
        if (updatedTask.getUrgent() != null) {
            task.setUrgent(updatedTask.getUrgent());
        }
        if (updatedTask.getCompleted() != null) {
            task.setCompleted(updatedTask.getCompleted());
        }

        // Save the updated task and return
        return taskRepository.save(task);
    }



}
