package com.example.eksamenvinter2021.Resporsitories;

import com.example.eksamenvinter2021.Models.Project;
import com.example.eksamenvinter2021.Models.SubTask;
import com.example.eksamenvinter2021.Utility.JDBC;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class SubTaskRepo {

    Connection conn = JDBC.getConnection();

    public void insertNewSubtaskToDB(SubTask subTask) {
        String sql = "INSERT INTO `heroku_7aba49c42d6c0f0`.`subtasks` " +
                "(`title`, `description`, `estimatedTime`, `time_used`, `status`, " +
                "`project_id`, `task_id`, `start_date`, `end_date`) " +
                "VALUES (?,?,?,?,?,?,?,?,?);";
        try {
            PreparedStatement stmt = conn.prepareStatement(sql);

            stmt.setString(1,subTask.getTitle());
            stmt.setString(2,subTask.getDescription());
            stmt.setString(3,subTask.getEstimatedTime());
            stmt.setString(4,subTask.getTimeUsed());
            stmt.setString(5,subTask.getStatus());
            stmt.setInt(6,subTask.getProjectID());
            stmt.setInt(7,subTask.getTaskID());
            stmt.setString(8,subTask.getStartDate());
            stmt.setString(9,subTask.getEndDate());

            stmt.executeQuery();


        } catch (SQLException e) {
            System.out.println("connection not found");
            System.out.println(e.getMessage());
        }
    }

    public SubTask getSubtaskFromDB(int id){
        SubTask sb = new SubTask();

        try {
            PreparedStatement stmt = conn.prepareStatement("SELECT * FROM " +
                    "heroku_7aba49c42d6c0f0.subtasks WHERE subtask_id=?;");

            stmt.setInt(1,id);
            ResultSet rs= stmt.executeQuery();

            rs.next();
            String title = rs.getString("title");
            String description = rs.getString("description");
            String estimatedTime = rs.getString("estimated_time");
            String timeUsed = rs.getString("time_used");
            String status = rs.getString("status");
            int projectID = rs.getInt("project_id");
            int taskID = rs.getInt("task_id");
            String startDate = rs.getString("start_date");
            String endDate = rs.getString("end_date");

            sb = new SubTask(title,description,estimatedTime,timeUsed,status,projectID,taskID,startDate,endDate);
            sb.setTitle(title);
            sb.setDescription(description);
            sb.setEstimatedTime(estimatedTime);
            sb.setTimeUsed(timeUsed);
            sb.setStatus(status);
            sb.setProjectID(projectID);
            sb.setTaskID(taskID);
            sb.setStartDate(startDate);
            sb.setEndDate(endDate);

        } catch (SQLException e) {
            System.out.println("Couldn't get subtask with id " + id + " from database");
            System.out.println(e.getMessage());

        }

        return sb;
    }

    public void updateProjectInDB(SubTask sb){
        PreparedStatement stmt = null;
        try {
            stmt = conn.prepareStatement("UPDATE `heroku_7aba49c42d6c0f0`.`subtasks` " +
                    "SET `subtask_id` = ?, `title` =?, `description` =?, " +
                    "`estimated_time` = ?, `time_used` = ?, `status` =?, `project_id` =?," +
                    " `task_id` =?, `start_date` =?, `end_date` =? WHERE (`subtask_id` =?);");

            stmt.setString(1,sb.getTitle());
            stmt.setString(2,sb.getDescription());
            stmt.setString(3,sb.getEstimatedTime());
            stmt.setString(4,sb.getTimeUsed());
            stmt.setString(5,sb.getStatus());
            stmt.setInt(6,sb.getProjectID());
            stmt.setInt(7,sb.getTaskID());
            stmt.setString(8,sb.getStartDate());
            stmt.setString(8,sb.getEndDate());

            stmt.executeUpdate();


        } catch (SQLException e) {
            System.out.println("Couldn't update subtask with id " + sb.getSubtaskID() + " in database");
            System.out.println(e.getMessage());
        }

    }

    public void deleteSubtaskFromDB(int id){

        try {
            PreparedStatement stmt = conn.prepareStatement("DELETE FROM `heroku_7aba49c42d6c0f0`.`projects` WHERE `subtask_id` = ?;");
            stmt.setInt(1,id);
            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public int getSubtaskID(String subtaskTitle){
        try {
            PreparedStatement stmt = conn.prepareStatement("SELECT subtask_id FROM " +
                    "heroku_7aba49c42d6c0f0.projects WHERE title=?;");
            stmt.setString(1, subtaskTitle);

            ResultSet rs = stmt.executeQuery();
            rs.next();

            int id = rs.getInt("subtask_id");
            return id;

        } catch (SQLException e) {
            System.out.println("Couldn't get id for with title " + subtaskTitle + " from database");
            System.out.println(e.getMessage());

            return 0;
        }
    }

    public ArrayList<SubTask> getSubtaskInArray(){

        ArrayList<SubTask> subtaskArray = new ArrayList<>();

        try {
            PreparedStatement stmt = JDBC.getConnection().prepareStatement("SELECT * FROM " +
                    "heroku_7aba49c42d6c0f0.subtasks;");

            ResultSet rs = stmt.executeQuery();

            while(rs.next()){
                int subtaskID = rs.getInt("subtask_id");
                String title = rs.getString("title");
                String description = rs.getString("description");
                String estimatedTime = rs.getString("estimated_time");
                String timeUsed = rs.getString("time_used");
                String status = rs.getString("status");
                int projectID = rs.getInt("project_id");
                int taskID = rs.getInt("task_id");
                String startDate = rs.getString("start_date");
                String endDate = rs.getString("end_date");

                SubTask sb = new SubTask(title, description,estimatedTime,timeUsed,status,projectID,taskID,startDate,endDate);
                sb.setTitle(title);
                sb.setDescription(description);
                sb.setEstimatedTime(estimatedTime);
                sb.setTimeUsed(timeUsed);
                sb.setStatus(status);
                sb.setStartDate(startDate);
                sb.setEndDate(endDate);

                subtaskArray.add(sb);
            }


        } catch (SQLException e) {
            System.out.println("Couldn't get subtask from database");
            System.out.println(e.getMessage());
        }
        return  subtaskArray;

    }

    public ArrayList<SubTask> getSubtaskInArrayForGantt() {
        ArrayList<SubTask> subtaskArray = new ArrayList<>();
        try {
            PreparedStatement stmt = JDBC.getConnection().prepareStatement("SELECT * FROM " +
                    "heroku_7aba49c42d6c0f0.subtasks;");
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                int subtaskId = rs.getInt("subtask_id");
                String title = rs.getString("title");
                String status = rs.getString("status");
                int taskID = rs.getInt("task_id");
                String startDate = rs.getString("start_date");
                String endDate = rs.getString("end_date");


                SubTask sb = new SubTask(subtaskId, title, status, taskID, startDate, endDate);
               sb.setSubtaskID(subtaskId);
               sb.setTitle(title);
               sb.setStatus(status);
               sb.setTaskID(taskID);


                if(startDate != null && !startDate.isEmpty() ) {
                    if (endDate != null && !endDate.isEmpty()) {

                        String newStartDate = startDate.replace("-",",").replace("'","");
                        String newEndDate = endDate.replace("-",",").replace("'","");

                        sb.setStartDate(newStartDate);
                        sb.setEndDate(newEndDate);

                        subtaskArray.add(sb);
                    }
                }
            }

        } catch (SQLException e) {
            System.out.println("Couldn't get subtask from database");
            System.out.println(e.getMessage());
        }
        return subtaskArray;
    }


}
