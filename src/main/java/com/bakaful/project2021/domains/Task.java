package com.bakaful.project2021.domains;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "tasks")
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    @ManyToMany
    private List<User> taskOwners = new ArrayList<>();

    @Column(nullable = false, length = 40)
    private String name;

    @Column()
    private String description;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(nullable = false)
    private LocalDate date;

    @DateTimeFormat(pattern = "HH:mm")
    @Column(nullable = false)
    private LocalTime startTime;

    @DateTimeFormat(pattern = "HH:mm")
    @Column(nullable = false)
    private LocalTime endTime;

    @Column(nullable = false, length = 20)
    private String priority;

    @Column(nullable = false)
    private String shareable;

    public Long getId () {
        return id;
    }

    public void setId (Long id) {
        this.id = id;
    }

    public List<User> getTaskOwners() {
        return taskOwners;
    }

    public String getName () {
        return name;
    }

    public void setName (String name) {
        this.name = name;
    }

    public String getDescription () {
        return description;
    }

    public void setDescription (String description) {
        this.description = description;
    }

    public LocalDate getDate () {
        return date;
    }

    public void setDate (LocalDate date) {
        this.date = date;
    }

    public LocalTime getStartTime () {
        return startTime;
    }

    public void setStartTime (LocalTime startTime) {
        this.startTime = startTime;
    }

    public LocalTime getEndTime () {
        return endTime;
    }

    public void setEndTime (LocalTime endTime) {
        this.endTime = endTime;
    }

    public String getPriority () {
        return priority;
    }

    public void setPriority (String priority) {
        this.priority = priority;
    }

    public String getShareable () {
        return shareable;
    }

    public void setShareable (String viewable) {
        shareable = viewable;
    }

    @Override
    public String toString() {
        return "Task{" +
                "id=" + id +
                '}';
    }
}
