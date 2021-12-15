package com.example.eksamenvinter2021.Services;

import com.example.eksamenvinter2021.Models.Employee;
import com.example.eksamenvinter2021.Models.Project;
import com.example.eksamenvinter2021.Models.Subproject;
import com.example.eksamenvinter2021.Resporsitories.LinkTableRepo;
import com.example.eksamenvinter2021.Utility.JDBC;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class LinkTabelService {

    ProjectService ps = new ProjectService();
    EmployeeService es = new EmployeeService();
    SubprojectService sps = new SubprojectService();
    TaskService ts = new TaskService();

    LinkTableRepo ltr = new LinkTableRepo();

    public ArrayList<Project> getActiveProjectsConnectedToEmployee(int employeeId) {
        return ltr.getActiveProjectsConnectedToEmployee(employeeId);
    }


    public ArrayList<Project> getCompletedProjectsConnectedToEmployee(int employeeId) {
        return ltr.getCompletedProjectsConnectedToEmployee(employeeId);
    }


    public ArrayList<Subproject> getSubprojectsConnectedToProjectsAndEmployee(int projectId, int employeeId) {
        return ltr.getSubprojectsConnectedToProjectsAndEmployee(projectId, employeeId);
    }

    public ArrayList<Employee> getEmployeesFromProject(int projectId) {
        return ltr.getEmployeesFromProject(projectId);
    }

    public ArrayList<Employee> getManagersFromProject(int projectId) {
        return ltr.getManagersFromProject(projectId);
    }


    public ArrayList<Employee> getEmployeesFromSubproject(int subprojectId) {
        return ltr.getEmployeesFromSubproject(subprojectId);
    }


    public void insertLinkTableWithEmployeeAndProjectIntoDatabase(int employeeId, int projectId) {
        ltr.insertLinkTableWithEmployeeAndProjectIntoDatabase(employeeId, projectId);
    }

    public void insertLinkTableWithEmployeeAndSubprojectIntoDatabase(int employeeId, int subprojectId, int projectId) {
       ltr.insertLinkTableWithEmployeeAndSubprojectIntoDatabase(employeeId, subprojectId, projectId);
    }

    public void removeEmployeeFromProject(int employeeId, int projectId) {
        ltr.removeEmployeeFromProject(employeeId, projectId);
    }

    public void removeEmployeeFromSubproject(int employeeId, int subprojectId) {
       ltr.removeEmployeeFromSubproject(employeeId, subprojectId);
    }


}
