package com.example.eksamenvinter2021.Controllers;

import com.example.eksamenvinter2021.Models.Employee;
import com.example.eksamenvinter2021.Models.Project;
import com.example.eksamenvinter2021.Models.Subproject;
import com.example.eksamenvinter2021.Resporsitories.EmployeeRepo;
import com.example.eksamenvinter2021.Resporsitories.LinkTableRepo;
import com.example.eksamenvinter2021.Resporsitories.ProjectRepo;
import com.example.eksamenvinter2021.Resporsitories.SubprojectRepo;
import com.example.eksamenvinter2021.Services.LoginService;
import com.example.eksamenvinter2021.Services.ProjectService;
import com.example.eksamenvinter2021.Services.SubprojectService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;

import javax.servlet.http.HttpSession;
import java.sql.SQLException;
import java.util.ArrayList;

@Controller
public class SubprojectController {


    ProjectService ps = new ProjectService();
    ProjectRepo pr = new ProjectRepo();
    Subproject sp = new Subproject();
    SubprojectService sps = new SubprojectService();
    SubprojectRepo spr = new SubprojectRepo();
    LinkTableRepo ltr = new LinkTableRepo();
    EmployeeRepo er = new EmployeeRepo();
    Subproject editThisSubproject = new Subproject();
    Project projectConnectedToSubproject = new Project();
    LoginService ls = new LoginService();

    //ArrayList<String> projectNames = pr.getProjectNamesInArray();
    ArrayList<Project> projectArray = pr.getProjectsInArray();


    //Skal denne bruges?
    @GetMapping("/subproject/{thisSubproject}")
    public String subproject(@PathVariable("thisSubproject") String thisSubproject, Model model) {
        int id = Integer.parseInt(thisSubproject);
        model.addAttribute("Subproject", sps.getSubprojectObject(id));
        model.addAttribute("Project", ps.getProjectObject((sps.getSubprojectObject(id)).getProjectId()));
        return "subproject_html/showSubproject";
    }


    @GetMapping("/newSubproject/{thisProjectId}")
    public String newSubproject(@PathVariable("thisProjectId") int thisProjectId, HttpSession session) {
        if (ls.notLoggedIn(session)) {
            return  "redirect:/";
        } else {
            projectConnectedToSubproject = pr.getProjectFromDatabase(thisProjectId);
            return "subproject_html/newSubproject";
        }
    }


    @PostMapping("/createNewSubproject")
    public String createNewSubproject(HttpSession session, WebRequest webr) {
        //model.addAttribute("projects", projectArray);
        String title = webr.getParameter("subproject-title-input");
        String deadline = webr.getParameter("subproject-deadline-input");
        String description = webr.getParameter("subproject-description-input");
        //String projectTitle = webr.getParameter("subproject-projecttitle-input");
        String status = webr.getParameter("subproject-status-input");

        //int projectId = pr.getProjectId(projectTitle);
        int projectId = projectConnectedToSubproject.getProjectId();

        //Create subproject-object
        Subproject currentSubproject = sps.createNewSubproject(title, deadline, status, projectId);

        currentSubproject.setSubprojectDescription(description);

        //Add subproject to DB
        spr.insertSubprojectIntoDatabase(currentSubproject);

        //Get subproject ID
        int subprojectId = spr.getSubprojectIdByTitle(currentSubproject.getSubprojectTitle());

        //Insert link between  subproject and creator
        Employee employee = (Employee) session.getAttribute("employee");
        int employeeId = employee.getEmployeeId();
        ltr.insertLinkTableWithEmployeeAndSubprojectIntoDatabase(employeeId, subprojectId, projectId);

        return "frontPage";
    }


    @GetMapping("/deleteSubproject/{subprojectId}")
    public String deleteSubproject(@PathVariable String subprojectId, HttpSession session) throws SQLException {
        if (ls.notLoggedIn(session)) {
            return  "redirect:/";
        } else {
            int id = Integer.parseInt(subprojectId);
            sps.deleteSubprojectFromDatabase(id);
            return "frontPage";
        }
    }


