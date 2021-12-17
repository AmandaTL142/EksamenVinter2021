package com.example.eksamenvinter2021.Resporsitories;

import com.example.eksamenvinter2021.Models.Subproject;
import com.example.eksamenvinter2021.Models.Task;
import com.example.eksamenvinter2021.Utility.ConnectionManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class SubprojectRepo {
    //Amanda Tolstrup Laursen

    //Denne metode modtager et subproject-object og indsætter projektets attributter i databasen i
    // subprojects-tabellen via et insert-statement.
    public void insertSubprojectIntoDatabase(Subproject sp) {
        try {
            PreparedStatement stmt = ConnectionManager.getConnection().prepareStatement
                    ("INSERT INTO `heroku_7aba49c42d6c0f0`.`subprojects` (`title`, `description`, " +
                            "`subproject_deadline`, `status`, `project_id`, `start_date`, `end_date`) " +
                            "VALUES (?, ?, ?, ?, ?, ?, ?);");
            stmt.setString(1, sp.getSubprojectTitle());
            stmt.setString(2, sp.getSubprojectDescription());
            stmt.setString(3, sp.getSubprojectDeadline());
            stmt.setString(4, sp.getSubprojectStatus());
            stmt.setInt(5, sp.getProjectId());
            stmt.setString(6, sp.getStartDate());
            stmt.setString(7, sp.getEndDate());
            stmt.executeUpdate();
        } catch (Exception e) {
            System.out.println("Subproject could not be inserted into database");
            System.out.println(e.getMessage());
        }
    }

    //Denne metode henter et datasæt fra subprojects-tabellen i DB, ekstraherer subprojekt-attributterne og opretter
    // et subprojekt med disse.
    public Subproject getSubprojectFromDatabase(int id) {
        Subproject sp = new Subproject();
        try {
            PreparedStatement stmt = ConnectionManager.getConnection().prepareStatement("SELECT * FROM " +
                    "heroku_7aba49c42d6c0f0.subprojects WHERE subproject_id=?;");
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            rs.next();
            int subprojectId = rs.getInt("subproject_id");
            String title = rs.getString("title");
            String description = rs.getString("description");
            String date = rs.getString("subproject_deadline");
            String status = rs.getString("status");
            int projectId = rs.getInt("project_id");
            String startDate = rs.getString("start_date");
            String endDate = rs.getString("end_date");
            sp = new Subproject(title, date, status, projectId);
            sp.setSubprojectId(subprojectId);

            if (description != null && description != ""){
                sp.setSubprojectDescription(description);
            }

            if (startDate != null && startDate != ""){
                sp.setStartDate(startDate);
            }

            if (endDate != null && endDate != ""){
                sp.setEndDate(endDate);
            }

        } catch(SQLException e){
            System.out.println("Couldn't get subproject with id " + id + " from database");
            System.out.println(e.getMessage());
        }
        return sp;
    }

    //Denne metode sletter et subproject-datasæt fra subprojects-tabellen ud fra et bestemt subproject_id
    public void deleteSubprojectFromDatabase(int id) {
        try {
            PreparedStatement stmt1 = ConnectionManager.getConnection().prepareStatement
                    ("DELETE FROM `heroku_7aba49c42d6c0f0`.`link_table` WHERE (`subproject_id` = '" + id + "');");
            stmt1.executeUpdate();

            PreparedStatement stmt2 = ConnectionManager.getConnection().prepareStatement
                    ("DELETE FROM `heroku_7aba49c42d6c0f0`.`subprojects` WHERE (`subproject_id` = '" + id + "');");
            stmt2.executeUpdate();
        } catch (Exception e) {
            System.out.println("Couldn't delete subproject with id " + id + " from database");
            System.out.println(e.getMessage());
        }

    }

    //Denne metode modtager et subproject-object og opdaterer et datasæt i subprojects-tabellen med dette
    // objects attributter. Datasættet identificeres via subproject_id'et
    public void updateSubprojectInDatabase(Subproject sp) {
        try {
            PreparedStatement stmt = ConnectionManager.getConnection().prepareStatement
                    ("UPDATE `heroku_7aba49c42d6c0f0`.`subprojects` SET `title` = ?, `description` = ?, " +
                            "`subproject_deadline` = ?, `status` = ?, `start_date` = ?, `end_date` = ? " +
                            "WHERE (`subproject_id` = ?);");
            stmt.setString(1, sp.getSubprojectTitle());
            stmt.setString(2, sp.getSubprojectDescription());
            stmt.setString(3, sp.getSubprojectDeadline());
            stmt.setString(4, sp.getSubprojectStatus());
            stmt.setString(5, sp.getStartDate());
            stmt.setString(6, sp.getEndDate());
            stmt.setInt(7, sp.getSubprojectId());
            stmt.executeUpdate();

        } catch (Exception e) {
            System.out.println("Couldn't update subproject with id " + sp.getSubprojectId() + " in database");
            System.out.println(e.getMessage());
        }

    }

    /* Denne skal ikke bruges, men den fungerer fint
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

     */

    //Denne modtage modtager et project-id og finder alle datasæt i subprojects-tabellen, der har dette project_id.
    // Derefter ekstraheres attributterne fra datasættene, der oprettes subproject-objekter, og disse tilføjes til
    // et array, der returneres.
    public ArrayList<Subproject> getSubprojectsLinkedToProject(int thisProjectId) {
        Subproject sp;
        ArrayList<Subproject> subprojects = new ArrayList<>();

        try {
            PreparedStatement stmt = ConnectionManager.getConnection().prepareStatement("SELECT * FROM " +
                    "heroku_7aba49c42d6c0f0.subprojects WHERE project_id=?;");
            stmt.setInt(1, thisProjectId);
            ResultSet rs = stmt.executeQuery();
            while(rs.next()){

                int subprojectId = rs.getInt("subproject_id");
                String title = rs.getString("title");
                String description = rs.getString("description");
                String deadline = rs.getString("subproject_deadline");
                String status = rs.getString("status");
                int projectId = rs.getInt("project_id");
                String startDate = rs.getString("start_date");
                String endDate = rs.getString("end_date");
                sp = new Subproject();
                sp.setSubprojectId(subprojectId);
                sp.setSubprojectTitle(title);
                sp.setSubprojectDeadline(deadline);
                sp.setSubprojectStatus(status);
                sp.setProjectId(projectId);
                sp.setStartDate(startDate);
                sp.setEndDate(endDate);
                sp.setSubprojectDescription(description);

                subprojects.add(sp);

            }


        } catch(SQLException e){
            System.out.println("Couldn't get subprojects for project with id " + thisProjectId + " from database");
            System.out.println(e.getMessage());
        }
        return subprojects;
    }

    //Denne metode finder alle det i subprojects-tabellen, hvor titlen er lig inputtet. subproject_id ekstraheres
    // fra datasættet, og dette returneres.
    public int getSubprojectIdByTitle(String title) {
        try {
            PreparedStatement stmt = ConnectionManager.getConnection().prepareStatement("SELECT * FROM " +
                    "heroku_7aba49c42d6c0f0.subprojects WHERE title=?;");
            stmt.setString(1, title);
            ResultSet rs = stmt.executeQuery();
            rs.next();
            int subprojectId = rs.getInt("subproject_id");

            return subprojectId;

        } catch(SQLException e){
            System.out.println("Couldn't get subproject with title " + title + " from database");
            System.out.println(e.getMessage());

            return 0;
        }
    }

}
