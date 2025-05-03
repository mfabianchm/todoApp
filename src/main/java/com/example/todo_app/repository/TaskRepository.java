package com.example.todo_app.repository;

import com.example.todo_app.models.Task;
import com.example.todo_app.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Long> {
    List<Task> findByUserId(Long userId);
}
