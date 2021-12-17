package com.example.eksamenvinter2021.Controllers;

import com.example.eksamenvinter2021.Models.Employee;
import com.example.eksamenvinter2021.Models.Project;
import com.example.eksamenvinter2021.Models.Subproject;
import com.example.eksamenvinter2021.Services.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;

import javax.servlet.http.HttpSession;
import java.sql.SQLException;
import java.util.ArrayList;

@Controller
public class SubprojectController {
    //Amanda Tolstrup Laursen


    ProjectService ps = new ProjectService();
    EmployeeService es = new EmployeeService();
    SubprojectService sps = new SubprojectService();
    LinkTabelService lts = new LinkTabelService();
    LoginService ls = new LoginService();


    //Denne controller kan bruges til at vise et delprojekt, men den er ikke implementeret i programmets
    // nuværende version.
    /*
    @GetMapping("/subproject/{thisSubproject}")
    public String subproject(@PathVariable("thisSubproject") String thisSubproject, Model model) {
        int id = Integer.parseInt(thisSubproject);
        model.addAttribute("Subproject", sps.getSubprojectObject(id));
        model.addAttribute("Project", ps.getProjectObject((sps.getSubprojectObject(id)).getProjectId()));
        return "subproject_html/showSubproject";
    }
     */

    //Denne controller bruges til at vise siden, hvor brugeren kan oprette et nyt projekt
    @GetMapping("/newSubproject/{thisProjectId}")
    public String newSubproject(@PathVariable("thisProjectId") int thisProjectId, HttpSession session) {
        if (ls.notLoggedIn(session)) {
            return  "redirect:/";
        } else {
            Employee employee = (Employee) session.getAttribute("employee");
            if (employee.getRole().equals("MANAGER")){
                session.setAttribute("Project", ps.getProjectObject(thisProjectId));
                return "subproject_html/newSubproject";
            }
            else{
                return "error";
            }
        }
    }

    //Denne controller bruges til at hente input fra brugeren og bruge det til at oprette et delprojekt
    @PostMapping("/createNewSubproject")
    public String createNewSubproject(HttpSession session, WebRequest webr) {
        //model.addAttribute("projects", projectArray);
        String title = webr.getParameter("subproject-title-input");
        String deadline = webr.getParameter("subproject-deadline-input");
        String description = webr.getParameter("subproject-description-input");
        //String projectTitle = webr.getParameter("subproject-projecttitle-input");
        String status = webr.getParameter("subproject-status-input");

        //int projectId = pr.getProjectId(projectTitle);
        Project project = (Project) session.getAttribute("Project");
        int projectId = project.getProjectId();

        //Create subproject-object
        Subproject currentSubproject = sps.createNewSubproject(title, deadline, status, projectId);

        currentSubproject.setSubprojectDescription(description);

        //Add subproject to DB
        sps.insertSubprojectIntoDatabase(currentSubproject);

        //Get subproject ID
        int subprojectId = sps.getSubprojectIdByTitle(currentSubproject.getSubprojectTitle());

        //Insert link between  subproject and creator
        Employee employee = (Employee) session.getAttribute("employee");
        int employeeId = employee.getEmployeeId();
        lts.insertLinkTableWithEmployeeAndSubprojectIntoDatabase(employeeId, subprojectId, projectId);

        return "frontPage";
    }

    //Denne controller bruges til at slette et delprojekt via dets id
    @GetMapping("/deleteSubproject/{subprojectId}")
    public String deleteSubproject(@PathVariable String subprojectId, HttpSession session) throws SQLException {
        if (ls.notLoggedIn(session)) {
            return  "redirect:/";
        } else {
            Employee employee = (Employee) session.getAttribute("employee");
            if (employee.getRole().equals("MANAGER")){
                int id = Integer.parseInt(subprojectId);
                sps.deleteSubprojectFromDatabase(id);
                return "frontPage";
            }
            else{
                return "error";
            }
        }
    }

    //Denne controller bruges til at modtage et delprojekt-id fra programmet, oprette et tilhørende delprojekt
    // og gemme dette til senere brug
    @GetMapping("/editSubproject/{thisSubproject}")
    public String editSubrojectGetSubproject(@PathVariable("thisSubproject") int thisSubproject, Model model, HttpSession session) {
        if (ls.notLoggedIn(session)) {
            return  "redirect:/";
        } else {
            Employee employee = (Employee) session.getAttribute("employee");
            if (employee.getRole().equals("MANAGER")){

                Subproject subproject = (Subproject) session.getAttribute("Subproject");
                session.setAttribute("Subproject", sps.getSubprojectObject(thisSubproject));
                subproject = sps.getSubprojectObject(thisSubproject);
                //projectConnectedToSubproject= pr.getProjectFromDatabase(editThisSubproject.getProjectId());
                model.addAttribute("Subproject", sps.getSubprojectObject(thisSubproject));
                model.addAttribute("Project", ps.getProjectObject(subproject.getProjectId()));
                return "subproject_html/editSubproject";
            }
            else{
                return "error";
            }
        }
    }

