package com.example.eksamenvinter2021.Controllers;

import com.example.eksamenvinter2021.Models.Project;
import com.example.eksamenvinter2021.Models.Subproject;
import com.example.eksamenvinter2021.Resporsitories.ProjectRepo;
import com.example.eksamenvinter2021.Resporsitories.SubprojectRepo;
import com.example.eksamenvinter2021.Services.ProjectService;
import com.example.eksamenvinter2021.Services.SubprojectService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;

import java.sql.SQLException;
import java.util.ArrayList;

@Controller
public class SubprojectController {


    ProjectService ps = new ProjectService();
    ProjectRepo pr = new ProjectRepo();
    Subproject sp = new Subproject();
    SubprojectService sps = new SubprojectService();
    SubprojectRepo spr = new SubprojectRepo();
    Subproject editThisSubproject = new Subproject();
    Project projectConnectedToSubproject = new Project();

    //ArrayList<String> projectNames = pr.getProjectNamesInArray();
    ArrayList<Project> projectArray = pr.getProjectsInArray();



    //Denne virker
    @GetMapping("/subproject/{thisSubproject}")
    public String subproject(@PathVariable("thisSubproject") String thisSubproject, Model model) {
        int id = Integer.parseInt(thisSubproject);
        model.addAttribute("Subproject", sps.getSubprojectObject(id));
        model.addAttribute("Project", ps.getProjectObject((sps.getSubprojectObject(id)).getProjectId()));
        return "showSubproject";
    }

    //Denne virker
    @GetMapping("/newSubproject")
    public String newSubproject(Model model) {
        model.addAttribute("projects", projectArray);
        return "newSubproject";
    }

    //Denne virker i det basale, men jeg er ved at udvide den, så man kan vælge mellem de eksisterende projekter.
    @PostMapping("/createNewSubproject")
    public String createNewSubproject(WebRequest webr) {
        //model.addAttribute("projects", projectArray);
        String title = webr.getParameter("subproject-title-input");
        String deadline = webr.getParameter("subproject-deadline-input");
        String description = webr.getParameter("subproject-description-input");
        String projectName = webr.getParameter("subproject-projectname-input");
        String status = webr.getParameter("subproject-status-input");

        //int projectId = pr.getProjectId(projectName);


        //Create subproject-object
        Subproject currentSubproject = sps.createNewSubproject(title, deadline, status, 5);

        currentSubproject.setSubprojectDescription(description);

        //Add subproject to DB
        spr.insertSubprojectIntoDatabase(currentSubproject);

        return "redirect:/newSubproject";
    }

    //Denne virker
    @GetMapping("/deleteSubtask/{subtaskId}")
    public String deleteSubtask(@PathVariable String subtaskId) throws SQLException {
        int id = Integer.parseInt(subtaskId);
        sps.deleteSubprojectFromDatabase(id);
        return "confirmationPage";
    }

    //Denne virker
    @GetMapping("/editSubproject/{thisSubproject}")
    public String editSubrojectGetSubproject(@PathVariable("thisSubproject") String thisSubproject, Model model) {
        int id = Integer.parseInt(thisSubproject);
        editThisSubproject = sps.getSubprojectObject(id);
        //projectConnectedToSubproject= pr.getProjectFromDatabase(editThisSubproject.getProjectId());
        model.addAttribute("Subproject", sps.getSubprojectObject(id));
        model.addAttribute("Project", pr.getProjectFromDatabase(editThisSubproject.getProjectId()));
        return "editSubproject";
    }

    //Denne virker
    @RequestMapping("/editSubprojectChanges")
    public String editSubprojectGetChanges(WebRequest webr) {
        String title = webr.getParameter("subproject-title-input");
        String deadline = webr.getParameter("subproject-deadline-input");
        String description = webr.getParameter("subproject-description-input");
        //String projectName = webr.getParameter("subproject-project-input");
        String status = webr.getParameter("subproject-status-input");

        if (title!="" && title!=null){
            editThisSubproject.setSubprojectTitle(title);
        }

        if (deadline!="" && deadline!=null){
            editThisSubproject.setSubprojectDeadline(deadline);
        }

        if (description!="" && description!=null){
            editThisSubproject.setSubprojectDescription(description);
        }

        editThisSubproject.setSubprojectStatus(status);

        //Update project in DB
        spr.updateSubprojectInDatabase(editThisSubproject);

        return "confirmationPage";
    }

    //Denne virker
    @GetMapping("/showSubprojects/{thisProject}")
    public String subProjects(@PathVariable("thisProject") String thisProject, Model model) {
        int id = Integer.parseInt(thisProject);
        model.addAttribute("subprojects", sps.showSubprojectLinkedToProject(id));
        model.addAttribute("project", ps.getProjectObject(id));
        return "showSubprojects";
    }

}