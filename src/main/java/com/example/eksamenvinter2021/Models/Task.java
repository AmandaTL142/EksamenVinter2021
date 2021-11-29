package com.example.eksamenvinter2021.Models;

import java.sql.Time;

public class Task {
    private int taskID;
    private String title;
    private String description;
    private Time estimatedTime;
    private Time timeUsed;
    private String status;

    public Task() {
    }

    public Task(int taskID, String title, String description, Time estimatedTime, Time timeUsed, String status) {
        this.taskID = taskID;
        this.title = title;
        this.description = description;
        this.estimatedTime = estimatedTime;
        this.timeUsed = timeUsed;
        this.status = status;
    }

    public int getTaskID() {
        return taskID;
    }

    public void setTaskID(int taskID) {
        this.taskID = taskID;
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

    public Time getEstimatedTime() {
        return estimatedTime;
    }

    public void setEstimatedTime(Time estimatedTime) {
        this.estimatedTime = estimatedTime;
    }

    public Time getTimeUsed() {
        return timeUsed;
    }

    public void setTimeUsed(Time timeUsed) {
        this.timeUsed = timeUsed;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
