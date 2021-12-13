package com.example.eksamenvinter2021.Models;

public class SubTask {

    private int subtaskID;
    private String title;
    private String description;
    private String estimatedTime;
    private String timeUsed;
    private String status;
    private int projectID;
    private int taskID;
    private String startDate;
    private String endDate;


    public SubTask() {
    }

    public SubTask(int subtaskID, String title,
                   String description, String estimatedTime,
                   String timeUsed, String status, int projectID,
                   int taskID, String startDate, String endDate) {
        this.subtaskID = subtaskID;
        this.title = title;
        this.description = description;
        this.estimatedTime = estimatedTime;
        this.timeUsed = timeUsed;
        this.status = status;
        this.projectID = projectID;
        this.taskID = taskID;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public SubTask(String title, String description, String estimatedTime, String timeUsed, String status, int projectID, int taskID, String startDate, String endDate) {
    }

    public SubTask(String title, String description, String estimatedTime, String timeUsed, String status, String startDate, String endDate) {
    }

    public int getSubtaskID() {
        return subtaskID;
    }

    public void setSubtaskID(int subtaskID) {
        this.subtaskID = subtaskID;
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

    public int getProjectID() {
        return projectID;
    }

    public void setProjectID(int projectID) {
        this.projectID = projectID;
    }

    public int getTaskID() {
        return taskID;
    }

    public void setTaskID(int taskID) {
        this.taskID = taskID;
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
}