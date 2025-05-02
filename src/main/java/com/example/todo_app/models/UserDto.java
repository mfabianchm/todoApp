package com.example.todo_app.models;


/*📦 What is a DTO?
DTO stands for Data Transfer Object.It's a simple object used to carry data between different
layers of an application — especially between the UI (frontend) and the backend/service/database.
It typically contains only fields and getters/setters — no business logic.
DTOs let you control exactly what data the client sees or sends, useful in APIs or web apps.
*/

public class UserDto {

    private String username;
    private String password;
    private String email;

    public UserDto(String username, String password, String email) {
        this.username = username;
        this.password = password;
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "UserDto{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
