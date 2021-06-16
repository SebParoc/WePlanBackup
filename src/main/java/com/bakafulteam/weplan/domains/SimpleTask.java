package com.bakafulteam.weplan.domains;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;

@Entity
public class SimpleTask extends Task {

    @ManyToOne
    private User taskOwner;

    @ManyToMany
    private List<WePlanFile> taskFiles = new ArrayList<>();;

    public SimpleTask () {
        setTaskType("Simple Task");
    }

    public SimpleTask (String name, String description, String date, String taskTime, String taskType, User taskOwner) {
        super(name, description, date, taskTime, taskType);
        this.taskOwner = taskOwner;
    }

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