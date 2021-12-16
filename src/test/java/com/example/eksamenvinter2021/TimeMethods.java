package com.example.eksamenvinter2021;

import com.example.eksamenvinter2021.Models.Project;
import com.example.eksamenvinter2021.Models.Task;
import com.example.eksamenvinter2021.Resporsitories.ProjectRepo;
import com.example.eksamenvinter2021.Resporsitories.TaskRepo;
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
    //Forfatter: Christian Hundahl
    //Tidsmetoder
    TaskRepo tr = new TaskRepo();

    @Test
    public static void main(String[] args) {
        ProjectRepo pr = new ProjectRepo();
        Project p = pr.getProjectFromDatabase(15);
        findFinalEndTime(p);
    }

    public static Date findFinalEndTime(Project p) {

        Date maxDate = null;
        String finalEndDates = "";
        ArrayList<Date> dates = new ArrayList<>();
        //Udregner faktisk dato projekt færdigt
        //Find størst endDate ud af alle tabeller hvor project_id = ?
        try {
            PreparedStatement stmt = JDBC.getConnection().prepareStatement(
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

    //GetTotalHours
    public int totalTimeUsed (Project p) {
        //calculates total hours spend by adding together all reported hours spent by employees
        int totalHoursSpent = 0;

        for (Task t : tr.getAllTasksInProject(p.getProjectId())) {
            if (t.getStatus().equals("complete")) {
                totalHoursSpent += Integer.parseInt(t.getTimeUsed());
            }
        }
        return totalHoursSpent;
    }

    //GetEstimatedHours
    public int totalEstimatedHours(Project p) {
        //calculates endTimes reported by employees
        int totalHoursEstimated = 0;

        for (Task t : tr.getAllTasksInProject(p.getProjectId())) {
            totalHoursEstimated += Integer.parseInt(t.getEstimatedTime());
        }
       return totalHoursEstimated;
    }

    /*Public int hours worked employee (employee e, project p) {
        totals hours spend by one employee on one project
    }*/

    /*Public int hours spent on all projects (employee e) {
        Shows total hours used on all projects by an employee
    }*
    */
}