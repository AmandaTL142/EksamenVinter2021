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

            PreparedStatement stmt = conn.prepareStatement("INSERT INTO `heroku_7aba49c42d6c0f0`.`tasks` (`title`, `description`, `estimated_time`, `time_used`, `status`) VALUES (?, ?, ?, ?, ?)");

            stmt.setString(1,task.getTitle());
            stmt.setString(2,task.getDescription());
            stmt.setString(3,task.getEstimatedTime());
            stmt.setString(4,task.getTimeUsed());
            stmt.setString(5,task.getStatus());
            //stmt.setInt(6,getProjectId("projectTitle"));
            //stmt.setInt(7,getSubProjectId("subProjectTitle"));
            stmt.executeUpdate();


        } catch (SQLException e) {
            System.out.println("connection not found");
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }


    public int getProjectId(String projectTitle) {
        try {
            PreparedStatement stmt = JDBC.getConnection().prepareStatement("SELECT project_id FROM " +
                    "heroku_7aba49c42d6c0f0.projects WHERE title=?;");
            stmt.setString(1, projectTitle);
            ResultSet rs = stmt.executeQuery();
            rs.next();
            int id = rs.getInt("project_id");
            return id;

        } catch(SQLException e){
            System.out.println("Couldn't get id for with title " + projectTitle + " from database");
            System.out.println(e.getMessage());
        }
        return 0;
    }


    public int getSubProjectId(String subProjectTitle) {
        try {
            PreparedStatement stmt = JDBC.getConnection().prepareStatement("SELECT project_id FROM " +
                    "heroku_7aba49c42d6c0f0.projects WHERE title=?;");
            stmt.setString(1, subProjectTitle);
            ResultSet rs = stmt.executeQuery();
            rs.next();
            int id = rs.getInt("project_id");
            return id;

        } catch(SQLException e){
            System.out.println("Couldn't get id for with title " + subProjectTitle + " from database");
            System.out.println(e.getMessage());
        }
        return 0;
    }

    public Integer fetchSingleTask(){
        /*The users want a feature, such that they can see a single employee by their employeenumber*/

        Integer requestTask = 0;

        try {
            PreparedStatement ps = conn.prepareStatement("Select * from tasks where task_id=?;");
            ResultSet rs = ps.executeQuery();
            while (rs.next()){
                requestTask = rs.getInt("id");
            }

        } catch (SQLException e) {
            System.out.println("Can not find task" + e.getMessage());
        }

        return requestTask;
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
                allTasks.add(t);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return allTasks;

    }

  public Task updateTask( Task task){

      String sql = "UPDATE `heroku_7aba49c42d6c0f0`.`tasks` SET `title` =?, `description` =?, `estimated_time` =?, `time_used` =? WHERE (`task_id` =?) VALUES (?, ?, ?, ?);";

      try {
          PreparedStatement stmt = conn.prepareStatement(sql);

          stmt.setString();
          stmt.setString();
          stmt.setString();
          stmt.setString();

          stmt.executeUpdate();

      } catch (SQLException e) {
          System.out.println("no connection");
          System.out.println(e.getMessage());
          e.printStackTrace();
      }
      return task;

  }

  public void deleteTask(int inputFromUser){
        String sql ="DELETE from tasks where task_id=?";

      try {
          PreparedStatement stmt = conn.prepareStatement(sql);
          stmt.setInt(1,inputFromUser);
          stmt.executeUpdate();

      } catch (SQLException e) {
          System.out.println("no connection");
          System.out.println(e.getMessage());
          e.printStackTrace();
      }
  }

}
