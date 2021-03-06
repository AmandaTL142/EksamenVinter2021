package com.example.eksamenvinter2021.Resporsitories;

import com.example.eksamenvinter2021.Models.Employee;
import com.example.eksamenvinter2021.Models.SubTask;
import com.example.eksamenvinter2021.Utility.ConnectionManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

//Hele klassen lavet af Andrea
public class SubTaskRepo {

    Connection conn = ConnectionManager.getConnection();

    public void insertNewSubtaskToDB(SubTask subTask) {
        String sql = "INSERT INTO `heroku_7aba49c42d6c0f0`.`subtasks` " +
                "(`title`, `description`, `estimatedTime`, `time_used`, `status`, " +
                "`project_id`, `task_id`, `start_date`, `end_date`) " +
                "VALUES (?,?,?,?,?,?,?,?,?);";
        try {
            PreparedStatement stmt = conn.prepareStatement(sql);

            stmt.setString(1,subTask.getSubtaskTitle());
            stmt.setString(2,subTask.getSubtaskDescription());
            stmt.setString(3,subTask.getSubtaskEstimatedTime());
            stmt.setString(4,subTask.getSubtaskTimeUsed());
            stmt.setString(5,subTask.getSubtaskStatus());
            stmt.setInt(6,subTask.getSubtaskProjectId());
            stmt.setInt(7,subTask.getTaskId());
            stmt.setString(8,subTask.getSubtaskStartDate());
            stmt.setString(9,subTask.getSubtaskEndDate());

            stmt.executeUpdate();


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
            int projectId = rs.getInt("project_id");
            int taskId = rs.getInt("task_id");
            String startDate = rs.getString("start_date");
            String endDate = rs.getString("end_date");

            sb = new SubTask(title,description,estimatedTime,timeUsed,status,projectId,taskId,startDate,endDate);
            sb.setSubtaskId(id);
            sb.setSubtaskTitle(title);
            sb.setSubtaskDescription(description);
            sb.setSubtaskEstimatedTime(estimatedTime);
            sb.setSubtaskTimeUsed(timeUsed);
            sb.setSubtaskStatus(status);
            sb.setSubtaskProjectId(projectId);
            sb.setTaskId(taskId);
            sb.setSubtaskStartDate(startDate);
            sb.setSubtaskEndDate(endDate);

        } catch (SQLException e) {
            System.out.println("Couldn't get subtask with id " + id + " from database");
            System.out.println(e.getMessage());

        }

        return sb;
    }

    public void updateSubtask(SubTask sb){

        try {
            PreparedStatement stmt = conn.prepareStatement("UPDATE `heroku_7aba49c42d6c0f0`.`subtasks` " +
                    "SET `subtask_id` = ?, `title` =?, `description` =?, " +
                    "`estimated_time` = ?, `time_used` = ?, `status` =?, `project_id` =?," +
                    " `task_id` =?, `start_date` =?, `end_date` =? WHERE (`subtask_id` =?);");

            stmt.setString(1,sb.getSubtaskTitle());
            stmt.setString(2,sb.getSubtaskDescription());
            stmt.setString(3,sb.getSubtaskEstimatedTime());
            stmt.setString(4,sb.getSubtaskTimeUsed());
            stmt.setString(5,sb.getSubtaskStatus());
            stmt.setInt(6,sb.getSubtaskProjectId());
            stmt.setInt(7,sb.getTaskId());
            stmt.setString(8,sb.getSubtaskStartDate());
            stmt.setString(8,sb.getSubtaskEndDate());
            stmt.setInt(9,sb.getSubtaskId());

            stmt.executeUpdate();


        } catch (SQLException e) {
            System.out.println("Couldn't update subtask with id " + sb.getSubtaskId() + " in database");
            System.out.println(e.getMessage());
        }
    }