    //Denne controller bruges til at hente input fra brugeren og bruge det til at opdatere et delprojekt
    @RequestMapping("/editSubprojectChanges")
    public String editSubprojectGetChanges(WebRequest webr, HttpSession session) {
        String title = webr.getParameter("subproject-title-input");
        String deadline = webr.getParameter("subproject-deadline-input");
        String description = webr.getParameter("subproject-description-input");
        String status = webr.getParameter("subproject-status-input");

        Subproject subproject = (Subproject) session.getAttribute("Subproject");

        if (title!="" && title!=null){
            subproject.setSubprojectTitle(title);
        }

        if (deadline!="" && deadline!=null){
            subproject.setSubprojectDeadline(deadline);
        }

        if (description!="" && description!=null){
            subproject.setSubprojectDescription(description);
        }

        subproject.setSubprojectStatus(status);

        //Update project in DB
        sps.updateSubprojectInDatabase(subproject);

        return "frontPage";
    }

    //Nedenstående kan bruges til at vise alle delprojekter forbundet til et projekt men den er ikke implementeret
    // i programmets nuværende version.
    /*
    @GetMapping("/showSubprojects/{thisProject}")
    public String subProjects(@PathVariable("thisProject") String thisProject, Model model) {
        int id = Integer.parseInt(thisProject);
        model.addAttribute("subprojects", sps.showSubprojectLinkedToProject(id));
        model.addAttribute("project", ps.getProjectObject(id));
        return "subproject_html/showSubprojects";
    }

     */


    //Denne controller bruges til at vise brugeren den side, hvor den kan tilføje en employee til et delprojekt
    @GetMapping("/addEmployeeToSubproject/{thisSubproject}")
    public String addEmployeeToSubproject1(@PathVariable("thisSubproject") int thisSubproject, Model model,
                                           HttpSession session) {
        if (ls.notLoggedIn(session)) {
            return  "redirect:/";
        } else {
            Employee employee = (Employee) session.getAttribute("employee");
            if (employee.getRole().equals("MANAGER")){
                ArrayList<Employee> allEmployees = es.getAllEmployeesFromDatabase();
                ArrayList<Employee> subprojectEmployees = lts.getEmployeesFromSubproject(thisSubproject);

                allEmployees.removeAll(subprojectEmployees);

                model.addAttribute("allEmployees", allEmployees);
                model.addAttribute("subprojectEmployees", subprojectEmployees);

                session.setAttribute("Subproject", sps.getSubprojectObject(thisSubproject));

                Subproject subproject = sps.getSubprojectObject(thisSubproject);
                model.addAttribute("subproject", subproject);
                return "subproject_html/addEmployeeToSubproject.html";
            }
            else{
                return "error";
            }
        }
    }

    //Denne controller bruges til at modtage brugerens valg af employee, der skal tilføjes
    @RequestMapping("/addEmployeeToSubprojectInput")
    public String addEmployeeToSubproject2(WebRequest webr, HttpSession session) {
        String employeeIdString = webr.getParameter("subproject-employeeId-input");
        int employeeId = Integer.parseInt(employeeIdString);
        Subproject subproject = (Subproject) session.getAttribute("Subproject");
        int subprojectId = subproject.getSubprojectId();
        int projectId = subproject.getProjectId();
        //Nedenstående er ikke testet
        lts.insertLinkTableWithEmployeeAndSubprojectIntoDatabase(employeeId, subprojectId, projectId);
        return "frontPage";
    }

    //Denne controller bruges til at tilgå en side, hvor man kan vælge, hvilken employee, der skal fjernes fra
    // et delprojekt
    @GetMapping("/removeEmployeeFromSubproject/{thisSubproject}")
    public String removeEmployeeFromSubproject(@PathVariable("thisSubproject") int thisSubproject, Model model, HttpSession session) {
        if (ls.notLoggedIn(session)) {
            return  "redirect:/";
        } else {
            Employee employee = (Employee) session.getAttribute("employee");
            if (employee.getRole().equals("MANAGER")){
                ArrayList<Employee> subprojectEmployees = lts.getEmployeesFromSubproject(thisSubproject);
                model.addAttribute("subprojectEmployees", subprojectEmployees);
                session.setAttribute("Subproject", sps.getSubprojectObject(thisSubproject));
                Subproject subproject = sps.getSubprojectObject(thisSubproject);
                model.addAttribute("subproject", subproject);
                return "subproject_html/removeEmployeeFromSubproject.html";
            }
            else{
                return "error";
            }
        }
    }


    //Denne controller bruges til at fjerne en valgt employee fra en liste ud fra dennes employeeId samt et delprojekt,
    // der blev gemt i session i ovenstående controller
    @GetMapping("/removeEmployeeSubproject/{employeeId}")
    public String removeEmployeeSubproject(@PathVariable("employeeId") int employeeId, HttpSession session){

        if (ls.notLoggedIn(session)) {
            return  "redirect:/";
        } else {
            //Checks if the user is a manager and thus allowed to access the site
            Employee employee = (Employee) session.getAttribute("employee");
            if (employee.getRole().equals("MANAGER")){
                Subproject subproject = (Subproject) session.getAttribute("Subproject");
                int subprojectId = subproject.getSubprojectId();
                lts.removeEmployeeFromSubproject(employeeId, subprojectId);
                return "frontPage";
            }
            else{
                return "error";
            }
        }
    }

}