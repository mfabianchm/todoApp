package com.example.todo_app.controllers;

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

import java.security.Principal;
import java.util.List;

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
    public ResponseEntity<List<Task>> getUserTasks(Principal principal) {
        //we retrieve the user
        User user = userService.findByUsername(principal.getName());
        System.out.println(principal.getName());
        //retrieve user list of tasks
        List<Task> tasks = taskService.getTasksByUserId(user.getId());
        return  ResponseEntity.ok(tasks);
    }

    // ADD NEW TASK
    @PostMapping("/tasks")
    public ResponseEntity<Task> createTask(@RequestBody TaskDto taskDto, Principal principal) {
        // Get username from the authenticated session
        User user = userService.findByUsername(principal.getName());
        if (user == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        // Associate the task with the authenticated user
        Task task = new Task();
        task.setCompleted(taskDto.getIsCompleted());
        task.setUrgent(taskDto.getIsUrgent());
        task.setDescription(taskDto.getDescription());
        task.setUser(user);

        Task savedTask = taskService.createTask(task, user.getId());
        return ResponseEntity.ok(savedTask);
    }





//    @PostMapping
//    public ResponseEntity<Task> createTask(@RequestBody Task task, @RequestParam Long userId) {
//        Task createdTask = taskService.createTask(task, userId);
//        return ResponseEntity.status(HttpStatus.CREATED).body(createdTask);
//    }



}
