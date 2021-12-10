package com.example.eksamenvinter2021.Controllers;

import com.example.eksamenvinter2021.Models.Employee;
import com.example.eksamenvinter2021.Models.Project;
import com.example.eksamenvinter2021.Models.Subproject;
import com.example.eksamenvinter2021.Resporsitories.CustomerRepo;
import com.example.eksamenvinter2021.Resporsitories.EmployeeRepo;
import com.example.eksamenvinter2021.Resporsitories.LinkTabelRepo;
import com.example.eksamenvinter2021.Resporsitories.ProjectRepo;
import com.example.eksamenvinter2021.Services.EmployeeService;
import com.example.eksamenvinter2021.Services.LoginService;
import com.example.eksamenvinter2021.Services.ProjectService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;

@Controller
public class ProjectController {

    ProjectService ps = new ProjectService();
    ProjectRepo pr = new ProjectRepo();
    CustomerRepo cr = new CustomerRepo();
    LinkTabelRepo ltr = new LinkTabelRepo();
    Project editThisProject = new Project();
    EmployeeRepo er = new EmployeeRepo();
    EmployeeService es = new EmployeeService();
    LoginService ls = new LoginService();

    //Denne virker
    @GetMapping("/project/{thisProject}")
    public String project(@PathVariable("thisProject") int thisProject, Model model, HttpSession session) {
        if (ls.notLoggedIn(session)) {
            return  "redirect:/";
        } else {
            //int id = Integer.parseInt(thisProject);
            model.addAttribute("Project", ps.getProjectObject(thisProject));
            return "project_html/showProject";
        }
    }

    //Denne virker
    @GetMapping("/newProject")
    public String newProject(HttpSession session) {
        if (ls.notLoggedIn(session)) {
            return  "redirect:/";
        } else {
            Employee employee = (Employee) session.getAttribute("employee");
            if (employee.getRole().equals("MANAGER")){
                return "project_html/newProject";
            }
            else{
                return "error";
            }
        }

    }

    //Denne virker
    @PostMapping("/createNewProject")
    public String createNewProject(WebRequest webr, HttpSession session) {
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

        //Connect project to manager via LinkTabel
        Employee employee = (Employee) session.getAttribute("employee");
        int employeeID = employee.getEmployeeId();
        ltr.insertLinkTabelWithEmployeeAndProjectIntoDatabase(employeeID, projectId);

        return "confirmationPage";
    }

    //Denne virker
    @GetMapping("/editProject/{thisProject}")
    public String editProjectGetProject(@PathVariable("thisProject") int thisProject, Model model, HttpSession session) {
        if (ls.notLoggedIn(session)) {
            return  "redirect:/";
        } else {
            editThisProject = ps.getProjectObject(thisProject);
            model.addAttribute("Project", ps.getProjectObject(thisProject));

            //Checks if the user is a manager and thus allowed to access the site
            Employee employee = (Employee) session.getAttribute("employee");
            if (employee.getRole().equals("MANAGER")){
                return "project_html/editProject";
            }
            else{
                return "error";
            }
        }
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
        String startDate = webr.getParameter("project-startdate-input");
        String endDate = webr.getParameter("project-enddate-input");


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

        if (startDate!="" && startDate!=null){
            editThisProject.setStartDate(startDate);
        }

        if (endDate!="" && endDate!=null){
            editThisProject.setEndDate(endDate);
        }

        //Update project in DB
        pr.updateProjectInDatabase(editThisProject);

        return "confirmationPage";
    }

    @GetMapping("/deleteProject/{projectId}")
    public String deleteSubproject(@PathVariable("projectId") String projectId, HttpSession session){

        //Checks if the user is a manager and thus allowed to delete the project
        Employee employee = (Employee) session.getAttribute("employee");
        if (employee.getRole().equals("MANAGER")){
            int id = Integer.parseInt(projectId);
            ps.deleteProjectFromDatabase(id);
            return "confirmationPage";
        }
        else{
            return "error";
        }
    }

