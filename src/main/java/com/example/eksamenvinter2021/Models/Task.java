package com.example.eksamenvinter2021.Models;

import java.sql.Time;
import java.util.ArrayList;

public class Task {
    private String title;
    private String description;
    private String estimatedTime;
    private String timeUsed;
    private String status;
    private ArrayList<Task> tasks;

    //private Time startTime;
    //private Time endTIme; //update(status==complete)

    //TODO update time
    //man skal manuelt sætte tiden
    //officielt starttid
    //endTime deadline
    //actual EndTIme

    public Task() {
    }

    public Task(String title, String description, String estimatedTime, String timeUsed, String status) {
        this.title = title;
        this.description = description;
        this.estimatedTime = estimatedTime;
        this.timeUsed = timeUsed;
        this.status = status;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getEstimatedTime() {
        return estimatedTime;
    }

    public void setEstimatedTime(String estimatedTime) {
        this.estimatedTime = estimatedTime;
    }

    public String getTimeUsed() {
        return timeUsed;
    }

    public void setTimeUsed(String timeUsed) {
        this.timeUsed = timeUsed;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public ArrayList<Task> getTasks() {
        return tasks;
    }

    public void setTasks(ArrayList<Task> tasks) {
        this.tasks = tasks;
    }
}
