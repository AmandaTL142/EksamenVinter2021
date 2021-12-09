package com.example.eksamenvinter2021.Models;

import java.sql.Time;
import java.util.ArrayList;

public class Task {
    private int id;
    private String title;
    private String description;
    private String estimatedTime;
    private String timeUsed;
    private String status;
    private int projectId;
    private int subprojectId;
    private String startDate;
    private String endDate;


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

    public Task(String title, String description, String estimated_time, String timeUsed, String status, int projectID, int subprojectID, String startDate, String endDate) {
    }

    public Task(String title, String description, String estimated_time, String timeUsed, String status, String startDate, String endDate) {
    }

    public Task(int task_id, String title, String description, String estimated_time, String time_used, String status, int project_id, int subproject_id, String start_date, String end_date) {
    }

    public Task(int taskId, String title, String description, String estimatedTime, String timeUsed, String status, String startDate, String endDate) {
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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getProjectId() {
        return projectId;
    }

    public void setProjectId(int projectId) {
        this.projectId = projectId;
    }

    public int getSubprojectId() {
        return subprojectId;
    }

    public void setSubprojectId(int subprojectId) {
        this.subprojectId = subprojectId;
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

    public void setTasks(ArrayList<Task> tasks) {
        this.tasks = tasks;
    }

    @Override
    public String toString() {
        return "Task{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", estimatedTime='" + estimatedTime + '\'' +
                ", timeUsed='" + timeUsed + '\'' +
                ", status='" + status + '\'' +
                ", projectId=" + projectId +
                ", subprojectId=" + subprojectId +
                ", startDate='" + startDate + '\'' +
                ", endDate='" + endDate + '\'' +
                ", tasks=" + tasks +
                '}';
    }
}