    //Virker
    @GetMapping("/getProjectsForEmployee")
    public String getProjectsForEmployee(HttpSession session, Model model) {
        Employee employee;
        employee = (Employee) session.getAttribute("employee");
        int employeeId = employee.getEmployeeId();
        ArrayList<Project> projects = ltr.getActiveProjectsConnectedToEmployee(employeeId);
        model.addAttribute("Projects", projects);
        return "fragments/projectsConnectedToEmployee.html";
    }

    @GetMapping("/showProjects")
    public String showProjects(HttpSession session, Model model) {
        Employee employee = (Employee) session.getAttribute("employee");
        ArrayList<Project> projects = ltr.getActiveProjectsConnectedToEmployee(employee.getEmployeeId());
        //Arraylist subprojects
        ArrayList<Subproject> subprojects = new ArrayList<>();
        for (Project p : projects) {
            subprojects = ltr.getSubprojectsConnectedToProjectsAndEmployee(p.getProjectId(), employee.getEmployeeId());
        }

        //Map 'subprojects' to model, name 'Subprojects'
        model.addAttribute("Projects", projects);
        model.addAttribute("Subprojects", subprojects);
        return "project_html/showProjects";
    }

    @GetMapping("/addEmployeeToProject/{thisProject}")
    public String addEmployeeToProject(@PathVariable("thisProject") int thisProject, Model model) {
        ArrayList<Employee> allEmployees = er.getAllEmployeesFromDatabase();
        ArrayList<Employee> projectEmployees = ltr.getEmployeesFromProject(thisProject);

        allEmployees.removeAll(projectEmployees);

        model.addAttribute("projectEmployees", projectEmployees);
        model.addAttribute("allEmployees", allEmployees);
        editThisProject = ps.getProjectObject(thisProject);
        model.addAttribute("project", editThisProject);
        return "project_html/addEmployeeToProject.html";
    }


    @RequestMapping("/addEmployeeToProjectInput")
    public String addEmployeeToProject(WebRequest webr) {
        String employeeIdString = webr.getParameter("project-employeeId-input");
        int employeeId = Integer.parseInt(employeeIdString);
        int projectId = editThisProject.getProjectId();
        ltr.insertLinkTabelWithEmployeeAndProjectIntoDatabase(employeeId, projectId);
        return "confirmationPage";
    }

    @GetMapping("/frontPage")
    public String frontPage(HttpSession session) {
        if (ls.notLoggedIn(session)) {
            return  "redirect:/";
        } else {
            return "frontPage";
        }
        //Employee employee = (Employee) session.getAttribute("employee");
        //int employeeId = employee.getEmployeeId();
        //ArrayList<Project> projects = ltr.getProjectsConnectedToEmployee(employeeId);
        //model.addAttribute("projects", projects);
    }

    @GetMapping("/removeEmployeeFromProject/{thisProject}")
    public String removeEmployeeFromProject(@PathVariable("thisProject") int thisProject, Model model) {
        ArrayList<Employee> projectEmployees = ltr.getEmployeesFromProject(thisProject);
        model.addAttribute("projectEmployees", projectEmployees);
        editThisProject = ps.getProjectObject(thisProject);
        model.addAttribute("project", editThisProject);
        return "project_html/removeEmployeeFromProject.html";
    }

    @GetMapping("/removeEmployee/{employeeId}")
    public String removeEmployee(@PathVariable("employeeId") int employeeId, HttpSession session){

        if (ls.notLoggedIn(session)) {
            return  "redirect:/";
        } else {
            //Checks if the user is a manager and thus allowed to access the site
            Employee employee = (Employee) session.getAttribute("employee");
            if (employee.getRole().equals("MANAGER")){
                int projectId = editThisProject.getProjectId();
               ltr. removeEmployeeFromProject(employeeId, projectId);
                return "confirmationPage";
            }
            else{
                return "error";
            }
        }
    }


}
