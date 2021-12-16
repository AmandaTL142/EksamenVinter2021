package com.example.eksamenvinter2021.Resporsitories;


import com.example.eksamenvinter2021.Models.Employee;
import com.example.eksamenvinter2021.Models.Task;
import com.example.eksamenvinter2021.Utility.ConnectionManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;


public class TaskRepo {

    Connection conn = ConnectionManager.getConnection();

    //Andrea
    public void insertNewTaskToDB(Task task){

        try{

            PreparedStatement stmt = ConnectionManager.getConnection().prepareStatement("INSERT INTO " +
                    "`heroku_7aba49c42d6c0f0`.`tasks` (`title`, " +
                    "`description`, `estimated_time`, `time_used`, `status`, `project_id`, " +
                    "`subproject_id`, `start_date`, `end_date`) VALUES (?,?,?,?,?,?,?,?,?);");


            stmt.setString(1,task.getTaskTitle());
            stmt.setString(2,task.getTaskDescription());
            stmt.setString(3,task.getTaskEstimatedTime());
            stmt.setString(4,task.getTaskTimeUsed());
            stmt.setString(5,task.getTaskStatus());
            stmt.setInt(6,task.getTaskProjectId());

            stmt.setString(8, task.getTaskStartDate());
            stmt.setString(9,task.getTaskEndDate());

            if(task.getTaskSubprojectId() != 0){
                stmt.setInt(7,task.getTaskSubprojectId());
            }
            else{
                stmt.setString(7,null);
            }

            stmt.executeUpdate();


        } catch (SQLException e) {
            System.out.println("Task could not be inserted into database");
            System.out.println(e.getMessage());
        }
    }

    //Andrea
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
            int projectId = rs.getInt("project_id");
            int subprojectId = rs.getInt("subproject_id");
            String startDate = rs.getString("start_date");
            String endDate = rs.getString("end_date");


