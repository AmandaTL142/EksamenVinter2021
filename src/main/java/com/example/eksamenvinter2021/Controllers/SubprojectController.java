package com.example.eksamenvinter2021.Controllers;

import com.example.eksamenvinter2021.Models.Project;
import com.example.eksamenvinter2021.Models.Subproject;
import com.example.eksamenvinter2021.Resporsitories.CustomerRepo;
import com.example.eksamenvinter2021.Resporsitories.ProjectRepo;
import com.example.eksamenvinter2021.Resporsitories.SubprojectRepo;
import com.example.eksamenvinter2021.Services.ProjectService;
import com.example.eksamenvinter2021.Services.SubprojectService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;

import java.sql.SQLException;
import java.util.Date;

@Controller
public class SubprojectController {

    ProjectService ps = new ProjectService();
    ProjectRepo pr = new ProjectRepo();
    Subproject sp = new Subproject();
    SubprojectService sps = new SubprojectService();
    SubprojectRepo spr = new SubprojectRepo();
    CustomerRepo cr = new CustomerRepo();


    //Denne virker
    @GetMapping("/subproject/{thisSubproject}")
    public String project(@PathVariable("thisSubproject") String thisSubproject, Model model) {
        int id = Integer.parseInt(thisSubproject);
        model.addAttribute("Subproject", sps.showSubproject(id));
        model.addAttribute("Project", ps.showProject((sps.showSubproject(id)).getProjectId()));
        return "showSubproject";
    }


    @GetMapping("/newSubproject")
    public String newSubproject() {
        return "newSubproject";
    }

    @PostMapping("/createNewSubproject")
    public String createNewSubproject(WebRequest webr) {
        String title = webr.getParameter("subproject-title-input");
        String deadline = webr.getParameter("subproject-deadline-input");
        String description = webr.getParameter("subproject-description-input");
        String projectName = webr.getParameter("subproject-projectname-input");
        String status = webr.getParameter("subproject-status-input");


        int projectId = pr.getProjectId(projectName);


        //Create subproject-object
        Subproject currentSubproject = new Subproject(title, deadline, status, projectId);

        currentSubproject.setSubprojectDescription(description);

        //Add subproject to DB
        spr.insertSubprojectIntoDatabase(currentSubproject);

        return "redirect:/newSubproject";
    }

/*
    @GetMapping("/editProject")
    public String editProject() {
        return "editProject";
    }

    @RequestMapping("/editProjectId/{thisProject}")
    public String editProject(@PathVariable("thisProject") String thisProject, Model model, WebRequest webr) {
        int id = Integer.parseInt(thisProject);
        model.addAttribute("Project", ps.showProject(id));
        String title = webr.getParameter("project-title-input");
        String deadline = webr.getParameter("project-deadline-input");
        String basePriceString = webr.getParameter("project-baseprice-input");
        String description = webr.getParameter("project-description-input");
        String costumerName = webr.getParameter("project-costumer-input");
        String status = webr.getParameter("project-status-input");

        double basePrice = 0;
        try {
            basePrice = Double.parseDouble(basePriceString);
        } catch (Exception e) {
            System.out.println("Baseprice could not be converted from string to double. " +
                    "Check whether the input is a number.");
            e.printStackTrace();
        }

        int customerId = cr.getCustomerIdFromDatabase(costumerName);

        //Create project-object
        Project currentProject = ps.createNewProjectObject(title, deadline, status, basePrice, customerId);

        currentProject.setDescription(description);

        //Update project in DB
        pr.updateProjectInDatabase(currentProject);

        return "redirect:/editProject";
    }

     */

}
