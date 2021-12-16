package com.example.eksamenvinter2021.Services;

import com.example.eksamenvinter2021.Models.Project;
import com.example.eksamenvinter2021.Resporsitories.ProjectRepo;
import com.example.eksamenvinter2021.Resporsitories.SubprojectRepo;

import java.util.ArrayList;
import java.util.List;

public class ProjectService {
    //Amanda Tolstrup Laursen

    ProjectRepo pr = new ProjectRepo();
    SubprojectRepo spr = new SubprojectRepo();
    SubprojectService sps = new SubprojectService();

    public Project createNewProjectObject(String title, String projectDeadline, String status, double basePrice,
                                 int customerId) {
        Project p = new Project(title, projectDeadline, status, basePrice, customerId);

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

}


