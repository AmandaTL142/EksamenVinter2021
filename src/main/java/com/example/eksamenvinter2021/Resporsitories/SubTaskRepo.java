package com.example.eksamenvinter2021.Resporsitories;

import com.example.eksamenvinter2021.Models.Employee;
import com.example.eksamenvinter2021.Models.SubTask;
import com.example.eksamenvinter2021.Utility.ConnectionManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class SubTaskRepo {

    Connection conn = ConnectionManager.getConnection();

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
            int projectID = rs.getInt("project_id");
            int taskID = rs.getInt("task_id");
            String startDate = rs.getString("start_date");
            String endDate = rs.getString("end_date");

            sb = new SubTask(title,description,estimatedTime,timeUsed,status,projectID,taskID,startDate,endDate);
            sb.setSubtaskID(id);
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

    public void updateSubtask(SubTask sb){

        try {
            PreparedStatement stmt = conn.prepareStatement("UPDATE `heroku_7aba49c42d6c0f0`.`subtasks` " +
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
            stmt.setInt(9,sb.getSubtaskID());

            stmt.executeUpdate();


        } catch (SQLException e) {
            System.out.println("Couldn't update subtask with id " + sb.getSubtaskID() + " in database");
            System.out.println(e.getMessage());
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

    public ArrayList<SubTask> getAllSubtaskInTask(int tID){
        ArrayList<SubTask> allSubtasks = new ArrayList<>();

        PreparedStatement stmt = null;
        try {
            stmt = conn.prepareStatement("SELECT * FROM heroku_7aba49c42d6c0f0.subtasks where task_id=?");
            stmt.setInt(1,tID);

            ResultSet rs = stmt.executeQuery();

            while(rs.next()){
                int subtaskID = rs.getInt("subtask_id");
                String title = rs.getString("title");
                String description = rs.getString("description");
                String estimated_time = rs.getString("estimated_time");
                String timeUsed = rs.getString("time_used");
                String status = rs.getString("status");
                int projectID = rs.getInt("project_id");
                int taskID = rs.getInt("task_id");
                String startDate = rs.getString("start_date");
                String endDate = rs.getString("end_date");

                SubTask sb = new SubTask();
                sb.setSubtaskID(subtaskID);
                sb.setTitle(title);
                sb.setDescription(description);
                sb.setEstimatedTime(estimated_time);
                sb.setTimeUsed(timeUsed);
                sb.setStatus(status);
                sb.setProjectID(projectID);
                sb.setTaskID(taskID);
                sb.setStartDate(startDate);
                sb.setEndDate(endDate);

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

                /*Nøjes med at lave objekt med tom construktor,
                så det ikke går i karambolage med model og man skal ende med at oprette en constructor,
                som skal matche med den man laver her ift. rækkefølge datatyper*/
                SubTask sb = new SubTask();
                sb.setSubtaskID(subtaskID);
                sb.setTitle(title);
                sb.setDescription(description);
                sb.setEstimatedTime(estimatedTime);
                sb.setTimeUsed(timeUsed);
                sb.setStatus(status);
                sb.setProjectID(projectID);
                sb.setTaskID(taskID);
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

    public ArrayList<SubTask> getSubtaskConnectedToEmployee(int employeeID){
        ArrayList<Integer> subtaskIDs = new ArrayList<>();
        ArrayList<SubTask> subtaskObjects = new ArrayList<>();

        try {
            PreparedStatement stmt = conn.prepareStatement("SELECT * FROM " +
                    "heroku_7aba49c42d6c0f0.link_table WHERE employee_id=?;");
            stmt.setInt(1,employeeID);

            ResultSet rs = stmt.executeQuery();

            while (rs.next()){
                int subtaskID = rs.getInt("subtask_id");
                subtaskIDs.add(subtaskID);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return subtaskObjects;
    }

    public ArrayList<Employee> getEmployeeFromSubtask(int subtaskID){
        ArrayList<Integer> employeeIDs = new ArrayList<>();
        ArrayList<Employee> employeeObjects = new ArrayList<>();

        try {
            PreparedStatement stmt = conn.prepareStatement("SELECT * FROM " +
                    "heroku_7aba49c42d6c0f0.link_table WHERE task_id=?;");
            stmt.setInt(1,subtaskID);
            ResultSet rs = stmt.executeQuery();

            while(rs.next()){
                int employeeID= rs.getInt("employee_id");
                employeeIDs.add(employeeID);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return employeeObjects;
    }

    public void insertLinkTTableWithEmployeeAndTaskInDB(int employeeID, int subtaskID, int taskID){
        try {
            PreparedStatement stmt = conn.prepareStatement("INSERT INTO `heroku_7aba49c42d6c0f0`.`link_table` (`employee_id`, `subtask_id`, `task_id`) " +
                    "VALUES (?, ?,?);");

            stmt.setInt(1,employeeID);
            stmt.setInt(2,subtaskID);
            stmt.setInt(3,taskID);

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
                int taskID = rs.getInt("task_id");
                String startDate = rs.getString("start_date");
                String endDate = rs.getString("end_date");


                SubTask sb = new SubTask();
                sb.setSubtaskID(subtaskId);
                sb.setTitle(title);
                sb.setStatus(status);
                sb.setTaskID(taskID);
                sb.setStartDate(startDate);
                sb.setEndDate(endDate);


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
