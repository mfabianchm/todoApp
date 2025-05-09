package com.example.todo_app.controllers;

import com.example.todo_app.exception.UnauthorizedAccessException;
import com.example.todo_app.models.Task;
import com.example.todo_app.models.TaskDto;
import com.example.todo_app.models.User;
import com.example.todo_app.services.TaskService;
import com.example.todo_app.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.security.Principal;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class TaskController {

    @Autowired
    private UserDetailsService userDetailsService;

    private final UserService userService;
    private final TaskService taskService;

    public TaskController(TaskService taskService, UserService userService) {
        this.taskService = taskService;
        this.userService = userService;
    }

    /*Spring automatically provides the Principal based on who is logged in. Internally, Spring builds it
    from the Authentication object in the SecurityContext.*/


//GET ALL TASKS
    @GetMapping("/tasks")
    public ResponseEntity<List<TaskDto>> getUserTasks(Principal principal) {

        User user = userService.findByUsername(principal.getName());
        if (user == null) {
            throw new UnauthorizedAccessException("User not found or unauthorized");
        }

        List<TaskDto> taskDtos = taskService.getTasks(user);
        return ResponseEntity.ok(taskDtos);

    }

    // ADD NEW TASK
    @PostMapping("/tasks")
    public ResponseEntity<?> createTask(@RequestBody TaskDto taskDto, Principal principal) {

        // Get username from the authenticated session
        User user = userService.findByUsername(principal.getName());
        if (user == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        Task newTask = taskService.createTask(taskDto, user);
        URI location = URI.create("/api/tasks/" + newTask.getId());
        return ResponseEntity.created(location).body(newTask);

    }

    // DELETE TASK
    @DeleteMapping("/tasks/{taskId}")
    public ResponseEntity<?> deleteTask(@PathVariable Long taskId, Principal principal) {
        taskService.deleteTask(taskId, principal);
        return ResponseEntity.noContent().build();
    }


    //UPDATE TASK
    @PatchMapping("/tasks/{taskId}")
    public ResponseEntity<?> partialUpdateTask(@PathVariable Long taskId, @RequestBody Task updatedTask, Principal principal) {
        Task updatedTaskResult = taskService.updateTask(taskId, updatedTask, principal);
        return ResponseEntity.ok(updatedTaskResult);  // HTTP 200 OK with the updated task
    }


}
