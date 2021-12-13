package com.example.eksamenvinter2021.Resporsitories;

import com.example.eksamenvinter2021.Models.*;
import com.example.eksamenvinter2021.Services.EmployeeService;
import com.example.eksamenvinter2021.Services.ProjectService;
import com.example.eksamenvinter2021.Services.SubprojectService;
import com.example.eksamenvinter2021.Services.TaskService;
import com.example.eksamenvinter2021.Utility.JDBC;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class LinkTabelRepo {

    ProjectService ps = new ProjectService();
    EmployeeService es = new EmployeeService();
    SubprojectService sps = new SubprojectService();
    TaskService ts = new TaskService();

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
                //FIXME: Couldn't get project with id 0 from database
                //Illegal operation on empty result set.
                //Couldn't get projects for employee with id 15 from database
                //Cannot invoke "String.equalsIgnoreCase(String)" because "status" is null
                //TODO: create method 'changeStatus' to set a project's status to ongoing
                /*if (status == null) {
                    System.out.println("Project status should not be null.");
                    projectObject.changeStatus("ongoing");
                    activeProjectObjects.add(projectObject);
                }*/
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


    public ArrayList<Subproject> getSubprojectsConnectedToProjectsAndEmployee(int projectId, int employeeId) {
        ArrayList<Subproject> subProjects = new ArrayList<>();
        try {
            PreparedStatement stmt = JDBC.getConnection().prepareStatement("SELECT subproject_id FROM " +
                    "link_tabel WHERE project_id = ? AND employee_id = ?;");
            stmt.setInt(1, projectId);
            stmt.setInt(2, employeeId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                int subprojectId = rs.getInt("subproject_id");
                Subproject temp = sps.getSubprojectObject(subprojectId);
                subProjects.add(temp);
            }
        } catch (Exception e) {
            System.out.println("Could not get subprojects for employee ID" + employeeId +
                    " and project ID " + projectId);
            System.out.println(e.getMessage());
        }
        return subProjects;
    }
    /*
    public ArrayList<Task> getTasksConnectedToSubProjectsAndEmployee(int subprojectId, int employeeId) {
        ArrayList<Task> tasks = new ArrayList<>();
        try {
            PreparedStatement stmt = JDBC.getConnection().prepareStatement("SELECT task_id FROM " +
                    "link_tabel WHERE subproject_id = ? AND employee_id = ?;");
            stmt.setInt(1, subprojectId);
            stmt.setInt(2, employeeId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                int taskId = rs.getInt("task_id");
                Task temp = ts.getTaskObject(taskId);
                tasks.add(temp);
            }
        } catch (Exception e) {
            System.out.println("Could not get tasks for employee ID" + employeeId +
                    " and subproject ID " + subprojectId);
            System.out.println(e.getMessage());
        }
        return tasks;
    }

    public ArrayList<SubTask> getSubtasksConnectedToTasksAndEmployee(int taskId, int employeeId) {
        ArrayList<SubTask> subTasks = new ArrayList<>();
        try {
            PreparedStatement stmt = JDBC.getConnection().prepareStatement("SELECT subtask_id FROM " +
                    "link_tabel WHERE task_id = ? AND employee_id = ?;");
            stmt.setInt(1, taskId);
            stmt.setInt(2, employeeId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                int subTaskId = rs.getInt("subproject_id");
                SubTask temp = sps.getSubTaskObject(subTaskId);
                subTasks.add(temp);
            }
        } catch (Exception e) {
            System.out.println("Could not get subprojects for employee ID" + employeeId +
                    " and project ID " + taskId);
            System.out.println(e.getMessage());
        }
        return subTasks;
    }*/

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

    public ArrayList<Employee> getManagersFromProject(int projectId) {
        ArrayList<Integer> employeeIds = new ArrayList<>();
        ArrayList<Employee> managerObjects = new ArrayList<>();
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


    public ArrayList<Employee> getEmployeesFromSubproject(int subprojectId) {
        ArrayList<Integer> employeeIds = new ArrayList<>();
        ArrayList<Employee> employeeObjects = new ArrayList<>();
        try {
            PreparedStatement stmt = JDBC.getConnection().prepareStatement("SELECT * FROM " +
                    "heroku_7aba49c42d6c0f0.link_tabel WHERE subproject_id=?;");
            stmt.setInt(1, subprojectId);
            ResultSet rs = stmt.executeQuery();
            while(rs.next()){
                int employeeId = rs.getInt("employee_id");
                employeeIds.add(employeeId);
            }

            //Prevents doubles
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

    public void insertLinkTabelWithEmployeeAndSubprojectIntoDatabase(int employeeId, int subprojectId, int projectId) {
        try {
            PreparedStatement stmt = JDBC.getConnection().prepareStatement
                    ("INSERT INTO `heroku_7aba49c42d6c0f0`.`link_tabel` (`employee_id`, `subproject_id`, `project_Id`) " +
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

    public void removeEmployeeFromSubproject(int employeeId, int subprojectId) {
        try {
            PreparedStatement stmt = JDBC.getConnection().prepareStatement
                    ("DELETE FROM heroku_7aba49c42d6c0f0.link_tabel WHERE employee_id=? AND subproject_id=?;");
            stmt.setInt(1, employeeId);
            stmt.setInt(2, subprojectId);

            stmt.executeUpdate();
        } catch (Exception e) {
            System.out.println("Employee could not be removed from project in database");
            System.out.println(e.getMessage());
        }
    }
}