            t = new Task(title, description,estimated_time,timeUsed,status);
            t.setTaskId(id);
            t.setTaskTitle(title);
            t.setTaskDescription(description);
            t.setTaskEstimatedTime(estimated_time);
            t.setTaskTimeUsed(timeUsed);
            t.setTaskStatus(status);
            t.setTaskProjectId(projectId);
            t.setTaskSubprojectId(subprojectId);
            t.setTaskStartDate(startDate);
            t.setTaskEndDate(endDate);


        } catch (SQLException e) {
            System.out.println("Couldn't get task with id " + id + " from database");
            System.out.println(e.getMessage());
        }
        return t;
    }
    //Andrea
    public void updateTask(Task task){

        String sql = "UPDATE `heroku_7aba49c42d6c0f0`.`tasks` SET `title` =?, `description` = ?, `estimated_time` = ?, `time_used` = ?, `status` =?, `start_date` = ?, `end_date` =? WHERE (`task_id` =?);";

        try {
            PreparedStatement stmt = conn.prepareStatement(sql);

            stmt.setInt(8,task.getTaskId());
            stmt.setString(1,task.getTaskTitle());
            stmt.setString(2,task.getTaskDescription());
            stmt.setString(3,task.getTaskEstimatedTime());
            stmt.setString(4,task.getTaskTimeUsed());
            stmt.setString(5,task.getTaskStatus());
            stmt.setString(6,task.getTaskStartDate());
            stmt.setString(7,task.getTaskEndDate());


            stmt.executeUpdate();

        } catch (SQLException e) {
            System.out.println("no connection");
            System.out.println(e.getMessage());
            e.printStackTrace();
        }

    }

    //Andrea
    public int getTaskId(String taskTitle){
        try {
            PreparedStatement stmt = ConnectionManager.getConnection().prepareStatement("SELECT task_id FROM " +
                    "heroku_7aba49c42d6c0f0.tasks WHERE title=?;");
            stmt.setString(1,taskTitle);
            ResultSet rs = stmt.executeQuery();

            rs.next();
            int id = rs.getInt("task_id");
            return id;
        } catch (SQLException e) {
            System.out.println("Couldn't get id for task with title " + taskTitle + " from database");
            System.out.println(e.getMessage());
        }
        return 0;
    }

    //Andrea
    public ArrayList<Task> getAllTasksInProject(int pId){
        ArrayList<Task> allTasks = new ArrayList<>();
        try {

            PreparedStatement stmt = conn.prepareStatement("SELECT * FROM heroku_7aba49c42d6c0f0.tasks where project_id=?");
            stmt.setInt(1, pId);

            ResultSet rs = stmt.executeQuery();


            while(rs.next()){
                int taskId = rs.getInt("task_id");
                String title = rs.getString("title");
                String description = rs.getString("description");
                String estimated_time = rs.getString("estimated_time");
                String timeUsed = rs.getString("time_used");
                String status = rs.getString("status");
                int projectId = rs.getInt("project_id");
                int subprojectId = rs.getInt("subproject_id");
                String startDate = rs.getString("start_date");
                String endDate = rs.getString("end_date");

                Task t = new Task();
                t.setTaskTitle(title);
                t.setTaskDescription(description);
                t.setTaskEstimatedTime(estimated_time);
                t.setTaskTimeUsed(timeUsed);
                t.setTaskStatus(status);
                t.setTaskProjectId(projectId);
                t.setTaskSubprojectId(subprojectId);
                t.setTaskStartDate(startDate);
                t.setTaskEndDate(endDate);
                t.setTaskId(taskId);

                allTasks.add(t);
            }
        } catch (SQLException e) {
            System.out.println("Couldn't get tasks from database");
            System.out.println(e.getMessage());
        }
        return allTasks;

    }

    //Andrea
    public void deleteTask(int taskId){

        try {
            PreparedStatement stmt = conn.prepareStatement("DELETE FROM `heroku_7aba49c42d6c0f0`.`tasks` WHERE `task_id` = ?;");
            stmt.setInt(1, taskId);
            stmt.executeUpdate();

        } catch (SQLException e) {
            System.out.println("Couldn't delete task with id " + taskId + " from database");
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

    //Andrea
    public ArrayList<Task> getTasksInArray() {
        ArrayList<Task> taskArray = new ArrayList<>();
        try {
            PreparedStatement stmt = ConnectionManager.getConnection().prepareStatement("SELECT * FROM " +
                    "heroku_7aba49c42d6c0f0.tasks;");
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {

                int taskId = rs.getInt("task_id");
                String title = rs.getString("title");
                String description = rs.getString("description");
                String estimatedTime = rs.getString("estimated_time");
                String timeUsed = rs.getString("time_used");
                String status = rs.getString("status");
                int projectId = rs.getInt("project_id");
                int subprojectId = rs.getInt("subproject_id");
                String startDate = rs.getString("start_date");
                String endDate = rs.getString("end_date");

                Task task = new Task();
                task.setTaskId(taskId);
                task.setTaskTitle(title);
                task.setTaskDescription(description);
                task.setTaskEstimatedTime(estimatedTime);
                task.setTaskTimeUsed(timeUsed);
                task.setTaskStatus(status);
                task.setTaskProjectId(projectId);
                task.setTaskSubprojectId(subprojectId);
                task.setTaskStartDate(startDate);
                task.setTaskEndDate(endDate);


                taskArray.add(task);
            }

        } catch (SQLException e) {
            System.out.println("Couldn't get projects from database");
            System.out.println(e.getMessage());
        }
        return taskArray;
    }

    //Denne virker ikke
    public ArrayList<Task> getTaskConnectedToEmployee(int employeeId){
        ArrayList<Integer> taskIds = new ArrayList<>();
        ArrayList<Task> taskObjects = new ArrayList<>();

        try {
            PreparedStatement stmt = conn.prepareStatement("SELECT * FROM " +
                    "heroku_7aba49c42d6c0f0.link_table WHERE employee_id=?;");
            stmt.setInt(1,employeeId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()){
                int taskId = rs.getInt("task_id");
                taskIds.add(taskId);
            }

            /*Set<Integer> taskIDHashSet = new HashSet<>();
            taskIds.clear();
            taskIds.addAll(taskIDHashSet);

            taskIds.forEach(taskId){
            taskObject.add(ts.getTaskOBject(taskId))}*/

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return taskObjects;
    }
    //Andrea
    public void insertTaskToLinktable(int employeeId, int taskId, int projectId){
        try  {
            PreparedStatement stmt = conn.prepareStatement("INSERT INTO `heroku_7aba49c42d6c0f0`.`link_table` " +
                    "(`employee_id`, `project_id`, `task_id`) " +
                    "VALUES (?,?,?);");

            stmt.setInt(1,employeeId);
            stmt.setInt(2,projectId);
            stmt.setInt(3,taskId);

            stmt.executeUpdate();


        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    //Lavet af Amanda på baggrund af insertTaskToLinktable()
    public void insertTaskToLinktableWithSubproject(int employeeId, int taskId, int projectId, int subprojectId){
        try  {
            PreparedStatement stmt = conn.prepareStatement("INSERT INTO `heroku_7aba49c42d6c0f0`.`link_table` " +
                    "(`employee_id`, `project_id`, `task_id`, `subproject_id`) " +
                    "VALUES (?,?,?,?);");

            stmt.setInt(1,employeeId);
            stmt.setInt(2,projectId);
            stmt.setInt(3,taskId);
            stmt.setInt(4,subprojectId);

            stmt.executeUpdate();


        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    //Andrea
    public  ArrayList<Employee> getEmployeeFromTask(int taskId){
        ArrayList<Integer> employeeIds = new ArrayList<>();
        ArrayList<Employee> employeeObjects = new ArrayList<>();

        try {
            PreparedStatement stmt = ConnectionManager.getConnection().prepareStatement("SELECT * FROM " +
                    "heroku_7aba49c42d6c0f0.link_table WHERE task_id=?;");
            stmt.setInt(1,taskId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()){
                int emmployeeId = rs.getInt("employee_id");
                employeeIds.add(emmployeeId);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return employeeObjects;
    }
//Andrea
    public void insertLinkTableWithEmployeeAndTaskInDB(int employeeId, int taskID){
        try {
            PreparedStatement stmt = ConnectionManager.getConnection().prepareStatement
                    ("INSERT INTO `heroku_7aba49c42d6c0f0`.`link_table` (`employee_id`, `task_id`) " +
                            "VALUES (?, ?);");

            stmt.setInt(1,employeeId);
            stmt.setInt(2,taskID);

            stmt.executeUpdate();


        } catch (SQLException e) {
            System.out.println("Information could not be inserted into database");
            System.out.println(e.getMessage());
        }
    }


    public ArrayList<Task> getTaskInArrayForGantt(){
        ArrayList<Task> taskArray = new ArrayList<>();


        try {
            PreparedStatement stmt = conn.prepareStatement("SELECT * FROM " +
                    "heroku_7aba49c42d6c0f0.tasks;");

            ResultSet rs= stmt.executeQuery();

            while(rs.next()){
                int taskId = rs.getInt("task_id");
                String title = rs.getString("title");
                String status = rs.getString("status");
                int projectId = rs.getInt("project_id");
                String startDate = rs.getString("start_date");
                String endDate = rs.getString("end_date");

                Task t = new Task();
                t.setTaskId(taskId);
                t.setTaskTitle(title);
                t.setTaskStatus(status);
                t.setTaskProjectId(projectId);
                t.setTaskStartDate(startDate);
                t.setTaskEndDate(endDate);

                if(startDate != null && !startDate.isEmpty() ) {
                    if (endDate != null && !endDate.isEmpty()) {

                        String newStartDate = startDate.replace("-",",").replace("'","");
                        String newEndDate = endDate.replace("-",",").replace("'","");

                        t.setTaskStartDate(newStartDate);
                        t.setTaskEndDate(newEndDate);

                        taskArray.add(t);
                    }
                }

            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return taskArray;
    }


    /*
    public ArrayList<Task> getAllTasksInArray(){
        ArrayList<Task> taskArray = new ArrayList<>();


        try {
            PreparedStatement stmt = conn.prepareStatement("SELECT * FROM " +
                    "heroku_7aba49c42d6c0f0.tasks;");

            ResultSet rs= stmt.executeQuery();

            while(rs.next()){
                int taskID = rs.getInt("task_id");
                String title = rs.getString("title");
                String status = rs.getString("status");
                int projectID = rs.getInt("project_id");
                String startDate = rs.getString("start_date");
                String endDate = rs.getString("end_date");

                Task t = new Task();
                t.setId(taskID);
                t.setTitle(title);
                t.setStatus(status);
                t.setProjectId(projectID);
                t.setStartDate(startDate);
                t.setEndDate(endDate);

                taskArray.add(t);

            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return taskArray;
    }

     */


    //Amanda
    public Task getTaskFromDatabase(int taskId) {
        Task t = new Task();
        try {
            PreparedStatement stmt = ConnectionManager.getConnection().prepareStatement("SELECT * FROM " +
                    "heroku_7aba49c42d6c0f0.tasks WHERE task_id=?;");
            stmt.setInt(1, taskId);
            ResultSet rs = stmt.executeQuery();
            rs.next();
            String title = rs.getString("title");
            String description = rs.getString("description");
            String estimatedTime = rs.getString("estimated_time");
            String timeUsed = rs.getString("time_used");
            String status = rs.getString("status");
            int projectId = rs.getInt("project_id");
            int subprojectId = rs.getInt("subproject_id");
            String startDate = rs.getString("start_date");
            String endDate = rs.getString("end_date");


            t.setTaskTitle(title);
            t.setTaskDescription(description);
            t.setTaskEstimatedTime(estimatedTime);
            t.setTaskTimeUsed(timeUsed);
            t.setTaskStatus(status);
            t.setTaskProjectId(projectId);
            t.setTaskSubprojectId(subprojectId);
            t.setTaskStartDate(startDate);
            t.setTaskEndDate(endDate);
            t.setTaskId(taskId);

        } catch(SQLException e){
            System.out.println("Couldn't get task with id " + taskId + " from database");
            System.out.println(e.getMessage());
        }
        return t;
    }

    //Amanda
    public ArrayList<Task> getTaskConnectedToEmployeeAndProject(int employeeId, int projectId){
        ArrayList<Integer> tempTaskIds = new ArrayList<>();
        ArrayList<Integer> taskIds = new ArrayList<>();
        ArrayList<Task> taskObjects = new ArrayList<>();

        try {
            PreparedStatement stmt = conn.prepareStatement("SELECT task_id FROM " +
                    "heroku_7aba49c42d6c0f0.link_table WHERE employee_id=? AND project_id=?;");
            stmt.setInt(1,employeeId);
            stmt.setInt(2,projectId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()){
                int taskId = rs.getInt("task_id");
                if(taskId != 0){
                    tempTaskIds.add(taskId);

                    //Forhindrer gentagelser
                    if (!taskIds.contains(taskId)) {
                        taskIds.add(taskId);
                        Task tempTask = getTaskFromDatabase(taskId);
                        taskObjects.add(tempTask);
                    }

                }
            }


        } catch (SQLException e) {
            e.printStackTrace();
        }
        return taskObjects;
    }
}
