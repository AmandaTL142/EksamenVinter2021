package com.example.eksamenvinter2021.Models;

public class Employee {
    private int employeeId;
    private String employeeName;
    private String competence;

    public String getEmployeeName(){
        return employeeName;
    }

    public void setEmployeeName(String employeeName){
        this.employeeName=employeeName;
    }

    public String getCompetence(){
        return competence;
    }

    public void setCompetence(String competence){
        this.competence=competence;
    }

    public int getEmployeeId(){
        return employeeId;
    }

    public Employee(String employeeName, String competence){
        this.employeeName = employeeName;
        this.competence = competence;
    }

    public Employee(){}
}
