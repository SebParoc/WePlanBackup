package com.bakaful.project2021.domains;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;

@Entity
public class PersonalTask extends Task {
    public PersonalTask () {
        setTaskType("Personal Task");
    }

    public PersonalTask (String name, String description, LocalDate date, LocalTime taskTime) {
        super(name, description, date, taskTime);
        setTaskType("Personal Task");
    }

    @ManyToOne
    private User taskOwner;

    @ManyToMany
    private List<WePlanFile> taskFiles = new ArrayList<>();

    public User getTaskOwner () {
        return taskOwner;
    }

    public void setTaskOwner (User taskOwner) {
        this.taskOwner = taskOwner;
    }

    public List<WePlanFile> getTaskFiles () {
        return taskFiles;
    }

    @Override
    public String toString () {
        return "Task owner = " + getTaskOwner() + " --> " + super.toString() + "Task files" + getTaskFiles();
    }
}