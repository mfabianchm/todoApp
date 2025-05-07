package com.example.todo_app.models;

public class TaskDto {
    private Boolean isCompleted;
    private Boolean isUrgent;
    private String description;

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
