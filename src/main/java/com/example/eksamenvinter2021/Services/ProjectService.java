package com.example.eksamenvinter2021.Services;

import com.example.eksamenvinter2021.Models.Project;
import com.example.eksamenvinter2021.Resporsitories.ProjectRepo;

import java.util.Date;

public class ProjectService {
    ProjectRepo pr = new ProjectRepo();

    public Project createNewProjectObject(String title, String projectDeadline, String status, String basePrice,
                                 int customerId) {
        Project p = new Project(title, projectDeadline, status, basePrice, customerId);
        //pr.insertProjectIntoDatabase(p);

        return p;
    }

    //Jeg har ikke integreret, hvordan metoden får ændringerne fra brugeren.
    public void updateProject(Project p, String title, String projectDeadline, String status, String basePrice,
                              int costumerId, String description) {
        p.setProjectTitle(title);
        p.setProjectDeadline(projectDeadline);
        p.setStatus(status);
        p.setBasePrice(basePrice);
        p.setCustomerId(costumerId);
        p.setDescription(description);
        pr.updateProjectInDatabase(p);
    }

    public Project showProject (int projectId) {
        return pr.getProjectFromDatabase(projectId);
    }

}
