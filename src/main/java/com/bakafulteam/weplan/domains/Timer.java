package com.bakafulteam.weplan.domains;


import javax.persistence.*;

@Entity
@Table(name= "timers")
public class Timer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String timerName;

    @Column
    private double minutes;

    @Column
    private double shortBreakMins;

    @Column
    private double longBreakMins;

    @ManyToOne
    private User timerOwner;


    public Long getId () {
        return id;
    }

    public void setId (Long id) {
        this.id = id;
    }

    public double getMinutes () {
        return minutes;
    }

    public void setMinutes (double minutes) {
        this.minutes = minutes;
    }

    public double getShortBreakMins () {
        return shortBreakMins;
    }

    public void setShortBreakMins (double shortBreakMins) {
        this.shortBreakMins = shortBreakMins;
    }

    public double getLongBreakMins () {
        return longBreakMins;
    }

    public void setLongBreakMins (double longBreakMins) {
        this.longBreakMins = longBreakMins;
    }

    public String getTimerName () {
        return timerName;
    }

    public void setTimerName (String timerName) {
        this.timerName = timerName;
    }

    public User getTimerOwner () {
        return timerOwner;
    }

    public void setTimerOwner (User timerOwner) {
        this.timerOwner = timerOwner;
    }

    @Override
    public String toString() {
        return "Timer{" +
                "id=" + id +
                ", timerName='" + timerName + '\'' +
                ", minutes=" + minutes +
                ", shortBreakMins=" + shortBreakMins +
                ", longBreakMins=" + longBreakMins +
                '}';
    }
}
