package com.example.eksamenvinter2021.Resporsitories;

import com.example.eksamenvinter2021.Models.Project;
import com.example.eksamenvinter2021.Services.SubprojectService;
import com.example.eksamenvinter2021.Utility.ConnectionManager;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

public class ProjectRepo {
    //Amanda Tolstrup Laursen

    SubprojectService sps = new SubprojectService();

    //Denne metode modtager et project-object og indsætter projektets attributter i databasen under projects-tabellen
    // via et insert-statement.
    public void insertProjectIntoDatabase(Project p) {
        try {
            PreparedStatement stmt = ConnectionManager.getConnection().prepareStatement
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

    //Denne metode henter et datasæt fra projects-tabellen i DB, ekstraherer projekt-attributterne og opretter
    // et projekt med disse.
    public Project getProjectFromDatabase(int id) {
        Project p = new Project();
        try {
            PreparedStatement stmt = ConnectionManager.getConnection().prepareStatement("SELECT * FROM " +
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

    //Denne metode sletter et projekt-datasæt fra projects-tabellen ud fra et bestemt project_id
    public void deleteProjectFromDatabase(int id) {
        try {
            PreparedStatement stmt = ConnectionManager.getConnection().prepareStatement
                    ("DELETE FROM `heroku_7aba49c42d6c0f0`.`projects` WHERE `project_id` = ?;");
            stmt.setInt(1, id);
                    //("DELETE FROM `heroku_7aba49c42d6c0f0`.`projects` WHERE (`project_id` = " + id + ");");
            stmt.executeUpdate();
        } catch (Exception e) {
            System.out.println("Couldn't delete project with id " + id + " from database");
            System.out.println(e.getMessage());
        }

    }

    //Denne metode modtager et project-object og opdaterer et datasæt i projects-tabellen med dette objects attributter.
    // Datasættet identificeres via project_id'et
    public void updateProjectInDatabase(Project p) {
        try {
            PreparedStatement stmt = ConnectionManager.getConnection().prepareStatement
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

    //Denne metode søger efter en bestemt titel i projects-tabellen og returnerer det tilhørende project_id
    public int getProjectIdFromTitle(String projectTitle) {
        try {
            PreparedStatement stmt = ConnectionManager.getConnection().prepareStatement("SELECT project_id FROM " +
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

    //Denne metode henter alle titler fra projects-tabellen i DB og returnerer dem som en ArrayList
    public ArrayList<String> getProjectNamesInArray() {
        ArrayList<String> projectNames = new ArrayList<>();
        try {
            PreparedStatement stmt = ConnectionManager.getConnection().prepareStatement("SELECT title FROM " +
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

    //Denne metode henter alle datasæt fra projects-tabellen, ekstraherer dataen og opretter et proejekt med
    // projekt-attributter baseret på denne data.
    public ArrayList<Project> getProjectsInArray() {
        ArrayList<Project> projectArray = new ArrayList<>();
        try {
            PreparedStatement stmt = ConnectionManager.getConnection().prepareStatement("SELECT * FROM " +
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
            PreparedStatement stmt = ConnectionManager.getConnection().prepareStatement("SELECT * FROM " +
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

                //if(!status.equals("Complete")){
                    if(startDate != null && !startDate.isEmpty() ) {
                        if (endDate != null && !endDate.isEmpty()) {

                            String newStartDate = startDate.replace("-", ",").replace("'", "");
                            String newEndDate = endDate.replace("-", ",").replace("'", "");

                            p.setStartDate(newStartDate);
                            p.setEndDate(newEndDate);
                            p.setAssociatedSubprojects(sps.showSubprojectLinkedToProject(projectId));


                            projectArray.add(p);
                        }
                    }
                //}
            }

        } catch (SQLException e) {
            System.out.println("Couldn't get projects from database");
            System.out.println(e.getMessage());
        }
        return projectArray;
    }


    //lavet af Christian
    public static Date findFinalEndTime(Project p) {
        //Udregner faktisk dato projekt færdigt
        //Find størst endDate ud af alle tabeller hvor project_id = ?
        Date maxDate = null;
        String finalEndDates = "";
        ArrayList<Date> dates = new ArrayList<>();
        try {
            PreparedStatement stmt = ConnectionManager.getConnection().prepareStatement(
                    "call find_deadline(?);");//Finder den seneste deadline i kategorierne project, subproject, task, subtask for project_id = p
            stmt.setInt(1, p.getProjectId());
            ResultSet rs = stmt.executeQuery();
            rs.next();
            Date projectEndDate = rs.getDate(1);
            Date subprojectEndDate = rs.getDate(2);
            Date taskEndDate = rs.getDate(3);
            Date subtaskEndDate = rs.getDate(4);

            dates.add(projectEndDate);
            dates.add(subprojectEndDate);
            dates.add(taskEndDate);
            dates.add(subtaskEndDate);
            maxDate = dates.stream().max(Date::compareTo).get();

            finalEndDates = "Project ends on " + projectEndDate +
                    "\nSubprojects ends on " + subprojectEndDate +
                    "\nTasks ends on " + taskEndDate +
                    "\nSubtasks ends on " + subtaskEndDate +
                    "\nThe final project end date is " + maxDate;

        } catch (Exception e) {
            System.out.println("Final project deadline could not be calculated");
            System.out.println(e.getMessage());
        }
        System.out.println(finalEndDates);
        return maxDate;
    }
}
