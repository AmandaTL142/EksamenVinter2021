package com.example.eksamenvinter2021.Models;

import com.example.eksamenvinter2021.Resporsitories.TaskRepo;

import java.util.ArrayList;

public class Subproject {
    //Amanda Tolstrup Laursen
    private int subprojectId;
    private String subprojectTitle;
    private String subprojectDescription;
    private String subprojectDeadline;
    private String subprojectStatus;
    private int projectId;
    private String startDate;
    private String endDate;

    TaskRepo tr = new TaskRepo();

    //Herunder er getters og setters for subprojects attributter
    public int getSubprojectId() {
        return subprojectId;
    }

    public void setSubprojectId(int subprojectId) {
        this.subprojectId = subprojectId;
    }

    public String getSubprojectTitle() {
        return subprojectTitle;
    }

    public void setSubprojectTitle(String subprojectTitle) {
        this.subprojectTitle = subprojectTitle;
    }

    public String getSubprojectDescription() {
        return subprojectDescription;
    }

    public void setSubprojectDescription(String subprojectDescription) {
        this.subprojectDescription = subprojectDescription;
    }

    public String getSubprojectDeadline() {
        return subprojectDeadline;
    }

    public void setSubprojectDeadline(String subprojectDeadline) {
        this.subprojectDeadline = subprojectDeadline;
    }

    public String getSubprojectStatus() {
        return subprojectStatus;
    }

    public void setSubprojectStatus(String subprojectStatus) {
        this.subprojectStatus = subprojectStatus;
    }

    public int getProjectId() {
        return projectId;
    }

    public void setProjectId(int projectId) {
        this.projectId = projectId;
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

    //Herunder er to controllers. Controlleren er s??ledes overloadet.
    public Subproject() {
    }

    public Subproject(String title, String deadline, String status, int projectId) {
        this.subprojectTitle = title;
        this.subprojectStatus = status;
        this.subprojectDeadline = deadline;
        this.projectId = projectId;
    }

    @Override
    public String toString() {
        return "Subproject{" +
                "subprojectId=" + subprojectId +
                ", subprojectTitle='" + subprojectTitle + '\'' +
                ", subprojectDescription='" + subprojectDescription + '\'' +
                ", subprojectDeadline='" + subprojectDeadline + '\'' +
                ", subprojectStatus='" + subprojectStatus + '\'' +
                ", projectId=" + projectId +
                '}';
    }

    //equals-metoden overrides, s?? vi kan teste getSubprojectObject i en unit-test
    @Override
    public boolean equals(Object other) {
        if (!(other instanceof Subproject)) {
            return false;
        } else {
            Subproject otherSubproject = (Subproject) other;
            boolean theSameId = otherSubproject.getSubprojectId() == this.getSubprojectId();
            boolean theSameProjectId = otherSubproject.getProjectId() == this.getProjectId();
            boolean theSameTitle = otherSubproject.getSubprojectTitle().equals(this.getSubprojectTitle());
            boolean theSameDescription = otherSubproject.getSubprojectDescription().equals(this.getSubprojectDescription());

            return theSameId && theSameTitle && theSameDescription && theSameProjectId;
        }
    }

    //Nedenst??ende metoder er tilknyttet Subproject, s?? de kan kaldes i HTML
    public ArrayList<Task> getTasksLinkedToSubproject() {
        return tr.getTasksLinkedToSubproject(subprojectId);
    }

}
