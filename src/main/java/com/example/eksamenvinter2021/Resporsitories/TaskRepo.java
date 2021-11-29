package com.example.eksamenvinter2021.Resporsitories;


import com.example.eksamenvinter2021.Models.Project;
import com.example.eksamenvinter2021.Models.Task;
import com.example.eksamenvinter2021.Utility.JDBC;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class TaskRepo {

    Connection conn = JDBC.getConnection();

    public void insertNewTaskToDB(Task task, int projectID){

        String insertTaskSQL ="INSERT INTO tasks(task_id, title, description,estimated_time, " +
                "time_used, status, project_id) values (?,?,?,?,?,?,?)";


        try{

            PreparedStatement stmt = conn.prepareStatement(insertTaskSQL);
            stmt.setInt(1,task.getTaskID());
            stmt.setString(2,task.getTitle());
            stmt.setString(3,task.getDescription());
            stmt.setTime(4,task.getEstimatedTime());
            stmt.setTime(5,task.getTimeUsed());
            stmt.setString(6,task.getStatus());
            stmt.setInt(7,projectID);

        } catch (SQLException e) {
            System.out.println("connection not found");
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }

   /* public int getProjectId(Project project) {
        int projectId = 0;
        try {
            PreparedStatement stmt = conn.prepareStatement("SELECT * FROM project where project_id = ?");
            stmt.setString(1, String.valueOf(project.getProjectId()));
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                projectId = rs.getInt(7);
            }
        } catch (SQLException e) {
            System.out.println("Something went wrong");
            System.out.println(e.getMessage());
        }
        return projectId;
    }


    public int getSubProjectId(Project project) {
        int subProjectId = 0;
        try {
            PreparedStatement stmt = conn.prepareStatement("SELECT subproject_id FROM subprojects WHERE title= ?");
            stmt.setString(1, String.valueOf(project.getProjectId()));
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                subProjectId = rs.getInt(8);
            }
        } catch (SQLException e) {
            System.out.println("Something went wrong");
            System.out.println(e.getMessage());
        }
        return projectId;
    }*/

    public void readTask(){

    }

  public void updateTask(){
      Task task = new Task();

      String sql= "UPDATE task SET title=?, description=?, estimatedTime=?, time_used=?, status=?";

      try {
          PreparedStatement stmt = conn.prepareStatement(sql);
          stmt.executeUpdate();

          ResultSet rs = stmt.executeQuery();
          while (rs.next()){
              task.setTitle(rs.getString(2));
              task.setDescription(rs.getString(3));
              task.setEstimatedTime(rs.getTime(4));
              task.setTimeUsed(rs.getTime(5));
              task.setStatus(rs.getString(6));
          }

      } catch (SQLException e) {
          System.out.println("no connection");
          System.out.println(e.getMessage());
          e.printStackTrace();
      }

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
