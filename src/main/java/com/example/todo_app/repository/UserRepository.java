package com.example.todo_app.repository;

import com.example.todo_app.models.User;
import com.example.todo_app.models.UserDto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

    User findByUsername(String username);

    User save(UserDto userDto);
}
