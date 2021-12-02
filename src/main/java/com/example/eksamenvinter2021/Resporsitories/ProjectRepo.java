package com.example.eksamenvinter2021.Resporsitories;

import com.example.eksamenvinter2021.Models.Project;
import com.example.eksamenvinter2021.Utility.JDBC;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ProjectRepo {

    //Testet i "test"
    public void insertProjectIntoDatabase(Project p) {
        try {
            PreparedStatement stmt = JDBC.getConnection().prepareStatement
                    ("INSERT INTO heroku_7aba49c42d6c0f0.projects (`title`, `project_deadline`, " +
                            "`status`, `base_price`, `customer_id`, `description`) " +
                            "VALUES (?, ?, ?, ?, ?, ?);");
            stmt.setString(1, p.getProjectTitle());
            stmt.setString(2, p.getProjectDeadline());
            stmt.setString(3, p.getStatus());
            stmt.setDouble(4, p.getBasePrice());
            stmt.setInt(5, p.getCustomerId());
            stmt.setString(6, p.getDescription());
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
            String date = rs.getString("project_deadline");
            String status = rs.getString("status");
            double price = Double.parseDouble(rs.getString("base_price"));
            int customerId = rs.getInt("customer_id");
            p = new Project(title, date, status, price, customerId);

            //Kan ikke sætte total_price til null i condition, så det virker nok ikke, da databasen forventes at
            // returnere null og ikke 0. Jeg vil gerne teste dette, inden jeg finder på en mere kompliceret løsning.

            /*
            //Virker ikke
            if (rs.getDouble("total_price") != 0){
                p.setTotalPrice(rs.getDouble("total_price"));
            }

            //Virker ikke
            if (rs.getString("total_time") != null){
                p.setTotalPrice(rs.getInt("total_time"));
            }

             */

            //Virker!
            if (rs.getString("description") != null){
                p.setDescription(rs.getString("description"));
            }

            p.setProjectId(rs.getInt("project_id"));

        } catch(SQLException e){
            System.out.println("Couldn't get project with id " + id + " from database");
            System.out.println(e.getMessage());
        }
        return p;
    }

    //Testet i "test"
    public void deleteProjectFromDatabase(int id) {
        try {
            PreparedStatement stmt = JDBC.getConnection().prepareStatement
                    ("DELETE FROM `heroku_7aba49c42d6c0f0`.`projects` WHERE (`project_id` = '" + id + "');");
            stmt.executeUpdate();
        } catch (Exception e) {
            System.out.println("Couldn't delete project with id " + id + " from database");
            System.out.println(e.getMessage());
        }

    }

    //Skal jeg evt. lave if-statements til de attributter, der ikke er NN?
    public void updateProjectInDatabase(Project p) {
        try {
            PreparedStatement stmt = JDBC.getConnection().prepareStatement
                    ("UPDATE `heroku_7aba49c42d6c0f0`.`projects` SET `title` = ?, `project_deadline` = ?, " +
                            "`status` = ?, `base_price` = ?, `total_price` = ?, `total_time` = ?, " +
                            "`customer_id` = ?, `description` = ? WHERE (`project_id` = ?;");
            stmt.setString(1, p.getProjectTitle());
            stmt.setString(2, p.getProjectDeadline());
            stmt.setString(3, p.getStatus());
            stmt.setDouble(4, p.getBasePrice());
            stmt.setDouble(5, p.getTotalPrice());
            stmt.setInt(6, p.getTotalTime());
            stmt.setInt(7, p.getCustomerId());
            stmt.setString(8, p.getDescription());
            stmt.setInt(9, p.getProjectId());
            stmt.executeUpdate();

        } catch (Exception e) {
            System.out.println("Couldn't update project with id " + p.getProjectId() + " in database");
            System.out.println(e.getMessage());
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

    public ArrayList<Project> getProjectsInArray() {
        ArrayList<Project> projectArray = new ArrayList<>();
        try {
            PreparedStatement stmt = JDBC.getConnection().prepareStatement("SELECT * FROM " +
                    "heroku_7aba49c42d6c0f0.projects WHERE project_id=15;");
            ResultSet rs = stmt.executeQuery();
            rs.next();
            //while(rs.next());
            //{
                String title = rs.getString("title");
                String date = rs.getString("project_deadline");
                String status = rs.getString("status");
                double price = Double.parseDouble(rs.getString("base_price"));
                int customerId = rs.getInt("customer_id");
                Project p = new Project(title, date, status, price, customerId);
                projectArray.add(p);
            //}

        } catch(SQLException e){
            System.out.println("Couldn't get projects from database");
            System.out.println(e.getMessage());
        }
        return projectArray;
    }

}
