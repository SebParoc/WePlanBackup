package com.bakaful.project2021.domains;


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
    private int seconds;

    @Column
    private int minutes;

    @Column
    private int shortBreakSecs;

    @Column
    private int shortBreakMins;

    @Column
    private int longBreakSecs;

    @Column
    private int longBreakMins;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getSeconds() {
        return seconds;
    }

    public void setSeconds(int seconds) {
        this.seconds = seconds;
    }

    public int getMinutes() {
        return minutes;
    }

    public void setMinutes(int minutes) {
        this.minutes = minutes;
    }

    public int getShortBreakSecs() {
        return shortBreakSecs;
    }

    public void setShortBreakSecs(int shortBreakSecs) {
        this.shortBreakSecs = shortBreakSecs;
    }

    public int getShortBreakMins() {
        return shortBreakMins;
    }

    public void setShortBreakMins(int shortBreakMins) {
        this.shortBreakMins = shortBreakMins;
    }

    public int getLongBreakSecs() {
        return longBreakSecs;
    }

    public void setLongBreakSecs(int longBreakSecs) {
        this.longBreakSecs = longBreakSecs;
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
                ", seconds=" + seconds +
                ", minutes=" + minutes +
                ", shortBreakSecs=" + shortBreakSecs +
                ", shortBreakMins=" + shortBreakMins +
                ", longBreakSecs=" + longBreakSecs +
                ", longBreakMins=" + longBreakMins +
                '}';
    }
}
