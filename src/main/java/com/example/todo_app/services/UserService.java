package com.example.todo_app.services;

import com.example.todo_app.models.User;
import com.example.todo_app.models.UserDto;

public interface UserService {
    User findByUsername(String username);

    User save(UserDto userDto);

}
