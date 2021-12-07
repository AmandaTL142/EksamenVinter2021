package com.example.eksamenvinter2021;

import com.example.eksamenvinter2021.Models.Project;
import com.example.eksamenvinter2021.Resporsitories.ProjectRepo;
import com.example.eksamenvinter2021.Utility.JDBC;
import net.bytebuddy.dynamic.scaffold.MethodRegistry;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
@SpringBootTest
public class TimeMethods {
    //Tidsmetoder

    @Test
    public static void main(String[] args) {
        ProjectRepo pr = new ProjectRepo();
        Project p = pr.getProjectFromDatabase(15);
        findFinalEndTime(p);
    }

    public static void findFinalEndTime(Project p) {

        ArrayList<Date> dates = new ArrayList<>();
        //Udregner faktisk dato projekt færdigt
        //Find størst endDate ud af alle tabeller hvor project_id = ?
        try {
            PreparedStatement stmt = JDBC.getConnection().prepareStatement(
                    "call find_deadline(?);");
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
            final Date maxDate = dates.stream().max(Date::compareTo).get();

            System.out.println("Project ends on " + projectEndDate +
                    "\nSubprojects ends on " + subprojectEndDate +
                    "\nTasks ends on " + taskEndDate +
                    "\nSubtasks ends on " + subtaskEndDate +
                    "\nThe final project end date is " + maxDate);


        } catch (Exception e) {
            System.out.println("Final project deadline could not be calculated");
            System.out.println(e.getMessage());
        }
    }

    //GetTotalHours
    /*Public int getTotalHours (project p) {
        calculates total hours spend by adding together all reported hours spent by employees
    }*/

    /*Public int hours worked employee (employee e, project p) {
        totals hours spend by one employee on one project
    }*/

    /*Public int hours spent on all projects (employee e) {
        Shows total hours used on all projects by an employee
    }*
    */

}
