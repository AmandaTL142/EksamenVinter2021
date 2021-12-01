package com.example.eksamenvinter2021.Controllers;

import com.example.eksamenvinter2021.Resporsitories.SubprojectRepo;
import com.example.eksamenvinter2021.Services.SubprojectService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;

import java.sql.SQLException;

@Controller
public class SubprojectController {

    SubprojectService sps = new SubprojectService();
    SubprojectRepo spr = new SubprojectRepo();

    @GetMapping("/subproject/{thisSubProject}")//Path variables: /{}
    public String project(@PathVariable("thisSubproject") String thisProject, Model model) {
        //int id = Integer.parseInt(thisProject);
        model.addAttribute("Subproject", sps.showSubproject(0));
        return "Subproject";
    }

    @GetMapping("/newSubproject")
    public String newProject() {
        return "newSubproject";
    }


    @RequestMapping(value = "/createNewSubproject", method = RequestMethod.GET)
    public String createNewProject(WebRequest webr) throws SQLException {
        String title = webr.getParameter("project-title-input");
        String deadline = webr.getParameter("project-deadline-input");
        String basePrice = webr.getParameter("project-baseprice-input");
        String description = webr.getParameter("project-description-input");

        //Det ser ud til, at der tilføjes et projekt, før input er modtaget. Hvordan forhindrer jeg dette?

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
        //Project currentProject = sps.createNewProjectObject(title, deadline, "Ikke påbegyndt", basePrice, 5);

        //currentProject.setDescription(description);



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
        //spr.insertSubprojectIntoDatabase(currentProject);

        //Get project id
        //int projectId = spr.getSubprojectId(title);
        //currentProject.setProjectId(projectId);

        //System.out.println(currentProject.toString());



        return "newProject";
    }

}
