package com.example.eksamenvinter2021.Services;

import com.example.eksamenvinter2021.Models.Project;
import com.example.eksamenvinter2021.Resporsitories.ProjectRepo;
import com.example.eksamenvinter2021.Resporsitories.SubprojectRepo;

import java.util.ArrayList;

public class Test {

    public static void main(String[] args) {
        ProjectRepo pr = new ProjectRepo();
        SubprojectRepo spr = new SubprojectRepo();
        SubprojectService sps = new SubprojectService();
        ProjectService ps = new ProjectService();
        /*
        try {
            PreparedStatement stmt = JDBC.getConnection().prepareStatement("UPDATE `heroku_7aba49c42d6c0f0`." +
                    "`projects` SET `title` = 'Påske' WHERE (`project_id` = '25');");
            stmt.executeUpdate();
        } catch(SQLException e){
            System.out.println("Test failed");
            System.out.println(e.getMessage());
        }
*/
/*
        try {
            PreparedStatement stmt = JDBC.getConnection().prepareStatement("SELECT * FROM " +
                    "heroku_7aba49c42d6c0f0.projects WHERE project_id=?;");
            stmt.setInt(1, 15);
            ResultSet rs = stmt.executeQuery();
            rs.next();
            String title = rs.getString("title");
            String date = rs.getString("project_deadline");
            String status = rs.getString("status");
            double price = Double.parseDouble(rs.getString("base_price"));
            int customerId = rs.getInt("customer_id");
            Project p = new Project(title, date, status, price, customerId);

            //Kan ikke sætte total_price til null i condition, så det virker nok ikke, da databasen forventes at
            // returnere null og ikke 0. Jeg vil gerne teste dette, inden jeg finder på en mere kompliceret løsning.

            if (rs.getDouble("total_price") != 0){
                p.setTotalPrice(rs.getDouble("total_price"));
            }

            if (rs.getString("total_time") != null){
                p.setTotalPrice(rs.getInt("total_time"));
            }

            if (rs.getString("description") != null){
                p.setDescription(rs.getString("description"));
            }



            p.setProjectId(rs.getInt("project_id"));
            System.out.println("Succes");
            System.out.println(p);

        } catch(SQLException e){
            System.out.println("Couldn't get project with id " + 15 + " from database");
            System.out.println(e.getMessage());
        }

 */

        /*
        Date date = null;
        Project p = new Project("title", date, "Ikke påbegyndt", 1000, 5, 5);
        try {
            PreparedStatement stmt = JDBC.getConnection().prepareStatement
                    ("INSERT INTO heroku_7aba49c42d6c0f0.projects (`title`, `project_deadline`, " +
                            "`status`, `base_price`, `customer_id`, `manager_id`, `description`) " +
                            "VALUES (?, ?, ?, ?, ?, ?, ?);");
            stmt.setString(1, p.getProjectTitle());
            stmt.setDate(2, (Date) p.getProjectDeadline());
            stmt.setString(3, p.getStatus());
            stmt.setDouble(4, p.getBasePrice());
            stmt.setInt(5, p.getCustomerId());
            stmt.setInt(6, p.getManagerId());
            stmt.setString(7, p.getDescription());
            stmt.executeUpdate();
        } catch (Exception e) {
            System.out.println("Project could not be inserted into database");
            System.out.println(e.getMessage());
        }


        int id = 35;
        try {
            PreparedStatement stmt = JDBC.getConnection().prepareStatement
                    ("DELETE FROM `heroku_7aba49c42d6c0f0`.`projects` WHERE (`project_id` = '" + id + "');");
            stmt.executeUpdate();
        } catch (Exception e) {
            System.out.println("Couldn't delete project with id " + id + " from database");
            System.out.println(e.getMessage());
        }

          */
        System.out.println(pr.getProjectsInArray());
        //System.out.println(spr.showSubprojectLinkedToProject(15));
        //System.out.println(sps.showSubprojectLinkedToProject(15));
        //System.out.println(ps.getProjectObject(15));
        //pr.updateProjectInDatabase(Project p)
        //System.out.println(ps.getProjectObject(15));
    }
    }

