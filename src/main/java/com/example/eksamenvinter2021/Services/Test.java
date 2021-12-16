package com.example.eksamenvinter2021.Services;

import com.example.eksamenvinter2021.Models.Project;
import com.example.eksamenvinter2021.Resporsitories.CustomerRepo;
import com.example.eksamenvinter2021.Resporsitories.LinkTableRepo;
import com.example.eksamenvinter2021.Resporsitories.ProjectRepo;
import com.example.eksamenvinter2021.Resporsitories.SubprojectRepo;

import java.text.ParseException;

public class Test {
    ProjectRepo pr = new ProjectRepo();
    SubprojectRepo spr = new SubprojectRepo();
    SubprojectService sps = new SubprojectService();
    ProjectService ps = new ProjectService();
    LinkTableRepo ltr = new LinkTableRepo();

    public Test() throws ParseException {
    }

    public static void main(String[] args) throws ParseException {
        ProjectRepo pr = new ProjectRepo();
        CustomerRepo cr = new CustomerRepo();
        //System.out.println(getTaskFromDatabase(5));
        Project project = pr.getProjectFromDatabase(15);
        //Date date = project.getDateinDateFormat();
        //System.out.println(date);
        //System.out.println(cr.returnCustomerNameFromId(5));
        System.out.println(pr.doesProjectHaveSubprojects(35));

    }

/*
    public static Task getTaskFromDatabase(int id) {
        Task t = new Task();
        try {
            PreparedStatement stmt = JDBC.getConnection().prepareStatement("SELECT * FROM " +
                    "tasks WHERE task_id=?;");
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            rs.next();
            int taskId = rs.getInt("task_id");
            String title = rs.getString("title");
            String description = rs.getString("description");
            String status = rs.getString("status");
            String startDate = rs.getString("start_date");
            String endDate = rs.getString("end_date");
            Double estimatedTime = rs.getDouble("estimated_time");
            Double timeUsed = rs.getDouble("time_used");
            t = new Task(taskId, title, description, estimatedTime, timeUsed, status, startDate, endDate);
            t.setTaskID(taskId);

            if (description != null && description != ""){
                t.setDescription(description);
            }

            if (startDate != null && startDate != ""){
                t.setStartDate(startDate);
            }

            if (endDate != null && endDate != ""){
                t.setEndDate(endDate);
            }

        } catch(SQLException e){
            System.out.println("Couldn't get task with id " + id + " from database");
            System.out.println(e.getMessage());
        }
        return t;
    }
        /*
        try {
            PreparedStatement stmt = JDBC.getConnection().prepareStatement("UPDATE `heroku_7aba49c42d6c0f0`." +
                    "`projects` SET `title` = 'Påske' WHERE (`project_id` = '25');");
            stmt.executeUpdate();
        } catch(SQLException e){
            System.out.println("Test failed");
            System.out.println(e.getMessage());
        }
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
        //System.out.println(pr.getProjectsInArray());
        //System.out.println(spr.showSubprojectLinkedToProject(15));
        //System.out.println(sps.showSubprojectLinkedToProject(15));
        //System.out.println(ps.getProjectObject(15));
        //Project p = new Project("01:02", "2012.12.12", "Ikke påbegyndt", 1000, 5);
        //p.setProjectId(125);
        //pr.updateProjectInDatabase(p);
        //System.out.println(ps.getProjectObject(15));

        /*
        Subproject sp = new Subproject("13:13", "2012.12.12", "Ikke påbegyndt", 15);
        System.out.println(sp.getStartDate());
        spr.insertSubprojectIntoDatabase(sp);

         */
        //ps.deleteProjectFromDatabase(135);
        //System.out.println(ltr.getActiveProjectsConnectedToEmployee(5));
        //System.out.println(ltr.getEmployeesFromSubproject(45));
        //ltr.removeEmployeeFromSubproject(1, 45);


}

