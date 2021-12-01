package com.example.eksamenvinter2021.Controllers;

import com.example.eksamenvinter2021.Models.Project;
import com.example.eksamenvinter2021.Resporsitories.CustomerRepo;
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
    CustomerRepo cr = new CustomerRepo();

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
    //Jeg har også problemer med at omsætte String til double, jeg måtte droppe date-formattet.
    @PostMapping("/createNewProject")
    public String createNewProject(WebRequest webr) throws SQLException {
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

   //Add project to DB
        pr.insertProjectIntoDatabase(currentProject);

        //Get project id
        int projectId = pr.getProjectId(title);
        currentProject.setProjectId(projectId);

        System.out.println(currentProject.toString());

        return "redirect:/newProject";
    }

}
