package com.example.eksamenvinter2021.Models;

import com.example.eksamenvinter2021.Resporsitories.SubTaskRepo;

import java.util.ArrayList;

//Hele klassen lavet af Andrea
public class Task {
    private int taskId;
    private String taskTitle;
    private String taskDescription;
    private String taskEstimatedTime;
    private String taskTimeUsed;
    private String taskStatus;
    private int taskProjectId;
    private int taskSubprojectId;
    private String taskStartDate;
    private String taskEndDate;

    SubTaskRepo str = new SubTaskRepo();


    private ArrayList<Task> tasks;


    public Task() {
    }

    public Task(String taskTitle, String taskDescription, String TaskEstimatedTime, String timeUsed, String status) {
        this.taskTitle = taskTitle;
        this.taskDescription = taskDescription;
        this.taskEstimatedTime = TaskEstimatedTime;
        this.taskTimeUsed = timeUsed;
        this.taskStatus = status;
    }

    public Task(String taskTitle, String taskDescription, String estimated_time, String timeUsed, String status, int projectID, int subprojectID, String taskStartDate, String taskEndDate) {
        this.taskTitle = taskTitle;
        this.taskDescription = taskDescription;
        this.taskEstimatedTime = estimated_time;
        this.taskTimeUsed = timeUsed;
        this.taskStatus = status;
        this.taskStartDate = taskStartDate;
        this.taskEndDate = taskEndDate;
    }

    public Task(String taskTitle, String taskDescription, String estimated_time, String timeUsed, String status, String taskStartDate, String taskEndDate) {
        this.taskTitle = taskTitle;
        this.taskDescription = taskDescription;
        this.taskEstimatedTime = estimated_time;
        this.taskTimeUsed = timeUsed;
        this.taskStatus = status;
        this.taskStartDate = taskStartDate;
        this.taskEndDate = taskEndDate;
    }

    public Task(int task_id, String taskTitle, String taskDescription, String estimated_time, String time_used, String status, int project_id, int subproject_id, String start_date, String end_date) {
    }

    public Task(int taskId, String taskTitle, String taskDescription, String TaskEstimatedTime, String timeUsed, String status, String taskStartDate, String taskEndDate) {
    }

    public String getTaskTitle() {
        return taskTitle;
    }

    public void setTaskTitle(String taskTitle) {
        this.taskTitle = taskTitle;
    }

    public String getTaskDescription() {
        return taskDescription;
    }

    public void setTaskDescription(String taskDescription) {
        this.taskDescription = taskDescription;
    }

    public String getTaskEstimatedTime() {
        return taskEstimatedTime;
    }

    public void setTaskEstimatedTime(String taskEstimatedTime) {
        this.taskEstimatedTime = taskEstimatedTime;
    }

    public String getTaskTimeUsed() {
        return taskTimeUsed;
    }

    public void setTaskTimeUsed(String taskTimeUsed) {
        this.taskTimeUsed = taskTimeUsed;
    }

    public String getTaskStatus() {
        return taskStatus;
    }

    public void setTaskStatus(String taskStatus) {
        this.taskStatus = taskStatus;
    }

    public ArrayList<Task> getTasks() {
        return tasks;
    }

    public int getTaskId() {
        return taskId;
    }

    public void setTaskId(int taskId) {
        this.taskId = taskId;
    }

    public int getTaskProjectId() {
        return taskProjectId;
    }

    public void setTaskProjectId(int taskProjectId) {
        this.taskProjectId = taskProjectId;
    }

    public int getTaskSubprojectId() {
        return taskSubprojectId;
    }

    public void setTaskSubprojectId(int taskSubprojectId) {
        this.taskSubprojectId = taskSubprojectId;
    }

    public String getTaskStartDate() {
        return taskStartDate;
    }

    public void setTaskStartDate(String taskStartDate) {
        this.taskStartDate = taskStartDate;
    }

    public String getTaskEndDate() {
        return taskEndDate;
    }

    public void setTaskEndDate(String taskEndDate) {
        this.taskEndDate = taskEndDate;
    }

    public void setTasks(ArrayList<Task> tasks) {
        this.tasks = tasks;
    }

    @Override
    public String toString() {
        return "Task{" +
                "id=" + taskId +
                ", title='" + taskTitle + '\'' +
                ", description='" + taskDescription + '\'' +
                ", estimatedTime='" + taskEstimatedTime + '\'' +
                ", timeUsed='" + taskTimeUsed + '\'' +
                ", status='" + taskStatus + '\'' +
                ", projectId=" + taskProjectId +
                ", subprojectId=" + taskSubprojectId +
                ", startDate='" + taskStartDate + '\'' +
                ", endDate='" + taskEndDate + '\'' +
                ", tasks=" + tasks +
                '}';
    }

    public ArrayList<SubTask> getAllSubtaskInTask() {
        return str.getAllSubtaskInTask(taskId);
    }

}
