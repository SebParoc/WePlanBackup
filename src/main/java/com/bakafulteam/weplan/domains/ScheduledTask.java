package com.bakafulteam.weplan.domains;

import org.springframework.format.annotation.DateTimeFormat;

import javax.management.timer.Timer;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Date;

@Entity
public class ScheduledTask extends Task {

    @ManyToOne
    private User taskOwner;

    @Column(nullable = false)
    private String startTime;

    @Column(nullable = false)
    private String endTime;

    public ScheduledTask () {
        setTaskType("Scheduled Task");
        setTaskTime(startTime + " - " + endTime);
    }

    public ScheduledTask (String name, String description, String date, String startTime, String endTime, String taskType, User eventOwner) {
        super(name, description, date, startTime + " - " + endTime, taskType);
        this.taskOwner = eventOwner;
    }

    public User getTaskOwner () {
        return taskOwner;
    }

    public void setTaskOwner (User eventOwner) {
        this.taskOwner = eventOwner;
    }

    public String getStartTime () {
        return startTime;
    }

    public void setStartTime (String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    @Override
    public String toString () {
        return "Event Owner = " + getTaskOwner() + " --> " + super.toString();
    }
}
