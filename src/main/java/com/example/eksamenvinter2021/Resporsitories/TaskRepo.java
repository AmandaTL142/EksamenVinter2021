package com.example.eksamenvinter2021.Resporsitories;


import com.example.eksamenvinter2021.Models.Project;
import com.example.eksamenvinter2021.Models.Task;
import com.example.eksamenvinter2021.Utility.JDBC;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class TaskRepo {

    Connection conn = JDBC.getConnection();

    public void insertNewTaskToDB(Task task){
        //int projectId= getProjectId("projectTitle");
        //int subProjectID= getSubProjectId("subProjectTitle");

        try{

            PreparedStatement stmt = conn.prepareStatement("INSERT INTO `heroku_7aba49c42d6c0f0`.`tasks` " +
                    "(`title`, `description`, `estimated_time`, `time_used`, `status`, `project_id`," +
                    " `subproject_id`, `start_date`, `end_date`) VALUES (?, ?, ?, ?, ?,?,?,?,?)");

            stmt.setString(1,task.getTitle());
            stmt.setString(2,task.getDescription());
            stmt.setString(3,task.getEstimatedTime());
            stmt.setString(4,task.getTimeUsed());
            stmt.setString(5,task.getStatus());
            stmt.setInt(6,task.getProjectId());
            stmt.setInt(7,task.getSubprojectId());
            stmt.setString(8, task.getStartDate());
            stmt.setString(9,task.getEndDate());
            //stmt.setInt(6,getProjectId("projectTitle"));
            //stmt.setInt(7,getSubProjectId("subProjectTitle"));
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
            t.setTitle(title);
            t.setDescription(description);
            t.setEstimatedTime(estimated_time);
            t.setTimeUsed(timeUsed);
            t.setStatus(status);

        } catch (SQLException e) {
            System.out.println("Couldn't get task with id " + id + " from database");
            System.out.println(e.getMessage());
        }
        return t;
    }

    public void updateTask(Task task){

        String sql = "UPDATE `heroku_7aba49c42d6c0f0`.`tasks` SET `title` =?, `description` = ?, `estimated_time` = ?, `time_used` = ?, `status` =?, `start_date` = ?, `end_date` =? WHERE (`task_id` =?);\n";

        try {
            PreparedStatement stmt = conn.prepareStatement(sql);

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

    public ArrayList<Task> getAllTasksInnProject(int pID){
        ArrayList<Task> allTasks = new ArrayList<>();
        try {

            PreparedStatement stmt = conn.prepareStatement("SELECT * FROM heroku_7aba49c42d6c0f0.tasks where project_id=?");
            stmt.setInt(1, pID);

            ResultSet rs = stmt.executeQuery();


            while(rs.next()){
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
        PreparedStatement stmt = null;
        try {
            stmt = conn.prepareStatement("DELETE FROM `heroku_7aba49c42d6c0f0`.`projects` WHERE `project_id` = ?;");
            stmt.setInt(1, taskID);
            stmt.executeUpdate();

        } catch (SQLException e) {
            System.out.println("Couldn't delete project with id " + taskID + " from database");
            System.out.println(e.getMessage());
        }



    }

}
