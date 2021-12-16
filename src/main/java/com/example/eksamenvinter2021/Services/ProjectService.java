package com.example.eksamenvinter2021.Services;

import com.example.eksamenvinter2021.Models.Project;
import com.example.eksamenvinter2021.Models.Task;
import com.example.eksamenvinter2021.Resporsitories.ProjectRepo;
import com.example.eksamenvinter2021.Resporsitories.SubprojectRepo;
import com.example.eksamenvinter2021.Resporsitories.TaskRepo;

import java.util.ArrayList;
import java.util.List;

public class ProjectService {
    //Amanda Tolstrup Laursen

    ProjectRepo pr = new ProjectRepo();
    SubprojectRepo spr = new SubprojectRepo();
    SubprojectService sps = new SubprojectService();
    TaskRepo tr = new TaskRepo();

    public Project createNewProjectObject(String title, String projectDeadline, String status, double basePrice,
                                 int customerId) {
        Project p = new Project(title, projectDeadline, status, basePrice, customerId);
        //pr.insertProjectIntoDatabase(p);

        return p;
    }

    public void insertProjectIntoDatabase(Project p) {
        pr.insertProjectIntoDatabase(p);
    }


    public void updateProject(Project p, String title, String projectDeadline, String status, double basePrice,
                              int costumerId, String description) {
        p.setProjectTitle(title);
        p.setProjectDeadline(projectDeadline);
        p.setStatus(status);
        p.setBasePrice(basePrice);
        p.setCustomerId(costumerId);
        p.setDescription(description);
        pr.updateProjectInDatabase(p);
    }

    public void updateProjectInDatabase(Project p) {
        pr.updateProjectInDatabase(p);
    }

    public Project getProjectObject(int projectId) {
        return pr.getProjectFromDatabase(projectId);
    }

    public void deleteProjectFromDatabase(int id) {
        pr.deleteProjectFromDatabase(id);
    }

    public List<Project> getAllProjectsAndSubprojects()
    {
        List<Project> allProjects = pr.getAllProjects();

        for (Project project : allProjects) {
            project.setAssociatedSubprojects(sps.showSubprojectLinkedToProject(project.getProjectId()));
        }
        return allProjects;
    }

    public int getProjectIdFromTitle(String projectTitle) {
        return pr.getProjectIdFromTitle(projectTitle);
    }

    public ArrayList<String> getProjectNamesInArray() {
        return pr.getProjectNamesInArray();
    }

    public ArrayList<Project> getProjectsInArray() {
        return pr.getProjectsInArray();
    }

    public ArrayList<Project> getAllProjects() {
        return pr.getAllProjects();
    }

    public boolean doesProjectHaveSubprojects(int projectId) {
        return pr.doesProjectHaveSubprojects(projectId);
    }



    /*

    Might be need for GANTT

    public List<Project> projectList;

    public List<Project> getAllProjects()
    {
        projectList = pr.getProjectsInArrayForGantt();
        return projectList;
    }

    public List<Project> getAllProjectsFromDataBase()
    {
        List<Project> allProjects = getAllProjects();

        for (Project p : allProjects) {
            p.setAssociatedSubprojects(subprojectService.getAllAssociatedSubprojectsAndAssociatedTasks(p.getProjectId()));
        }

        return allProjects;
    }
    */

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


