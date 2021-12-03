package com.example.eksamenvinter2021.Models;

import java.util.Date;

public class Project {
    private String projectTitle;
    private String projectDeadline;
    private String status;
    private double basePrice;
    private double totalPrice;
    private int totalTime;
    private int customerId;
    private String startDate;
    private String endDate;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    private String description;

    public int getProjectId() {
        return projectId;
    }

    public void setProjectId(int projectId) {
        this.projectId = projectId;
    }

    private int projectId;


    public String getProjectTitle() {
        return projectTitle;
    }

    public void setProjectTitle(String projectTitle) {
        this.projectTitle = projectTitle;
    }

    public String getProjectDeadline() {
        return projectDeadline;
    }

    public void setProjectDeadline(String projectDeadline) {
        this.projectDeadline = projectDeadline;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public double getBasePrice() {
        return basePrice;
    }

    public void setBasePrice(double basePrice) {
        this.basePrice = basePrice;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }


    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalTime(int totalTime) {
        this.totalTime = totalTime;
    }

    public int getTotalTime() {
        return totalTime;
    }

    public Project(String title, String projectDeadline, String status, double basePrice, int customerId) {
    this.projectTitle = title;
    this.projectDeadline = projectDeadline;
    this.status = status;
    this.basePrice = basePrice;
    this.customerId = customerId;
    }

    public Project() {
    }

    @Override
    public String toString() {
        return "Project{" +
                "projectTitle='" + projectTitle + '\'' +
                ", projectDeadline=" + projectDeadline +
                ", status='" + status + '\'' +
                ", basePrice=" + basePrice +
                ", totalPrice=" + totalPrice +
                ", totalTime=" + totalTime +
                ", customerId=" + customerId +
                ", description='" + description + '\'' +
                ", projectId=" + projectId +
                '}';
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
