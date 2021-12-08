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

    public ArrayList<Task> getAllTasks(int projectID){
        ArrayList<Task> allTasks = new ArrayList<>();
        try {

            PreparedStatement stmt = conn.prepareStatement("SELECT * FROM heroku_7aba49c42d6c0f0.tasks where project_id=?");
            stmt.setInt(1, projectID);

            ResultSet rs = stmt.executeQuery();


            while(rs.next()){
                Task t= new Task();
                t.setTitle(rs.getString(1));
                t.setDescription(rs.getString(2));
                t.setEstimatedTime(rs.getString(3));
                t.setTimeUsed(rs.getString(4));
                t.setStatus(rs.getString(5));
                t.setStartDate(rs.getString(6));
                t.setEndDate(rs.getString(7));
                allTasks.add(t);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return allTasks;

    }

    public int getTaskId(String taskTitle){
        try {
            PreparedStatement stmt = conn.prepareStatement("SELECT task_id from" +
                    "heroku_7aba49c42d6c0f0.tasks where title=?");
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


    public void deleteTask(int taskID){
        String sql ="DELETE from tasks where task_id=?";

        try {
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1,taskID);
            stmt.executeUpdate();

        } catch (SQLException e) {
            System.out.println("no connection");
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }

}
