package com.example.todo_app.models;

public class TaskDto {

    private Long id;
    private Boolean isCompleted;
    private Boolean isUrgent;
    private String description;

    public TaskDto() {
    }

    public TaskDto(Long id, Boolean isCompleted, Boolean isUrgent, String description) {
        this.id = id;
        this.isCompleted = isCompleted;
        this.isUrgent = isUrgent;
        this.description = description;
    }

    public Long getId() {
        return id;
    }

    public Boolean getIsCompleted() {
        return isCompleted;
    }

    public void setIsCompleted(Boolean completed) {
        isCompleted = completed;
    }

    public Boolean getIsUrgent() {
        return isUrgent;
    }

    public void setIsUrgent(Boolean urgent) {
        isUrgent = urgent;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "TaskDto{" +
                "isCompleted=" + isCompleted +
                ", isUrgent=" + isUrgent +
                ", description='" + description + '\'' +
                '}';
    }
}
