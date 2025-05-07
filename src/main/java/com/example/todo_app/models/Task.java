package com.example.todo_app.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;


//Task is the child entity
@Entity
@Table(name = "tasks")
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "task_seq")
    @SequenceGenerator(name = "task_seq", sequenceName = "task_sequence", allocationSize = 1)
    private Long id;

    private Boolean isCompleted;
    private Boolean isUrgent;
    private String description;

   /* @ManyToOne annotation defines a one to many relationship between the student entity and the
    university entity. This annotation means that many instances of this entity are mapped
    to one instance of another entity- Many students are in one university.*/

     /* @JoinColumn annotation will create a column in the student table to store the primary
     key of the university table.*/
     @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
     @JsonBackReference
    private User user;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean getCompleted() {
        return isCompleted;
    }

    public void setCompleted(Boolean completed) {
        isCompleted = completed;
    }

    public Boolean getUrgent() {
        return isUrgent;
    }

    public void setUrgent(Boolean urgent) {
        isUrgent = urgent;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "Task{" +
                "id=" + id +
                ", isCompleted=" + isCompleted +
                ", isUrgent=" + isUrgent +
                ", description='" + description + '\'' +
                ", user=" + user +
                '}';
    }
}