    @GetMapping("/editSubproject/{thisSubproject}")
    public String editSubrojectGetSubproject(@PathVariable("thisSubproject") String thisSubproject, Model model, HttpSession session) {
        if (ls.notLoggedIn(session)) {
            return  "redirect:/";
        } else {
            int id = Integer.parseInt(thisSubproject);
            editThisSubproject = sps.getSubprojectObject(id);
            //projectConnectedToSubproject= pr.getProjectFromDatabase(editThisSubproject.getProjectId());
            model.addAttribute("Subproject", sps.getSubprojectObject(id));
            model.addAttribute("Project", pr.getProjectFromDatabase(editThisSubproject.getProjectId()));
            return "subproject_html/editSubproject";
        }
    }

    //Denne virker
    @RequestMapping("/editSubprojectChanges")
    public String editSubprojectGetChanges(WebRequest webr) {
        String title = webr.getParameter("subproject-title-input");
        String deadline = webr.getParameter("subproject-deadline-input");
        String description = webr.getParameter("subproject-description-input");
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

        return "frontPage";
    }

    //Skal denne bruges?
    @GetMapping("/showSubprojects/{thisProject}")
    public String subProjects(@PathVariable("thisProject") String thisProject, Model model) {
        int id = Integer.parseInt(thisProject);
        model.addAttribute("subprojects", sps.showSubprojectLinkedToProject(id));
        model.addAttribute("project", ps.getProjectObject(id));
        return "subproject_html/showSubprojects";
    }


    @GetMapping("/addEmployeeToSubproject/{thisSubproject}")
    public String addEmployeeToSubproject1(@PathVariable("thisSubproject") int thisSubproject, Model model, HttpSession session) {
        if (ls.notLoggedIn(session)) {
            return  "redirect:/";
        } else {
            ArrayList<Employee> allEmployees = er.getAllEmployeesFromDatabase();
            ArrayList<Employee> subprojectEmployees = ltr.getEmployeesFromSubproject(thisSubproject);

            allEmployees.removeAll(subprojectEmployees);

            model.addAttribute("allEmployees", allEmployees);
            model.addAttribute("subprojectEmployees", subprojectEmployees);
            editThisSubproject = sps.getSubprojectObject(thisSubproject);
            model.addAttribute("subproject", editThisSubproject);
            return "subproject_html/addEmployeeToSubproject.html";
        }
    }


    @RequestMapping("/addEmployeeToSubprojectInput")
    public String addEmployeeToSubproject2(WebRequest webr) {
        String employeeIdString = webr.getParameter("subproject-employeeId-input");
        int employeeId = Integer.parseInt(employeeIdString);
        int subprojectId = editThisSubproject.getSubprojectId();
        int projectId = editThisSubproject.getProjectId();
        //Nedenstående er ikke testet
        ltr.insertLinkTableWithEmployeeAndSubprojectIntoDatabase(employeeId, subprojectId, projectId);
        return "frontPage";
    }


    @GetMapping("/removeEmployeeFromSubproject/{thisSubproject}")
    public String removeEmployeeFromSubproject(@PathVariable("thisSubproject") int thisSubproject, Model model, HttpSession session) {
        if (ls.notLoggedIn(session)) {
            return  "redirect:/";
        } else {
            ArrayList<Employee> subprojectEmployees = ltr.getEmployeesFromSubproject(thisSubproject);
            model.addAttribute("subprojectEmployees", subprojectEmployees);
            editThisSubproject = sps.getSubprojectObject(thisSubproject);
            model.addAttribute("subproject", editThisSubproject);
            return "subproject_html/removeEmployeeFromSubproject.html";
        }
    }


    //Nedenstående virker ikke
    @PostMapping("/removeEmployee/{employeeId}")
    public String removeEmployee(@PathVariable("employeeId") int employeeId, HttpSession session){

        if (ls.notLoggedIn(session)) {
            return  "redirect:/";
        } else {
            //Checks if the user is a manager and thus allowed to access the site
            Employee employee = (Employee) session.getAttribute("employee");
            if (employee.getRole().equals("MANAGER")){
                int subprojectId = editThisSubproject.getSubprojectId();
                ltr.removeEmployeeFromSubproject(employeeId, subprojectId);
                return "frontPage";
            }
            else{
                return "error";
            }
        }
    }



}