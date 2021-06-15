package com.bakafulteam.weplan.domains;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Column;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;

    @Column(nullable = false)
    protected String name;

    @Column()
    protected String description;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(nullable = false)
    protected Date date;

    @Column(nullable = false)
    protected String taskTime;

    @Column
    protected String taskType;

    public Task () {
    }

    public Task (String name, String description, Date date, String taskTime) {
        this.name = name;
        this.description = description;
        this.date = date;
        this.taskTime = taskTime;
    }

    public Long getId () {
        return id;
    }

    public void setId (Long id) {
        this.id = id;
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

    public Date getDate () {
        return date;
    }

    public void setDate (Date date) {
        this.date = date;
    }

    public String getTaskTime () {
        return taskTime;
    }

    public void setTaskTime (String taskTime) {
        this.taskTime = taskTime;
    }

    public String getTaskType () {
        return taskType;
    }

    protected void setTaskType (String eventType) {
        this.taskType = eventType;
    }

    @Override
    public String toString () {
        return "Task{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", date=" + date +
                ", taskTime=" + taskTime +
                ", taskType='" + taskType + '\'' +
                '}';
    }
}
