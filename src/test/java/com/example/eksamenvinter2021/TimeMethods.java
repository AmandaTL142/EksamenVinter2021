package com.example.eksamenvinter2021;

import com.example.eksamenvinter2021.Models.Project;
import com.example.eksamenvinter2021.Models.Task;
import com.example.eksamenvinter2021.Resporsitories.ProjectRepo;
import com.example.eksamenvinter2021.Resporsitories.TaskRepo;
import com.example.eksamenvinter2021.Utility.ConnectionManager;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
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

    //Udregner total tidsforbrug på projekt ved at addere alle færdiggjorte 'task' rapporteret af medarbejdere
    public int totalTimeUsed (Project p) {
        int totalHoursSpent = 0;
        for (Task t : tr.getAllTasksInProject(p.getProjectId())) {
            if (t.getTaskStatus().equals("complete")) {
                totalHoursSpent += Integer.parseInt(t.getTaskTimeUsed());
            }
        }
        return totalHoursSpent;
    }

    //Udregner det tidsforbrug medarbejderne selv estimerer
    public int totalEstimatedHours(Project p) {
        int totalHoursEstimated = 0;

        for (Task t : tr.getAllTasksInProject(p.getProjectId())) {
            totalHoursEstimated += Integer.parseInt(t.getTaskEstimatedTime());
        }
       return totalHoursEstimated;
    }
}