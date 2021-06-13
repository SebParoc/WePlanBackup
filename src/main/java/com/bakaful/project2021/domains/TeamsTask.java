package com.bakaful.project2021.domains;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Entity
public class TeamsTask extends Task {

    public TeamsTask () {
        setTaskType("Teams Task");
    }

    public TeamsTask (String name, String description, LocalDate date, LocalTime taskTime) {
        super(name, description, date, taskTime);
        setTaskType("Teams task");
    }

    @ManyToMany
    private List<User> collaborators = new ArrayList<>();

    @ManyToMany
    private List<WePlanFile> taskFiles = new ArrayList<>();


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
