package com.example.eksamenvinter2021.Resporsitories;


import com.example.eksamenvinter2021.Models.Project;
import com.example.eksamenvinter2021.Models.Task;
import com.example.eksamenvinter2021.Utility.JDBC;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static jdk.javadoc.internal.doclets.formats.html.markup.HtmlStyle.title;

public class TaskRepo {

    Connection conn = JDBC.getConnection();

    public void insertNewTaskToDB(Task task){

        //TODO hvorfor vil den ikke indsætte i DB?

        int projectId= getProjectId("projectTitle");
        int subProjectID= getSubProjectId("subProjectTitle");


        String insertTaskSQL ="INSERT INTO heroku_7aba49c42d6c0f0.tasks VALUES(?,?,?,?,?,?,?,?)";


        try{

            PreparedStatement stmt = conn.prepareStatement(insertTaskSQL);
            stmt.setString(2,task.getTitle());
            stmt.setString(3,task.getDescription());
            stmt.setString(4,task.getEstimatedTime());
            stmt.setString(5,task.getTimeUsed());
            stmt.setString(6,task.getStatus());
            stmt.setInt(7,getProjectId("projectTitle"));
            stmt.setInt(8,getSubProjectId("subProjectTitle"));

            stmt.executeUpdate();


        } catch (SQLException e) {
            System.out.println("connection not found");
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }


    public int getProjectId(String projectTitle) {
        int projectID=0;
        try {
            PreparedStatement stmt = conn.prepareStatement("SELECT project_id FROM " +
                    "heroku_7aba49c42d6c0f0.projects WHERE title=?;");
            stmt.setString(1, projectTitle);
            ResultSet rs = stmt.executeQuery();
            while(rs.next()){
                projectID = rs.getInt("project_id");
            }


        } catch(SQLException e){
            System.out.println("Couldn't get id for with title " + projectTitle + " from database");
            System.out.println(e.getMessage());
        }
        return projectID;
    }


    public int getSubProjectId(String subProjectTitle) {
        int subProjectID=0;
        try {
            PreparedStatement stmt = conn.prepareStatement("SELECT subproject_id FROM " +
                    "heroku_7aba49c42d6c0f0.projects WHERE title=?;");
            stmt.setString(1, subProjectTitle);
            ResultSet rs = stmt.executeQuery();
            while(rs.next()){
                subProjectID = rs.getInt("subproject_id");
            }


        } catch(SQLException e){
            System.out.println("Couldn't get id for with title " + subProjectTitle + " from database");
            System.out.println(e.getMessage());
        }
        return subProjectID;
    }


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
              task.setEstimatedTime(rs.getString(4));
              task.setTimeUsed(rs.getString(5));
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
