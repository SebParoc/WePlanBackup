package com.bakafulteam.weplan.domains;

import org.springframework.format.annotation.DateTimeFormat;

import javax.management.timer.Timer;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;
import java.time.LocalDate;
import java.time.LocalTime;

@Entity
public class ScheduledTask extends Task {
    public ScheduledTask () {
        setTaskType("Scheduled Task");
    }

    public ScheduledTask (String name, String description, LocalDate date, LocalTime taskTime) {
        super(name, description, date, taskTime);
        setTaskType("Scheduled Task");
    }

    @ManyToOne
    private User eventOwner;

    @DateTimeFormat(pattern = "HH:mm")
    @Column(nullable = false)
    private LocalTime endTime;

    @Column
    private String timeString;

    public User getEventOwner () {
        return eventOwner;
    }

    public void setEventOwner (User eventOwner) {
        this.eventOwner = eventOwner;
    }

    public LocalTime getStartTime() {
        return getTaskTime();
    }

    public void setStartTime(LocalTime startTime) {
        setTaskTime(startTime);
    }

    public LocalTime  getEndTime() {
        return endTime;
    }

    public void  setEndTime(LocalTime endTime) {
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
