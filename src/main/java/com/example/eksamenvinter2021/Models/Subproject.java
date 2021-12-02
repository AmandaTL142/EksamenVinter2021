package com.example.eksamenvinter2021.Models;

import java.util.Date;

public class Subproject {
    private int subprojectId;
    private String subprojectTitle;
    private String subprojectDescription;
    private String subprojectDeadline;
    private String subprojectStatus;
    private int projectId;

    public Subproject() {
    }


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

    public Subproject(String title, String deadline, String status, int projectId) {
        this.subprojectTitle = title;
        this.subprojectStatus = status;
        this.subprojectDeadline = deadline;
        this.projectId = projectId;
    }
}
