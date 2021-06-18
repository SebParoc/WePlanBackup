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
    private int minutes;

    @Column
    private int shortBreakMins;

    @Column
    private int longBreakMins;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


    public int getMinutes() {
        return minutes;
    }

    public void setMinutes(int minutes) {
        this.minutes = minutes;
    }


    public int getShortBreakMins() {
        return shortBreakMins;
    }

    public void setShortBreakMins(int shortBreakMins) {
        this.shortBreakMins = shortBreakMins;
    }

    public int getLongBreakMins() {
        return longBreakMins;
    }

    public void setLongBreakMins(int longBreakMins) {
        this.longBreakMins = longBreakMins;
    }

    public String getTimerName() {
        return timerName;
    }

    public void setTimerName(String timerName) {
        this.timerName = timerName;
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
