package com.example.eksamenvinter2021.Models;

public class Employee {
    private int employeeId;
    private String employeeName;
    private String password;
    private String competence;
    private String role;

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

    public String getRole(){
        return role;
    }

    public void setRole(String role){
        this.role = role;
    }

    public String getPassword(){
        return password;
    }

    public void setPassword(String password){
        this.password = password;
    }

    public Employee(String employeeName, String password,String competence, String role){
        this.employeeName = employeeName;
        this.password = password;
        this.competence = competence;
        this.role = role;
    }

    public Employee(){}
}
