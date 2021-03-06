package com.example.eksamenvinter2021.Models;

import com.example.eksamenvinter2021.Resporsitories.LinkTableRepo;
import com.example.eksamenvinter2021.Resporsitories.TaskRepo;

import java.util.ArrayList;

public class Employee {
    private int employeeId;
    private String employeeName;
    private String password;
    private String competence;
    private String role;
    LinkTableRepo ltr = new LinkTableRepo();
    TaskRepo tr = new TaskRepo();

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
    public ArrayList<Project> getActiveProjectArray() {
        return ltr.getActiveProjectsConnectedToEmployee(employeeId);
    }

    public ArrayList<Project> getCompletedProjectArray() {
        return ltr.getCompletedProjectsConnectedToEmployee(employeeId);
    }

    public boolean hasCompletedProjects() {
        int noOfComProj = getCompletedProjectArray().size();
        if (noOfComProj == 0){
            return false;
        } else {
            return true;
        }
    }

    @Override
    public boolean equals(Object other) {
        if (!(other instanceof Employee)) {
            return false;
        } else {
            Employee otherEmployee = (Employee) other;
            boolean theSame = otherEmployee.getEmployeeId() == this.getEmployeeId();
            return theSame;
        }
    }


    //Bruges ikke
    public ArrayList<Task> getTaskConnectedToEmployee() {
        return tr.getTaskConnectedToEmployee(employeeId);
    }

    public ArrayList<Task> getTaskConnectedToEmployeeAndProject(int projectId) {
        return tr.getTaskConnectedToEmployeeAndProject(employeeId, projectId);
    }

}
