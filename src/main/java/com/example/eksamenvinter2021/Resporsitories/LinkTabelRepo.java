package com.example.eksamenvinter2021.Resporsitories;

import com.example.eksamenvinter2021.Models.Employee;
import com.example.eksamenvinter2021.Models.Project;
import com.example.eksamenvinter2021.Services.EmployeeService;
import com.example.eksamenvinter2021.Services.ProjectService;
import com.example.eksamenvinter2021.Utility.JDBC;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class LinkTabelRepo {

    ProjectService ps = new ProjectService();
    EmployeeService es = new EmployeeService();

    public ArrayList<Project> getActiveProjectsConnectedToEmployee(int employeeId) {
        ArrayList<Integer> projectIds = new ArrayList<>();
        ArrayList<Project> projectObjects = new ArrayList<>();
        ArrayList<Project> activeProjectObjects = new ArrayList<>();
        try {
            PreparedStatement stmt = JDBC.getConnection().prepareStatement("SELECT * FROM " +
                    "heroku_7aba49c42d6c0f0.link_tabel WHERE employee_id=?;");
            stmt.setInt(1, employeeId);
            ResultSet rs = stmt.executeQuery();
            while(rs.next()){
                int projectId = rs.getInt("project_id");
                projectIds.add(projectId);
            }

            //Prevents doubles
            Set<Integer> projectIdsHashset = new HashSet<>(projectIds);
            projectIds.clear();
            projectIds.addAll(projectIdsHashset);


            projectIds.forEach((projectId) -> {
                projectObjects.add(ps.getProjectObject(projectId));
            });

            for (int i = 0; i < projectObjects.size(); i++) {
                Project projectObject = projectObjects.get(i);
                String status = projectObject.getStatus();

                if (!status.equalsIgnoreCase("complete")){
                    activeProjectObjects.add(projectObject);
                }
            }

        } catch(Exception e){
            System.out.println("Couldn't get projects for employee with id " + employeeId + " from database");
            System.out.println(e.getMessage());
        }
        return activeProjectObjects;
    }


    public ArrayList<Project> getCompletedProjectsConnectedToEmployee(int employeeId) {
        ArrayList<Integer> projectIds = new ArrayList<>();
        ArrayList<Project> projectObjects = new ArrayList<>();
        ArrayList<Project> completedProjectObjects = new ArrayList<>();
        try {
            PreparedStatement stmt = JDBC.getConnection().prepareStatement("SELECT * FROM " +
                    "heroku_7aba49c42d6c0f0.link_tabel WHERE employee_id=?;");
            stmt.setInt(1, employeeId);
            ResultSet rs = stmt.executeQuery();
            while(rs.next()){
                int projectId = rs.getInt("project_id");
                projectIds.add(projectId);
            }

            //Prevents doubles
            Set<Integer> projectIdsHashset = new HashSet<>(projectIds);
            projectIds.clear();
            projectIds.addAll(projectIdsHashset);


            projectIds.forEach((projectId) -> {
                projectObjects.add(ps.getProjectObject(projectId));
            });

            for (int i = 0; i < projectObjects.size(); i++) {
                Project projectObject = projectObjects.get(i);
                String status = projectObject.getStatus();

                if (status.equalsIgnoreCase("complete")){
                    completedProjectObjects.add(projectObject);
                }
            }

        } catch(Exception e){
            System.out.println("Couldn't get projects for employee with id " + employeeId + " from database");
            System.out.println(e.getMessage());
        }
        return completedProjectObjects;
    }


    public ArrayList<Employee> getEmployeesFromProject(int projectId) {
        ArrayList<Integer> employeeIds = new ArrayList<>();
        ArrayList<Employee> employeeObjects = new ArrayList<>();
        try {
            PreparedStatement stmt = JDBC.getConnection().prepareStatement("SELECT * FROM " +
                    "heroku_7aba49c42d6c0f0.link_tabel WHERE project_id=?;");
            stmt.setInt(1, projectId);
            ResultSet rs = stmt.executeQuery();
            while(rs.next()){
                int employeeId = rs.getInt("employee_id");
                employeeIds.add(employeeId);
            }

            //Prevents doubles
            Set<Integer> projectIdsHashset = new HashSet<>(employeeIds);
            employeeIds.clear();
            employeeIds.addAll(projectIdsHashset);

            employeeIds.forEach((employeeId) -> {
                employeeObjects.add(es.showEmployee(employeeId));
            });

        } catch(Exception e){
            System.out.println("Couldn't get employees for project with id " + projectId + " from database");
            System.out.println(e.getMessage());
        }
        return employeeObjects;
    }

    public void insertLinkTabelWithEmployeeAndProjectIntoDatabase(int employeeId, int projectId) {
        try {
            PreparedStatement stmt = JDBC.getConnection().prepareStatement
                    ("INSERT INTO `heroku_7aba49c42d6c0f0`.`link_tabel` (`employee_id`, `project_id`) " +
                            "VALUES (?, ?);");
            stmt.setInt(1, employeeId);
            stmt.setInt(2, projectId);

            stmt.executeUpdate();
        } catch (Exception e) {
            System.out.println("Information could not be inserted into database");
            System.out.println(e.getMessage());
        }
    }

    public void removeEmployeeFromProject(int employeeId, int projectId) {
        try {
            PreparedStatement stmt = JDBC.getConnection().prepareStatement
                    ("DELETE FROM heroku_7aba49c42d6c0f0.link_tabel WHERE employee_id=? AND project_id=?;");
            stmt.setInt(1, employeeId);
            stmt.setInt(2, projectId);

            stmt.executeUpdate();
        } catch (Exception e) {
            System.out.println("Employee could not be removed from project in database");
            System.out.println(e.getMessage());
        }
    }

}