    public int getSubtaskId(String subtaskTitle){
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

    public ArrayList<SubTask> getAllSubtaskInTask(int tId){
        ArrayList<SubTask> allSubtasks = new ArrayList<>();

        PreparedStatement stmt = null;
        try {
            stmt = conn.prepareStatement("SELECT * FROM heroku_7aba49c42d6c0f0.subtasks where task_id=?");
            stmt.setInt(1,tId);

            ResultSet rs = stmt.executeQuery();

            while(rs.next()){
                int subtaskId = rs.getInt("subtask_id");
                String title = rs.getString("title");
                String description = rs.getString("description");
                String estimated_time = rs.getString("estimated_time");
                String timeUsed = rs.getString("time_used");
                String status = rs.getString("status");
                int projectId = rs.getInt("project_id");
                int taskId = rs.getInt("task_id");
                String startDate = rs.getString("start_date");
                String endDate = rs.getString("end_date");

                SubTask sb = new SubTask();
                sb.setSubtaskId(subtaskId);
                sb.setSubtaskTitle(title);
                sb.setSubtaskDescription(description);
                sb.setSubtaskEstimatedTime(estimated_time);
                sb.setSubtaskTimeUsed(timeUsed);
                sb.setSubtaskStatus(status);
                sb.setSubtaskProjectId(projectId);
                sb.setTaskId(taskId);
                sb.setSubtaskStartDate(startDate);
                sb.setSubtaskEndDate(endDate);

                allSubtasks.add(sb);
            }

        } catch (SQLException e) {
            System.out.println("Couldn't get subtasks from database");
            System.out.println(e.getMessage());
        }
        return allSubtasks;
    }

    public void deleteSubtaskFromDB(int id){

        try {
            PreparedStatement stmt = conn.prepareStatement("DELETE FROM `heroku_7aba49c42d6c0f0`.`subtasks` WHERE `subtask_id` = ?;");
            stmt.setInt(1,id);
            stmt.executeUpdate();

        } catch (SQLException e) {
            System.out.println("Couldn't delete task with id " + id + " from database");
            System.out.println(e.getMessage());
        }
    }

    public ArrayList<SubTask> getSubtaskInArray(){

        ArrayList<SubTask> subtaskArray = new ArrayList<>();

        try {
            PreparedStatement stmt = conn.prepareStatement("SELECT * FROM " +
                    "heroku_7aba49c42d6c0f0.subtasks;");

            ResultSet rs = stmt.executeQuery();

            while(rs.next()){
                int subtaskId = rs.getInt("subtask_id");
                String title = rs.getString("title");
                String description = rs.getString("description");
                String estimatedTime = rs.getString("estimated_time");
                String timeUsed = rs.getString("time_used");
                String status = rs.getString("status");
                int projectId = rs.getInt("project_id");
                int taskId = rs.getInt("task_id");
                String startDate = rs.getString("start_date");
                String endDate = rs.getString("end_date");

                /*N??jes med at lave objekt med tom construktor,
                s?? det ikke g??r i karambolage med model og man skal ende med at oprette en constructor,
                som skal matche med den man laver her ift. r??kkef??lge datatyper*/
                SubTask sb = new SubTask();
                sb.setSubtaskId(subtaskId);
                sb.setSubtaskTitle(title);
                sb.setSubtaskDescription(description);
                sb.setSubtaskEstimatedTime(estimatedTime);
                sb.setSubtaskTimeUsed(timeUsed);
                sb.setSubtaskStatus(status);
                sb.setSubtaskProjectId(projectId);
                sb.setTaskId(taskId);
                sb.setSubtaskStartDate(startDate);
                sb.setSubtaskEndDate(endDate);

                subtaskArray.add(sb);
            }


        } catch (SQLException e) {
            System.out.println("Couldn't get subtask from database");
            System.out.println(e.getMessage());
        }
        return  subtaskArray;
    }

    public ArrayList<SubTask> getSubtaskConnectedToEmployee(int employeeId){
        ArrayList<Integer> subtaskIds = new ArrayList<>();
        ArrayList<SubTask> subtaskObjects = new ArrayList<>();

        try {
            PreparedStatement stmt = conn.prepareStatement("SELECT * FROM " +
                    "heroku_7aba49c42d6c0f0.link_table WHERE employee_id=?;");
            stmt.setInt(1,employeeId);

            ResultSet rs = stmt.executeQuery();

            while (rs.next()){
                int subtaskId = rs.getInt("subtask_id");
                subtaskIds.add(subtaskId);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return subtaskObjects;
    }

    public ArrayList<Employee> getEmployeeFromSubtask(int subtaskId){
        ArrayList<Integer> employeeIds = new ArrayList<>();
        ArrayList<Employee> employeeObjects = new ArrayList<>();

        try {
            PreparedStatement stmt = conn.prepareStatement("SELECT * FROM " +
                    "heroku_7aba49c42d6c0f0.link_table WHERE task_id=?;");
            stmt.setInt(1,subtaskId);
            ResultSet rs = stmt.executeQuery();

            while(rs.next()){
                int employeeId= rs.getInt("employee_id");
                employeeIds.add(employeeId);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return employeeObjects;
    }

    public void insertLinkTTableWithEmployeeAndTaskInDB(int employeeId, int subtaskId, int taskId){
        try {
            PreparedStatement stmt = conn.prepareStatement("INSERT INTO `heroku_7aba49c42d6c0f0`.`link_table` (`employee_id`, `subtask_id`, `task_id`) " +
                    "VALUES (?, ?,?);");

            stmt.setInt(1,employeeId);
            stmt.setInt(2,subtaskId);
            stmt.setInt(3,taskId);

            stmt.executeUpdate();


        } catch (SQLException e) {
            System.out.println("Information could not be inserted into database");
            System.out.println(e.getMessage());
        }
    }


    public ArrayList<SubTask> getSubtaskInArrayForGantt() {
        ArrayList<SubTask> subtaskArray = new ArrayList<>();
        try {
            PreparedStatement stmt = ConnectionManager.getConnection().prepareStatement("SELECT * FROM " +
                    "heroku_7aba49c42d6c0f0.subtasks;");
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                int subtaskId = rs.getInt("subtask_id");
                String title = rs.getString("title");
                String status = rs.getString("status");
                int taskId = rs.getInt("task_id");
                String startDate = rs.getString("start_date");
                String endDate = rs.getString("end_date");


                SubTask sb = new SubTask();
                sb.setSubtaskId(subtaskId);
                sb.setSubtaskTitle(title);
                sb.setSubtaskStatus(status);
                sb.setTaskId(taskId);
                sb.setSubtaskStartDate(startDate);
                sb.setSubtaskEndDate(endDate);


                if(startDate != null && !startDate.isEmpty() ) {
                    if (endDate != null && !endDate.isEmpty()) {

                        String newStartDate = startDate.replace("-",",").replace("'","");
                        String newEndDate = endDate.replace("-",",").replace("'","");

                        sb.setSubtaskStartDate(newStartDate);
                        sb.setSubtaskEndDate(newEndDate);

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
