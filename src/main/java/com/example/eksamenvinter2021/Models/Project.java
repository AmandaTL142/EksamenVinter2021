package com.example.eksamenvinter2021.Models;

import com.example.eksamenvinter2021.Resporsitories.LinkTabelRepo;

import java.util.ArrayList;

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
    private int projectId;

    LinkTabelRepo ltr = new LinkTabelRepo();

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


    public Project(int projectId, String title, String status, int customerId, String startDate, String endDate) {
        this.projectId = projectId;
        this.projectTitle = title;
        this.status = status;
        this.customerId = customerId;
        this.startDate = startDate;
        this.endDate = endDate;
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
                ", startDate=" + startDate + '\'' +
                ", endDate=" + endDate +
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

    public ArrayList<String> getEmployeesNamesFromProject(){
        ArrayList<Employee> employeeList = ltr.getEmployeesFromProject(projectId);
        ArrayList<String> employeeNameList = new ArrayList<>();

        employeeList.forEach((Employee) -> {
            employeeNameList.add(Employee.getEmployeeName());
        });

        employeeList.forEach((Employee) -> {
            employeeNameList.add(Employee.getEmployeeName());
        });


        return employeeNameList;
    }
}