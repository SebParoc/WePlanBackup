package com.bakaful.project2021.domains;

import javax.persistence.*;
import java.time.*;

@Entity
@Table(name = "tasks")
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 50)
    private String taskOwner;

    @Column(nullable = false, length = 40)
    private String name;

    @Column()
    private String description;

    @Column(nullable = false)
    private LocalDate date;

    @Column(nullable = false)
    private LocalTime startTime;

    @Column(nullable = false)
    private LocalTime endTime;

    @Column(nullable = false, length = 20)
    private String priority;

    @Column(nullable = false)
    private String Viewable;

    public Long getId () {
        return id;
    }

    public void setId (Long id) {
        this.id = id;
    }

    public String getTaskOwner () {
        return taskOwner;
    }

    public void setTaskOwner (String taskOwner) {
        this.taskOwner = taskOwner;
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

    public String getViewable () {
        return Viewable;
    }

    public void setViewable (String viewable) {
        Viewable = viewable;
    }
}
