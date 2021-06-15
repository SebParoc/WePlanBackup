package com.bakafulteam.weplan.domains;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import java.time.LocalDate;
import java.time.LocalTime;

@Entity
public class TimeTableEvent extends Task {
    public TimeTableEvent () {
        setTaskType("Timetable Event");
    }

    public TimeTableEvent (String name, String description, LocalDate date, LocalTime endTime) {
        super(name, description, date, endTime);
        setTaskType("Timetable Event");
    }

    @ManyToOne
    private User eventOwner;

    @DateTimeFormat(pattern = "HH:mm")
    @Column
    private LocalTime endTime;

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

    @Override
    public String toString () {
        return "Event Owner = " + getEventOwner() + " --> " + super.toString();
    }
}
