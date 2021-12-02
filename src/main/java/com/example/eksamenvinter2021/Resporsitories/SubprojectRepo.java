package com.example.eksamenvinter2021.Resporsitories;

import com.example.eksamenvinter2021.Models.Subproject;
import com.example.eksamenvinter2021.Utility.JDBC;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class SubprojectRepo {

    public void insertSubprojectIntoDatabase(Subproject p) {
        try {
            PreparedStatement stmt = JDBC.getConnection().prepareStatement
                    ("INSERT INTO `heroku_7aba49c42d6c0f0`.`subprojects` (`title`, `description`, " +
                            "`subproject_deadline`, `status`, `project_id`) " +
                            "VALUES (?, ?, ?, ?, ?);");
            stmt.setString(1, p.getSubprojectTitle());
            stmt.setString(2, p.getSubprojectDescription());
            stmt.setString(3, p.getSubprojectDeadline());
            stmt.setString(4, p.getSubprojectStatus());
            stmt.setInt(5, p.getProjectId());
            stmt.executeUpdate();
        } catch (Exception e) {
            System.out.println("Subproject could not be inserted into database");
            System.out.println(e.getMessage());
        }
    }

    //Denne virker
    public Subproject getSubprojectFromDatabase(int id) {
        Subproject sp = new Subproject();
        try {
            PreparedStatement stmt = JDBC.getConnection().prepareStatement("SELECT * FROM " +
                    "heroku_7aba49c42d6c0f0.subprojects WHERE subproject_id=?;");
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            rs.next();
            String title = rs.getString("title");
            String date = rs.getString("subproject_deadline");
            String status = rs.getString("status");
            int projectId = rs.getInt("project_id");
            sp = new Subproject(title, date, status, projectId);

            //Kan ikke sætte total_price til null i condition, så det virker nok ikke, da databasen forventes at
            // returnere null og ikke 0. Jeg vil gerne teste dette, inden jeg finder på en mere kompliceret løsning.
            if (rs.getString("description") != null){
                sp.setSubprojectDescription(rs.getString("description"));
            }

            sp.setSubprojectId(rs.getInt("subproject_id"));

        } catch(SQLException e){
            System.out.println("Couldn't get subproject with id " + id + " from database");
            System.out.println(e.getMessage());
        }
        return sp;
    }

    public void deleteSubprojectFromDatabase(int id) {
        try {
            PreparedStatement stmt = JDBC.getConnection().prepareStatement
                    ("DELETE FROM `heroku_7aba49c42d6c0f0`.`subprojects` WHERE (`subproject_id` = '" + id + "');");
            stmt.executeUpdate();
        } catch (Exception e) {
            System.out.println("Couldn't delete subproject with id " + id + " from database");
            System.out.println(e.getMessage());
        }

    }

    //Skal jeg evt. lave if-statements til de attributter, der ikke er NN?
    public void updateSubprojectInDatabase(Subproject sp) {
        try {
            PreparedStatement stmt = JDBC.getConnection().prepareStatement
                    ("UPDATE `heroku_7aba49c42d6c0f0`.`subprojects` SET `title` = ?, " +
                            "`description` = ?, `subproject_deadline` = ?, " +
                            "`status` = ? WHERE (`subproject_id` = ?);");
            stmt.setString(1, sp.getSubprojectTitle());
            stmt.setString(2, sp.getSubprojectDescription());
            stmt.setString(3, sp.getSubprojectDeadline());
            stmt.setString(4, sp.getSubprojectStatus());
            stmt.setInt(5, sp.getSubprojectId());
            stmt.executeUpdate();

        } catch (Exception e) {
            System.out.println("Couldn't update subproject with id " + sp.getSubprojectId() + " in database");
            System.out.println(e.getMessage());
        }

    }

    public int getSubprojectId(String subprojectTitle) {
        try {
            PreparedStatement stmt = JDBC.getConnection().prepareStatement("SELECT subproject_id FROM " +
                    "heroku_7aba49c42d6c0f0.projects WHERE title=?;");
            stmt.setString(1, subprojectTitle);
            ResultSet rs = stmt.executeQuery();
            rs.next();
            int id = rs.getInt("subproject_id");
            return id;

        } catch(SQLException e){
            System.out.println("Couldn't get id for with title " + subprojectTitle + " from database");
            System.out.println(e.getMessage());
        }
        return 0;
    }

    public ArrayList<Subproject> showSubprojectLinkedToProject(int thisProjectId) {
        Subproject sp = new Subproject();
        ArrayList<Subproject> subprojects = new ArrayList<Subproject>();

        try {
            PreparedStatement stmt = JDBC.getConnection().prepareStatement("SELECT * FROM " +
                    "heroku_7aba49c42d6c0f0.subprojects WHERE project_id=?;");
            stmt.setInt(1, thisProjectId);
            ResultSet rs = stmt.executeQuery();
            while(rs.next()){
                String title = rs.getString("title");
                String date = rs.getString("subproject_deadline");
                String status = rs.getString("status");
                int projectId = rs.getInt("project_id");
                sp = new Subproject(title, date, status, projectId);
                //Kan ikke sætte total_price til null i condition, så det virker nok ikke, da databasen forventes at
                // returnere null og ikke 0. Jeg vil gerne teste dette, inden jeg finder på en mere kompliceret løsning.
                if (rs.getString("description") != null){
                    sp.setSubprojectDescription(rs.getString("description"));
                }

                sp.setSubprojectId(rs.getInt("subproject_id"));
                subprojects.add(sp);
            }


        } catch(SQLException e){
            System.out.println("Couldn't get subprojects for project with id " + thisProjectId + " from database");
            System.out.println(e.getMessage());
        }
        return subprojects;
    }

}
