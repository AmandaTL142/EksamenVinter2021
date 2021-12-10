package com.example.eksamenvinter2021.Resporsitories;


import com.example.eksamenvinter2021.Models.Employee;
import com.example.eksamenvinter2021.Models.Project;
import com.example.eksamenvinter2021.Models.SubTask;
import com.example.eksamenvinter2021.Models.Task;
import com.example.eksamenvinter2021.Utility.JDBC;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class TaskRepo {

    Connection conn = JDBC.getConnection();

    public void insertNewTaskToDB(Task task){
        //int projectId= getProjectId("projectTitle");
        //int subProjectID= getSubProjectId("subProjectTitle");

        try{

            PreparedStatement stmt = conn.prepareStatement("INSERT INTO `heroku_7aba49c42d6c0f0`.`tasks` " +
                    "(`title`, `description`, `estimated_time`, `time_used`, `status`, `project_id`, `start_date`, `end_date`) VALUES (?, ?, ?, ?, ?,?,?,?)");

            stmt.setString(1,task.getTitle());
            stmt.setString(2,task.getDescription());
            stmt.setString(3,task.getEstimatedTime());
            stmt.setString(4,task.getTimeUsed());
            stmt.setString(5,task.getStatus());
            stmt.setInt(6,task.getProjectId());
            stmt.setString(7, task.getStartDate());
            stmt.setString(8,task.getEndDate());


            stmt.executeUpdate();


        } catch (SQLException e) {
            System.out.println("connection not found");
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }

    public Task getTaskFromDB(int id){
        Task t= new Task();

        try {
            PreparedStatement stmt = conn.prepareStatement("Select * FROM " +
                    "heroku_7aba49c42d6c0f0.tasks where task_id=?");
            stmt.setInt(1,id);
            ResultSet rs = stmt.executeQuery();

            rs.next();
            //int taskID = rs.getInt("task_id");
            String title = rs.getString("title");
            String description = rs.getString("description");
            String estimated_time = rs.getString("estimated_time");
            String timeUsed = rs.getString("time_used");
            String status = rs.getString("status");
            int projectID = rs.getInt("project_id");
            int subprojectID = rs.getInt("subproject_id");
            String startDate = rs.getString("start_date");
            String endDate = rs.getString("end_date");


            t = new Task(title, description,estimated_time,timeUsed,status);
            t.setId(id);
            t.setTitle(title);
            t.setDescription(description);
            t.setEstimatedTime(estimated_time);
            t.setTimeUsed(timeUsed);
            t.setStatus(status);
            t.setProjectId(projectID);
            t.setSubprojectId(subprojectID);
            t.setStartDate(startDate);
            t.setEndDate(endDate);


        } catch (SQLException e) {
            System.out.println("Couldn't get task with id " + id + " from database");
            System.out.println(e.getMessage());
        }
        return t;
    }

    public void updateTask(Task task){

        String sql = "UPDATE `heroku_7aba49c42d6c0f0`.`tasks` SET `title` =?, `description` = ?, `estimated_time` = ?, `time_used` = ?, `status` =?, `start_date` = ?, `end_date` =? WHERE (`task_id` =?);";

        try {
            PreparedStatement stmt = conn.prepareStatement(sql);

            stmt.setInt(8,task.getId());
            stmt.setString(1,task.getTitle());
            stmt.setString(2,task.getDescription());
            stmt.setString(3,task.getEstimatedTime());
            stmt.setString(4,task.getTimeUsed());
            stmt.setString(5,task.getStatus());
            stmt.setString(6,task.getStartDate());
            stmt.setString(7,task.getEndDate());


            stmt.executeUpdate();

        } catch (SQLException e) {
            System.out.println("no connection");
            System.out.println(e.getMessage());
            e.printStackTrace();
        }

    }

    public int getTaskID(String taskTitle){
        try {
            PreparedStatement stmt = JDBC.getConnection().prepareStatement("SELECT project_id FROM " +
                    "heroku_7aba49c42d6c0f0.tasks WHERE title=?;");
            stmt.setString(1,taskTitle);
            ResultSet rs = stmt.executeQuery();

            rs.next();
            int id = rs.getInt("task_id");
            return id;
        } catch (SQLException e) {
            System.out.println("Couldn't get id for with title " + taskTitle + " from database");
            System.out.println(e.getMessage());
        }
        return 0;
    }

    public ArrayList<Task> getAllTasksInProject(int pID){
        ArrayList<Task> allTasks = new ArrayList<>();
        try {

            PreparedStatement stmt = conn.prepareStatement("SELECT * FROM heroku_7aba49c42d6c0f0.tasks where project_id=?");
            stmt.setInt(1, pID);

            ResultSet rs = stmt.executeQuery();


            while(rs.next()){
                int taskID = rs.getInt("task_id");
                String title = rs.getString("title");
                String description = rs.getString("description");
                String estimated_time = rs.getString("estimated_time");
                String timeUsed = rs.getString("time_used");
                String status = rs.getString("status");
                int projectID = rs.getInt("project_id");
                int subprojectID = rs.getInt("subproject_id");
                String startDate = rs.getString("start_date");
                String endDate = rs.getString("end_date");

                Task t = new Task(title,description,estimated_time,timeUsed,status,projectID,subprojectID,startDate,endDate);

                allTasks.add(t);
            }
        } catch (SQLException e) {
            System.out.println("Couldn't get tasks from database");
            System.out.println(e.getMessage());
        }
        return allTasks;

    }

    public void deleteTask(int taskID){

        try {
            PreparedStatement stmt = conn.prepareStatement("DELETE FROM `heroku_7aba49c42d6c0f0`.`tasks` WHERE `task_id` = ?;");
            stmt.setInt(1, taskID);
            stmt.executeUpdate();

        } catch (SQLException e) {
            System.out.println("Couldn't delete task with id " + taskID + " from database");
            System.out.println(e.getMessage());
        }

    }

    /*public  ArrayList<Task> getTaskLinkedToProject(int thisProjectID){

        ArrayList<Task> allTasks = new ArrayList<>();
        Task task = new Task();
        try {
            PreparedStatement stmt = JDBC.getConnection().prepareStatement("SELECT * FROM " +
                    "heroku_7aba49c42d6c0f0.tasks WHERE project_id=?;");

            stmt.setInt(1,thisProjectID);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                System.out.println("Id: " + rs.getInt("task_id"));
                System.out.println("Title: " + rs.getString("title"));


                task.setTitle(rs.getString("title"));
                task.setDescription(rs.getString("description"));
                task.setEstimatedTime(rs.getString("estimated_time"));
                task.setTimeUsed( rs.getString("time_used"));
                task.setProjectId(rs.getInt("project_id"));
                task.setSubprojectId(rs.getInt("subproject_id"));
                task.setStartDate(rs.getString("start_date"));
                task.setEndDate( rs.getString("end_date"));

                allTasks.add(task);

                System.out.println("task: " + task);
            }


                //Task t = new Task(title,description,estimated_time,timeUsed,status,startDate,endDate);
                //System.out.println("task er: " + t);
                //t.setId(taskID);



            }
         catch (SQLException e) {
            System.out.println("Couldn't get subprojects for project with id " + thisProjectID + " from database");
            System.out.println(e.getMessage());
        }

        return allTasks;
    }*/

    public ArrayList<Task> getTasksInArray() {
        ArrayList<Task> taskArray = new ArrayList<>();
        try {
            PreparedStatement stmt = JDBC.getConnection().prepareStatement("SELECT * FROM " +
                    "heroku_7aba49c42d6c0f0.tasks;");
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {

                int taskId = rs.getInt("task_id");
                String title = rs.getString("title");
                String description = rs.getString("description");
                String estimatedTime = rs.getString("estimated_time");
                String timeUsed = rs.getString("time_used");
                String status = rs.getString("status");
                int projectID = rs.getInt("project_id");
                int subprojectID = rs.getInt("subproject_id");
                String startDate = rs.getString("start_date");
                String endDate = rs.getString("end_date");

                Task task = new Task();
                task.setId(taskId);
                task.setTitle(title);
                task.setDescription(description);
                task.setEstimatedTime(estimatedTime);
                task.setTimeUsed(timeUsed);
                task.setStatus(status);
                task.setProjectId(projectID);
                task.setSubprojectId(subprojectID);
                task.setStartDate(startDate);
                task.setEndDate(endDate);


                taskArray.add(task);
            }

        } catch (SQLException e) {
            System.out.println("Couldn't get projects from database");
            System.out.println(e.getMessage());
        }
        return taskArray;
    }

    public ArrayList<Task> getTaskConnectedToEmployee(int employeeID){
        ArrayList<Integer> taskIDs = new ArrayList<>();
        ArrayList<Task> taskObjects = new ArrayList<>();

        try {
            PreparedStatement stmt = conn.prepareStatement("SELECT * FROM " +
                    "heroku_7aba49c42d6c0f0.link_tabel WHERE employee_id=?;");
            stmt.setInt(1,employeeID);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()){
                int taskID = rs.getInt("task_id");
                taskIDs.add(taskID);
            }

            /*Set<Integer> taskIDHashSet = new HashSet<>();
            taskIDs.clear();
            taskIDs.addAll(taskIDHashSet);

            taskIDs.forEach(taskId){
            taskObject.add(ts.getTaskOBject(taskId))}*/

        } catch (SQLException e) {
            e.printStackTrace();
        }
    return taskObjects;
    }

    public  ArrayList<Employee> getEmployeeFromTask(int taskID){
        ArrayList<Integer> employeeIDs = new ArrayList<>();
        ArrayList<Employee> employeeObjects = new ArrayList<>();

        try {
            PreparedStatement stmt = JDBC.getConnection().prepareStatement("SELECT * FROM " +
                    "heroku_7aba49c42d6c0f0.link_tabel WHERE task_id=?;");
            stmt.setInt(1,taskID);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()){
                int emmployeeID = rs.getInt("employee_id");
                employeeIDs.add(emmployeeID);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return employeeObjects;
    }

    public void insertLinkTableWithEmployeeAndTaskInDB(int employeeID, int taskID){
        try {
            PreparedStatement stmt = JDBC.getConnection().prepareStatement
                    ("INSERT INTO `heroku_7aba49c42d6c0f0`.`link_tabel` (`employee_id`, `task_id`) " +
                            "VALUES (?, ?);");

            stmt.setInt(1,employeeID);
            stmt.setInt(2,taskID);

            stmt.executeUpdate();


        } catch (SQLException e) {
            System.out.println("Information could not be inserted into database");
            System.out.println(e.getMessage());
        }
    }


    public ArrayList<Task> getTaskInArrayForGantt(){

    }
}
