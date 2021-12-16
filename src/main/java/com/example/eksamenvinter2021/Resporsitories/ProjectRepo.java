package com.example.eksamenvinter2021.Resporsitories;

import com.example.eksamenvinter2021.Models.Project;
import com.example.eksamenvinter2021.Services.CustomerService;
import com.example.eksamenvinter2021.Services.SubprojectService;
import com.example.eksamenvinter2021.Utility.JDBC;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProjectRepo {
    //Amanda Tolstrup Laursen

    SubprojectService sps = new SubprojectService();

    //Testet i "test"
    public void insertProjectIntoDatabase(Project p) {
        try {
            PreparedStatement stmt = JDBC.getConnection().prepareStatement
                    ("INSERT INTO `heroku_7aba49c42d6c0f0`.`projects` " +
                            "(`title`, `project_deadline`, `status`, `base_price`, `customer_id`, " +
                            "`description`, `start_date`, `end_date`) VALUES " +
                            "(?,?,?,?,?,?,?,?);");
            stmt.setString(1, p.getProjectTitle());
            stmt.setString(2, p.getProjectDeadline());
            stmt.setString(3, p.getStatus());
            stmt.setDouble(4, p.getBasePrice());
            stmt.setInt(5, p.getCustomerId());
            stmt.setString(6, p.getDescription());
            stmt.setString(7, p.getStartDate());
            stmt.setString(8, p.getEndDate());
            stmt.executeUpdate();
        } catch (Exception e) {
            System.out.println("Project could not be inserted into database");
            System.out.println(e.getMessage());
        }
    }

    //Testet i "test", dele virker ikke
    public Project getProjectFromDatabase(int id) {
        Project p = new Project();
        try {
            PreparedStatement stmt = JDBC.getConnection().prepareStatement("SELECT * FROM " +
                    "heroku_7aba49c42d6c0f0.projects WHERE project_id=?;");
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            rs.next();
            String title = rs.getString("title");
            String deadline = rs.getString("project_deadline");
            String status = rs.getString("status");
            double price = Double.parseDouble(rs.getString("base_price"));
            int customerId = rs.getInt("customer_id");
            String description = rs.getString("description");
            String startDate = rs.getString("start_date");
            String endDate = rs.getString("end_date");
            int totalPrice = rs.getInt("total_price");
            int totalTime = rs.getInt("total_time");

            p = new Project(title, deadline, status, price, customerId);
            p.setProjectId(id);
            p.setDescription(description);
            p.setStartDate(startDate);
            p.setEndDate(endDate);
            p.setTotalPrice(totalPrice);
            p.setTotalTime(totalTime);

        } catch(SQLException e){
            System.out.println("Couldn't get project with id " + id + " from database");
            System.out.println(e.getMessage());
        }
        return p;
    }

    //Testet i "test"
    public void deleteProjectFromDatabase(int id) {
        try {
            PreparedStatement stmt = JDBC.getConnection().prepareStatement("DELETE FROM `heroku_7aba49c42d6c0f0`.`projects` WHERE `project_id` = ?;");
            stmt.setInt(1, id);
                    //("DELETE FROM `heroku_7aba49c42d6c0f0`.`projects` WHERE (`project_id` = " + id + ");");
            stmt.executeUpdate();
        } catch (Exception e) {
            System.out.println("Couldn't delete project with id " + id + " from database");
            System.out.println(e.getMessage());
        }

    }


    public void updateProjectInDatabase(Project p) {
        try {
            PreparedStatement stmt = JDBC.getConnection().prepareStatement
                    ("UPDATE `heroku_7aba49c42d6c0f0`.`projects` SET `title` = ?, `project_deadline`= ?, " +
                            "`status` = ?, `base_price` = ?, `customer_id` = ?, `description` = ?, " +
                            "`start_date` = ?, `end_date` = ? WHERE (`project_id` = ?);");
            stmt.setString(1, p.getProjectTitle());
            stmt.setString(2, p.getProjectDeadline());
            stmt.setString(3, p.getStatus());
            stmt.setDouble(4, p.getBasePrice());
            stmt.setInt(5, p.getCustomerId());
            stmt.setString(6, p.getDescription());
            stmt.setString(7, p.getStartDate());
            stmt.setString(8, p.getEndDate());
            stmt.setInt(9, p.getProjectId());
            stmt.executeUpdate();

        } catch (Exception e) {
            System.out.println("Couldn't update project with id " + p.getProjectId() + " in database");
            System.out.println(e.getMessage());
        }

    }


    public int getProjectIdFromTitle(String projectTitle) {
        try {
            PreparedStatement stmt = JDBC.getConnection().prepareStatement("SELECT project_id FROM " +
                    "heroku_7aba49c42d6c0f0.projects WHERE title=?;");
            stmt.setString(1, projectTitle);
            ResultSet rs = stmt.executeQuery();
            rs.next();
            int id = rs.getInt("project_id");
            return id;

        } catch(SQLException e){
            System.out.println("Couldn't get id for project with title " + projectTitle + " from database");
            System.out.println(e.getMessage());

            return 0;
        }
    }

    //Test denne
    public ArrayList<String> getProjectNamesInArray() {
        ArrayList<String> projectNames = new ArrayList<>();
        try {
            PreparedStatement stmt = JDBC.getConnection().prepareStatement("SELECT title FROM " +
                    "heroku_7aba49c42d6c0f0.projects;");
            ResultSet rs = stmt.executeQuery();

            while(rs.next()){
                projectNames.add(rs.getString("title"));
            }

        } catch(SQLException e){
            System.out.println("Couldn't get projectnames from database");
            System.out.println(e.getMessage());
        }
        return projectNames;
    }

    //Den her metode virker!
    public ArrayList<Project> getProjectsInArray() {
        ArrayList<Project> projectArray = new ArrayList<>();
        try {
            PreparedStatement stmt = JDBC.getConnection().prepareStatement("SELECT * FROM " +
                    "heroku_7aba49c42d6c0f0.projects;");
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                int projectId = rs.getInt("project_id");
                String title = rs.getString("title");
                String deadline = rs.getString("project_deadline");
                String status = rs.getString("status");
                double price = Double.parseDouble(rs.getString("base_price"));
                int customerId = rs.getInt("customer_id");
                String description = rs.getString("description");
                String startDate = rs.getString("start_date");
                String endDate = rs.getString("end_date");

                Project p = new Project(title, deadline, status, price, customerId);
                p.setProjectId(projectId);
                p.setDescription(description);
                p.setStartDate(startDate);
                p.setEndDate(endDate);

                projectArray.add(p);
            }

        } catch (SQLException e) {
            System.out.println("Couldn't get projects from database");
            System.out.println(e.getMessage());
        }
        return projectArray;
    }

    //Gets all projects in the database and excludes the once missing start- and end-Dates
    //CAS
    public ArrayList<Project> getAllProjects() {
        ArrayList<Project> projectArray = new ArrayList<>();
        try {
            PreparedStatement stmt = JDBC.getConnection().prepareStatement("SELECT * FROM " +
                    "heroku_7aba49c42d6c0f0.projects;");
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                int projectId = rs.getInt("project_id");
                String title = rs.getString("title");
                String status = rs.getString("status");
                int customerId = rs.getInt("customer_id");
                String startDate = rs.getString("start_date");
                String endDate = rs.getString("end_date");

                Project p = new Project(projectId, title, status, customerId, startDate, endDate);
                p.setProjectId(projectId);
                p.setProjectTitle(title);
                p.setStatus(status);
                p.setCustomerId(customerId);

                if(startDate != null && !startDate.isEmpty() ) {
                    if (endDate != null && !endDate.isEmpty()) {

                        String newStartDate = startDate.replace("-",",").replace("'","");
                        String newEndDate = endDate.replace("-",",").replace("'","");

                        p.setStartDate(newStartDate);
                        p.setEndDate(newEndDate);
                        p.setAssociatedSubprojects(sps.showSubprojectLinkedToProject(projectId));


                        projectArray.add(p);
                    }
                }
            }

        } catch (SQLException e) {
            System.out.println("Couldn't get projects from database");
            System.out.println(e.getMessage());
        }
        return projectArray;
    }

    public boolean doesProjectHaveSubprojects(int projectId) {
        try {
            PreparedStatement stmt = JDBC.getConnection().prepareStatement
                    ("SELECT project_id FROM heroku_7aba49c42d6c0f0.subprojects WHERE project_id=?;");
            stmt.setInt(1, projectId);
            ResultSet rs = stmt.executeQuery();
            return rs.next();

        } catch (Exception e) {
            System.out.println("Couldn't check subprojects connected to project with id " + projectId + " from database");
            System.out.println(e.getMessage());
        }

        return false;
    }
}
