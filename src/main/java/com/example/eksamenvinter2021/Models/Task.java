package com.example.eksamenvinter2021.Models;

import java.util.Date;

public class Task {
    private int taskID;
    private String title;
    private String description;
    private double estimatedTime;
    private double timeUsed;
    private String status;
    private String startDate;
    private String endDate;

    public Task() {
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public Task(int taskID, String title, String description, double estimatedTime, double timeUsed, String status, String startDate, String endDate) {
        this.taskID = taskID;
        this.title = title;
        this.description = description;
        this.estimatedTime = estimatedTime;
        this.timeUsed = timeUsed;
        this.status = status;
        this.startDate = startDate;
        this.endDate = endDate;
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

    public double getEstimatedTime() {
        return estimatedTime;
    }

    public void setEstimatedTime(double estimatedTime) {
        this.estimatedTime = estimatedTime;
    }

    public double getTimeUsed() {
        return timeUsed;
    }

    public void setTimeUsed(double timeUsed) {
        this.timeUsed = timeUsed;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Task {\n" +
                "Task ID = " + taskID + "\n" +
                "Title = " + title + "\n" +
                "Description = " + description + "\n" +
                "Estimate time = " + estimatedTime + "\n" +
                "Time used = " + timeUsed + "\n" +
                "Status = " + status + "\n" +
                "Start date = " + startDate + "\n" +
                "End date = " + endDate + "\n" +
                "}"
                ;
    }
}
