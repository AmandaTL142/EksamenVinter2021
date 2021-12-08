package com.example.eksamenvinter2021.Models;

import com.example.eksamenvinter2021.Resporsitories.LinkTabelRepo;

import java.util.ArrayList;

public class Employee {
    private int employeeId;
    private String employeeName;
    private String password;
    private String competence;
    private String role;
    LinkTabelRepo ltr = new LinkTabelRepo();

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

    @Override
    public String toString() {
        return "Employee{" +
                "employeeId=" + employeeId +
                ", employeeName='" + employeeName + '\'' +
                ", password='" + password + '\'' +
                ", competence='" + competence + '\'' +
                ", role='" + role + '\'' +
                '}';
    }

    public void setEmployeeId(int employeeId) {
        this.employeeId = employeeId;
    }

    //Denne metode skal bruges til at hente projects fra session direkte i html.
    public ArrayList<Project> getProjectArray() {
        return ltr.getActiveProjectsConnectedToEmployee(employeeId);
    }
}
