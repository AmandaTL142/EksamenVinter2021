package com.example.eksamenvinter2021.Controllers;

import com.example.eksamenvinter2021.Models.Project;
import com.example.eksamenvinter2021.Resporsitories.CustomerRepo;
import com.example.eksamenvinter2021.Resporsitories.ProjectRepo;
import com.example.eksamenvinter2021.Services.ProjectService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;

@Controller
public class ProjectController {

    ProjectService ps = new ProjectService();
    ProjectRepo pr = new ProjectRepo();
    CustomerRepo cr = new CustomerRepo();
    Project editThisProject = new Project();

    //Denne virker
    @GetMapping("/project/{thisProject}")
    public String project(@PathVariable("thisProject") String thisProject, Model model) {
        int id = Integer.parseInt(thisProject);
        model.addAttribute("Project", ps.getProjectObject(id));
        return "showProject";
    }

    //Denne virker
    @GetMapping("/newProject")
    public String newProject() {
        return "newProject";
    }

    //Denne virker
    @PostMapping("/createNewProject")
    public String createNewProject(WebRequest webr) {
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

        return "redirect:/newProject";
    }

    //Denne virker
    @GetMapping("/editProject/{thisProject}")
    public String editProjectGetProject(@PathVariable("thisProject") String thisProject, Model model) {
        int id = Integer.parseInt(thisProject);
        editThisProject = ps.getProjectObject(id);
        model.addAttribute("Project", ps.getProjectObject(id));
        return "editProject";
    }

    //Denne virker
    @RequestMapping("/editProjectChanges")
    public String editProjectGetChanges(WebRequest webr) {
        String title = webr.getParameter("project-title-input");
        String deadline = webr.getParameter("project-deadline-input");
        String basePriceString = webr.getParameter("project-baseprice-input");
        String description = webr.getParameter("project-description-input");
        String costumerName = webr.getParameter("project-costumer-input");
        String status = webr.getParameter("project-status-input");


        if (title!="" && title!=null){
            editThisProject.setProjectTitle(title);
        }

        if (deadline!="" && deadline!=null){
            editThisProject.setProjectDeadline(deadline);
        }

        if (basePriceString!="" && basePriceString!=null){
            double basePrice = 0;
            try {
                basePrice = Double.parseDouble(basePriceString);
                editThisProject.setBasePrice(basePrice);
            } catch (Exception e) {
                System.out.println("Baseprice could not be converted from string to double. " +
                        "Check whether the input is a number.");
                e.printStackTrace();
            }
        }

        if (description!="" && description!=null){
            editThisProject.setDescription(description);
        }
        if (costumerName!="" && costumerName!=null){
            int customerId = cr.getCustomerIdFromDatabase(costumerName);
            editThisProject.setCustomerId(customerId);
        }

        editThisProject.setStatus(status);

        //Update project in DB
        pr.updateProjectInDatabase(editThisProject);

        return "confirmationPage";
    }

}
