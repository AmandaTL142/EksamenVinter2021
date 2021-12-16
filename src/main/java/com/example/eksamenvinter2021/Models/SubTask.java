package com.example.eksamenvinter2021.Models;

public class SubTask {

    private int subtaskId;
    private String subtaskTitle;
    private String subtaskDescription;
    private String subtaskEstimatedTime;
    private String subtaskTimeUsed;
    private String subtaskStatus;
    private int subtaskProjectId;
    private int subtaskTaskId;
    private String subtaskStartDate;
    private String subtaskEndDate;


    public SubTask() {
    }

    public SubTask(int subtaskId, String subtaskTitle,
                   String subtaskDescription, String subtaskEstimatedTime,
                   String subtaskTimeUsed, String subtaskStatus, int subtaskProjectId,
                   int taskID, String subtaskStartDate, String endDate) {
        this.subtaskId = subtaskId;
        this.subtaskTitle = subtaskTitle;
        this.subtaskDescription = subtaskDescription;
        this.subtaskEstimatedTime = subtaskEstimatedTime;
        this.subtaskTimeUsed = subtaskTimeUsed;
        this.subtaskStatus = subtaskStatus;
        this.subtaskProjectId = subtaskProjectId;
        this.subtaskTaskId = taskID;
        this.subtaskStartDate = subtaskStartDate;
        this.subtaskEndDate = endDate;
    }

    public SubTask(String subtaskTitle, String subtaskDescription, String subtaskEstimatedTime, String subtaskTimeUsed, String subtaskStatus, int subtaskProjectId, int taskID, String subtaskStartDate, String endDate) {
    }

    public SubTask(String subtaskTitle, String subtaskDescription, String subtaskEstimatedTime, String subtaskTimeUsed, String subtaskStatus, String subtaskStartDate, String endDate) {

        this.subtaskTitle = subtaskTitle;
        this.subtaskDescription = subtaskDescription;
        this.subtaskEstimatedTime = subtaskEstimatedTime;
        this.subtaskTimeUsed = subtaskTimeUsed;
        this.subtaskStatus = subtaskStatus;
        this.subtaskStartDate = subtaskStartDate;
        this.subtaskEndDate = endDate;

    }

    public int getSubtaskId() {
        return subtaskId;
    }

    public void setSubtaskId(int subtaskId) {
        this.subtaskId = subtaskId;
    }

    public String getSubtaskTitle() {
        return subtaskTitle;
    }

    public void setSubtaskTitle(String subtaskTitle) {
        this.subtaskTitle = subtaskTitle;
    }

    public String getSubtaskDescription() {
        return subtaskDescription;
    }

    public void setSubtaskDescription(String subtaskDescription) {
        this.subtaskDescription = subtaskDescription;
    }

    public String getSubtaskEstimatedTime() {
        return subtaskEstimatedTime;
    }

    public void setSubtaskEstimatedTime(String subtaskEstimatedTime) {
        this.subtaskEstimatedTime = subtaskEstimatedTime;
    }

    public String getSubtaskTimeUsed() {
        return subtaskTimeUsed;
    }

    public void setSubtaskTimeUsed(String subtaskTimeUsed) {
        this.subtaskTimeUsed = subtaskTimeUsed;
    }

    public String getSubtaskStatus() {
        return subtaskStatus;
    }

    public void setSubtaskStatus(String subtaskStatus) {
        this.subtaskStatus = subtaskStatus;
    }

    public int getSubtaskProjectId() {
        return subtaskProjectId;
    }

    public void setSubtaskProjectId(int subtaskProjectId) {
        this.subtaskProjectId = subtaskProjectId;
    }

    public int getTaskId() {
        return subtaskTaskId;
    }

    public void setTaskId(int taskId) {
        this.subtaskTaskId = taskId;
    }

    public String getSubtaskStartDate() {
        return subtaskStartDate;
    }

    public void setSubtaskStartDate(String subtaskStartDate) {
        this.subtaskStartDate = subtaskStartDate;
    }

    public String getSubtaskEndDate() {
        return subtaskEndDate;
    }

    public void setSubtaskEndDate(String subtaskEndDate) {
        this.subtaskEndDate = subtaskEndDate;
    }
}