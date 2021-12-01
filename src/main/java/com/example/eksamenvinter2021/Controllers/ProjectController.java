package com.example.eksamenvinter2021.Controllers;

import com.example.eksamenvinter2021.Models.Project;
import com.example.eksamenvinter2021.Resporsitories.ProjectRepo;
import com.example.eksamenvinter2021.Services.ProjectService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;

import java.sql.SQLException;

@Controller
public class ProjectController {

    ProjectService ps = new ProjectService();
    ProjectRepo pr = new ProjectRepo();

    @GetMapping("/project/{thisProject}")//Path variables: /{}
    public String project(@PathVariable("thisProject") String thisProject, Model model) {
        //int id = Integer.parseInt(thisProject);
        model.addAttribute("Project", ps.showProject(15));
        return "project";
    }

    @GetMapping("/newProject")
    public String newProject() {
        return "newProject";
    }

    //Nu virker den!!!
    //Jeg har også problemer med at omsætte String til double, jeg måtte droppe date-formattet, og jeg kan
    // ikke bruge et if-statement til at løse mine problemer.
    @PostMapping("/createNewProject")
    public String createNewProject(WebRequest webr) throws SQLException {
        String title = webr.getParameter("project-title-input");
        String deadline = webr.getParameter("project-deadline-input");
        String basePrice = webr.getParameter("project-baseprice-input");
        String description = webr.getParameter("project-description-input");

        /*
        double basePrice = 0;
        try {
            basePrice = Double.parseDouble(price);
        } catch (Exception e) {
            System.out.println("Baseprice could not be converted from string to double");
            e.printStackTrace();
        }

         */


        //Create project-object
        Project currentProject = ps.createNewProjectObject(title, deadline, "Ikke påbegyndt", basePrice,
        5);

        currentProject.setDescription(description);

/*
        if (currentProject.getProjectTitle() != null){
            //Add project to DB
            pr.insertProjectIntoDatabase(currentProject);

            //Get project id
            int projectId = pr.getProjectId(title);
            currentProject.setProjectId(projectId);

        }
        System.out.println(currentProject.toString());

 */

   //Add project to DB
        pr.insertProjectIntoDatabase(currentProject);

        //Get project id
        int projectId = pr.getProjectId(title);
        currentProject.setProjectId(projectId);

        System.out.println(currentProject.toString());

        return "redirect:/newProject";
    }

}
