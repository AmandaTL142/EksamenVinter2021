package com.example.eksamenvinter2021.Resporsitories;

import com.example.eksamenvinter2021.Models.*;
import com.example.eksamenvinter2021.Services.EmployeeService;
import com.example.eksamenvinter2021.Services.ProjectService;
import com.example.eksamenvinter2021.Services.SubprojectService;
import com.example.eksamenvinter2021.Services.TaskService;
import com.example.eksamenvinter2021.Utility.ConnectionManager;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class LinkTableRepo {
    //Amanda Tolstrup Laursen

    ProjectService ps = new ProjectService();
    EmployeeService es = new EmployeeService();
    SubprojectService sps = new SubprojectService();

    //Denne metode henter alle datasæt fra DB, der har et bestemt employee_id. Herfra ekstraheres project_ids,
    // de tilhørende project-objects oprettes, og de færdige frasorteres. Outputtet er et array af project-objects.
    public ArrayList<Project> getActiveProjectsConnectedToEmployee(int employeeId) {
        ArrayList<Integer> projectIds = new ArrayList<>();
        ArrayList<Project> projectObjects = new ArrayList<>();
        ArrayList<Project> activeProjectObjects = new ArrayList<>();
        try {
            PreparedStatement stmt = ConnectionManager.getConnection().prepareStatement("SELECT * FROM " +
                    "heroku_7aba49c42d6c0f0.link_table WHERE employee_id=?;");
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

    //Denne metode henter alle datasæt fra DB, der har et bestemt employee_id. Herfra ekstraheres project_ids,
    // de tilhørende project-objects oprettes, og de ikke-færdige frasorteres. Outputtet er et array af project-objects.
    public ArrayList<Project> getCompletedProjectsConnectedToEmployee(int employeeId) {
        ArrayList<Integer> projectIds = new ArrayList<>();
        ArrayList<Project> projectObjects = new ArrayList<>();
        ArrayList<Project> completedProjectObjects = new ArrayList<>();
        try {
            PreparedStatement stmt = ConnectionManager.getConnection().prepareStatement("SELECT * FROM " +
                    "heroku_7aba49c42d6c0f0.link_table WHERE employee_id=?;");
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


    //Denne metode henter alle datasæt fra DB, der både har et bestemt project_id og employee_id.
    // Herfra ekstraheres subproject_ids, de tilhørende subproject-objects oprettes, og outputtet er
    // et array af subproject-objects.
    public ArrayList<Subproject> getSubprojectsConnectedToProjectsAndEmployee(int projectId, int employeeId) {
        ArrayList<Subproject> subProjects = new ArrayList<>();
        try {
            PreparedStatement stmt = ConnectionManager.getConnection().prepareStatement("SELECT subproject_id " +
                    "FROM link_table WHERE project_id = ? AND employee_id = ?;");
            stmt.setInt(1, projectId);
            stmt.setInt(2, employeeId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                int subprojectId = rs.getInt("subproject_id");
                Subproject temp = sps.getSubprojectObject(subprojectId);
                subProjects.add(temp);
            }
        } catch (Exception e) {
            System.out.println("Could not get subprojects for employee Id" + employeeId +
                    " and project Id " + projectId);
            System.out.println(e.getMessage());
        }
        return subProjects;
    }

    //Denne metode henter alle datasæt fra DB, der har et bestemt project_id. Herfra ekstraheres employee_ids,
    // eventuelle gentagelser fjernes (hvert subproject, task og subtask har tilknyttet et project_id), og
    // employee-objekter oprettes. Outputtet er et array af employee-objects.
    public ArrayList<Employee> getEmployeesFromProject(int projectId) {
        ArrayList<Integer> employeeIds = new ArrayList<>();
        ArrayList<Employee> employeeObjects = new ArrayList<>();

        try {
            PreparedStatement stmt = ConnectionManager.getConnection().prepareStatement("SELECT * FROM " +
                    "heroku_7aba49c42d6c0f0.link_table WHERE project_id=?;");
            stmt.setInt(1, projectId);
            ResultSet rs = stmt.executeQuery();
            while(rs.next()){
                int employeeId = rs.getInt("employee_id");
                employeeIds.add(employeeId);
            }

            //Forhindrer gentagelser
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

    //Denne metode henter alle datasæt fra DB, der har et bestemt project_id. Herfra ekstraheres employee_ids,
    // eventuelle gentagelser fjernes (hvert subproject, task og subtask har tilknyttet et project_id), og
    // employee-objekter oprettes. Employees, der ikke er managers frasorteres. Outputtet er et array af
    // employee-objects.
    public ArrayList<Employee> getManagersFromProject(int projectId) {
        ArrayList<Integer> employeeIds = new ArrayList<>();
        ArrayList<Employee> managerObjects = new ArrayList<>();
        try {
            PreparedStatement stmt = ConnectionManager.getConnection().prepareStatement("SELECT * FROM " +
                    "heroku_7aba49c42d6c0f0.link_table WHERE project_id=?;");
            stmt.setInt(1, projectId);
            ResultSet rs = stmt.executeQuery();
            while(rs.next()){
                int employeeId = rs.getInt("employee_id");
                employeeIds.add(employeeId);
            }

            //Forhindrer gentagelser
            Set<Integer> projectIdsHashset = new HashSet<>(employeeIds);
            employeeIds.clear();
            employeeIds.addAll(projectIdsHashset);

            employeeIds.forEach((employeeId) -> {
                if (es.showEmployee(employeeId).getRole().equalsIgnoreCase("MANAGER")){
                    managerObjects.add(es.showEmployee(employeeId));
                }
            });

        } catch(Exception e){
            System.out.println("Couldn't get employees for project with id " + projectId + " from database");
            System.out.println(e.getMessage());
        }
        return managerObjects;
    }


    //Denne metode henter alle datasæt fra DB, der har et bestemt subproject_id. Herfra ekstraheres employee_ids,
    // eventuelle gentagelser fjernes, og employee-objekter oprettes. Outputtet er et array af employee-objects.
    public ArrayList<Employee> getEmployeesFromSubproject(int subprojectId) {
        ArrayList<Integer> employeeIds = new ArrayList<>();
        ArrayList<Employee> employeeObjects = new ArrayList<>();
        try {
            PreparedStatement stmt = ConnectionManager.getConnection().prepareStatement("SELECT * FROM " +
                    "heroku_7aba49c42d6c0f0.link_table WHERE subproject_id=?;");
            stmt.setInt(1, subprojectId);
            ResultSet rs = stmt.executeQuery();
            while(rs.next()){
                int employeeId = rs.getInt("employee_id");
                employeeIds.add(employeeId);
            }

            //Forhindrer gentagelser
            Set<Integer> subprojectIdsHashset = new HashSet<>(employeeIds);
            employeeIds.clear();
            employeeIds.addAll(subprojectIdsHashset);

            employeeIds.forEach((employeeId) -> {
                employeeObjects.add(es.showEmployee(employeeId));
            });

        } catch(Exception e){
            System.out.println("Couldn't get employees for subproject with id " + subprojectId + " from database");
            System.out.println(e.getMessage());
        }
        return employeeObjects;
    }


    //Denne metode indsætter et datasæt i link_table, der har et employee_id og et project_id. Således forbindes
    // de to.
    public void insertLinkTableWithEmployeeAndProjectIntoDatabase(int employeeId, int projectId) {
        try {
            PreparedStatement stmt = ConnectionManager.getConnection().prepareStatement
                    ("INSERT INTO `heroku_7aba49c42d6c0f0`.`link_table` (`employee_id`, `project_id`) " +
                            "VALUES (?, ?);");
            stmt.setInt(1, employeeId);
            stmt.setInt(2, projectId);

            stmt.executeUpdate();
        } catch (Exception e) {
            System.out.println("Information could not be inserted into database");
            System.out.println(e.getMessage());
        }
    }


    //Denne metode indsætter et datasæt i link_table, der har et employee_id og et subproject_id. Således forbindes
    // de to.
    public void insertLinkTableWithEmployeeAndSubprojectIntoDatabase(int employeeId, int subprojectId, int projectId) {
        try {
            PreparedStatement stmt = ConnectionManager.getConnection().prepareStatement
                    ("INSERT INTO `heroku_7aba49c42d6c0f0`.`link_table` (`employee_id`, `subproject_id`, `project_Id`) " +
                            "VALUES (?, ?, ?);");
            stmt.setInt(1, employeeId);
            stmt.setInt(2, subprojectId);
            stmt.setInt(3, projectId);


            stmt.executeUpdate();
        } catch (Exception e) {
            System.out.println("Information could not be inserted into database");
            System.out.println(e.getMessage());
        }
    }

    //Denne metode sletter de datasæt fra link_table, hvor både employee_id og project_id har bestemte værdier.
    // Således fjernes forbindelsen mellem de to, også for alle de subprojects, tasks og subtasks, der måtte
    // være forbundet til projektet.
    public void removeEmployeeFromProject(int employeeId, int projectId) {
        try {
            PreparedStatement stmt = ConnectionManager.getConnection().prepareStatement
                    ("DELETE FROM heroku_7aba49c42d6c0f0.link_table WHERE employee_id=? AND project_id=?;");
            stmt.setInt(1, employeeId);
            stmt.setInt(2, projectId);

            stmt.executeUpdate();
        } catch (Exception e) {
            System.out.println("Employee could not be removed from project in database");
            System.out.println(e.getMessage());
        }
    }

    //Denne metode sletter de datasæt fra link_table, hvor både employee_id og subproject_id har bestemte værdier.
    // Således fjernes forbindelsen mellem de to.
    public void removeEmployeeFromSubproject(int employeeId, int subprojectId) {
        try {
            PreparedStatement stmt = ConnectionManager.getConnection().prepareStatement
                    ("DELETE FROM heroku_7aba49c42d6c0f0.link_table WHERE employee_id=? AND subproject_id=?;");
            stmt.setInt(1, employeeId);
            stmt.setInt(2, subprojectId);

            stmt.executeUpdate();
        } catch (Exception e) {
            System.out.println("Employee could not be removed from subproject in database");
            System.out.println(e.getMessage());
        }
    }
}
