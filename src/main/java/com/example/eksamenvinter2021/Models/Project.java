package com.example.eksamenvinter2021.Models;

import com.example.eksamenvinter2021.Resporsitories.LinkTableRepo;
import com.example.eksamenvinter2021.Resporsitories.ProjectRepo;
import com.example.eksamenvinter2021.Resporsitories.SubprojectRepo;
import com.example.eksamenvinter2021.Resporsitories.TaskRepo;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Project {
    //Amanda Tolstrup Laursen
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
    private List<Subproject> associatedSubprojects;

    LinkTableRepo ltr = new LinkTableRepo();
    SubprojectRepo spr = new SubprojectRepo();
    ProjectRepo pr = new ProjectRepo();
    TaskRepo tr = new TaskRepo();

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

    public List<Subproject> getAssociatedSubprojects()
    {
        return associatedSubprojects;
    }

    public void setAssociatedSubprojects(List<Subproject> associatedSubprojects)
    {
        this.associatedSubprojects = associatedSubprojects;
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
        this.associatedSubprojects = new ArrayList<>();
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
                ", endDate=" + endDate + '\'' +
                ", associatedSubprojects=" + associatedSubprojects +
                '}';
    }

    public String getStartDate() {
        return startDate;
    }

    public Date getDateinDateFormat(String dateInput) throws ParseException {
        Date returnDate=new SimpleDateFormat("yyyy-MM-dd").parse(dateInput);
        return returnDate;
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

    @Override
    public boolean equals(Object other) {
        if (!(other instanceof Project)) {
            return false;
        } else {
            Project otherProject = (Project) other;
            boolean theSameId = otherProject.getProjectId() == this.getProjectId();
            boolean theSameTitle = otherProject.getProjectTitle().equals(this.getProjectTitle());
            boolean theSameDescription = otherProject.getDescription().equals(this.getDescription());

            if (theSameId == true && theSameTitle == true && theSameDescription == true){
                return true;
            } else {
                return false;
            }
        }
    }

    public ArrayList<Subproject> getSubprojectsLinkedToProject() {
        return spr.getSubprojectsLinkedToProject(projectId);
    }

    public boolean doesProjectHaveSubprojects() {
        return pr.doesProjectHaveSubprojects(projectId);
    }


    public ArrayList<Task> getAllTasksInProject() {
        return tr.getAllTasksInProject(projectId);
    }


}
