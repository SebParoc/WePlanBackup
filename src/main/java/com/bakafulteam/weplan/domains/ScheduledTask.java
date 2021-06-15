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
    public ScheduledTask () {
        setTaskType("Scheduled Task");
    }

    public ScheduledTask (String name, String description, Date date, String taskTime) {
        super(name, description, date, taskTime);
        setTaskType("Scheduled Task");
    }

    @ManyToOne
    private User eventOwner;

    @Column(nullable = false)
    private String endTime;

    @Column
    private String timeString;

    public User getEventOwner () {
        return eventOwner;
    }

    public void setEventOwner (User eventOwner) {
        this.eventOwner = eventOwner;
    }

    public String getStartTime() {
        return getTaskTime();
    }

    public void setStartTime(String startTime) {
        setTaskTime(startTime);
    }

    public String getEndTime() {
        return endTime;
    }

    public void  setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getTimeString () {
        return timeString;
    }

    public void setTimeString (String time) {
        this.timeString = time;
    }

    @Override
    public String toString () {
        return "Event Owner = " + getEventOwner() + " --> " + super.toString();
    }
}
