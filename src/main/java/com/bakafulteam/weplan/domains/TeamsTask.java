package com.bakafulteam.weplan.domains;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
public class TeamsTask extends Task {

    @ManyToMany
    private List<User> collaborators = new ArrayList<>();

    @ManyToMany
    private List<WePlanFile> taskFiles = new ArrayList<>();

    public TeamsTask () {
        setTaskType("Teams Task");
    }

    public TeamsTask (String name, String description, String date, String taskTime, String taskType) {
        super(name, description, date, taskTime, taskType);
    }

    public List<WePlanFile> getTaskFiles () {
        return taskFiles;
    }

    public List<User> getCollaborators () {
        return collaborators;
    }

    @Override
    public String toString () {
        return "Collaborators = " + getCollaborators() + " --> " + super.toString();
    }
}
