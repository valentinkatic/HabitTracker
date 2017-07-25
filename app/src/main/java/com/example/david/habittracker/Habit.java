package com.example.david.habittracker;

/**
 * Created by valentin.katic on 25.7.2017..
 */

public class Habit {

    private int id;
    private String name;
    private String description;
    private long date;
    private long duration;

    public Habit(int id, String name, String description, long date, long duration) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.date = date;
        this.duration = duration;
    }

    public Habit(String name, String description, long date, long duration) {
        this.name = name;
        this.description = description;
        this.date = date;
        this.duration = duration;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }

    public long getDuration() {
        return duration;
    }

    public void setDuration(long duration) {
        this.duration = duration;
    }
}
