package com.example.eksamenvinter2021.Controllers;

import com.example.eksamenvinter2021.Models.Customer;
import com.example.eksamenvinter2021.Models.Employee;
import com.example.eksamenvinter2021.Models.Project;
import com.example.eksamenvinter2021.Models.Subproject;
import com.example.eksamenvinter2021.Services.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;

@Controller
public class ProjectController {
    //Amanda Tolstrup Laursen

    //Relevant services imported as objects
    ProjectService ps = new ProjectService();
    CustomerService cs = new CustomerService();
    LinkTabelService lts = new LinkTabelService();
    EmployeeService es = new EmployeeService();
    LoginService ls = new LoginService();


    //Denne controller kan bruges til at vise et projekt, men den er ikke implementeret i programmets nuværende version.
    /*
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

     */


    //Denne controller bruges til at vise siden, hvor brugeren kan oprette et nyt projekt
    @GetMapping("/newProject")
    public String newProject(HttpSession session, Model model) {
        //Det tjekkes, om brugeren er logget ind. Hvis ikke, sendes denne til login-siden
        if (ls.notLoggedIn(session)) {
            return  "redirect:/";
        } else {
            //Det tjekkes, om brugeren er en manager. Hvis ikke, sendes denne til en error-page
            Employee employee = (Employee) session.getAttribute("employee");
            if (employee.getRole().equals("MANAGER")){

                //En arraylist indeholdende customer-objekter oprettes. Objekterne skabes på baggrund af info fra DB.
                ArrayList<Customer> allCustomers = (ArrayList<Customer>) cs.getAllCustomers();

                //Arraylisten med customers gemmes i en model, som html-siden refererer til
                model.addAttribute("allCustomers", allCustomers);
                return "project_html/newProject";
            }
            else{
                return "error";
            }
        }

    }

    //Denne controller bruges til at hente input fra brugeren og bruge det til at oprette et projekt
    @PostMapping("/createNewProject")
    public String createNewProject(WebRequest webr, HttpSession session) {

        //Input hentes fra brugeren og gemmes som String-variabler
        String title = webr.getParameter("project-title-input");
        String deadline = webr.getParameter("project-deadline-input");
        String basePriceString = webr.getParameter("project-baseprice-input");
        String description = webr.getParameter("project-description-input");
        String customerIdString = webr.getParameter("project-customer-input");
        String status = webr.getParameter("project-status-input");

        //basePriceString forsøges konverteret til en int. Har brugeren ikke indtastet et gyldigt tal, printes en
        // fejlmeddelselse. Ellers gemmes int'en som en ny variabel.
        double basePrice = 0;
        if (basePriceString != null){
            try {
                basePrice = Double.parseDouble(basePriceString);
            } catch (Exception e) {
                System.out.println("Baseprice could not be converted from string to double. " +
                        "Check whether the input is a number.");
                e.printStackTrace();
            }
        }

        //customerIdString konverteres til int
        int customerId = 0;
        if (customerIdString != null){
            customerId = Integer.parseInt(customerIdString);
        }

        //Et projekt-objekt oprettes
        Project currentProject = ps.createNewProjectObject(title, deadline, status, basePrice, customerId, description);

        //Projekt-objektet tilføjes til DB som et datasæt. Ved tilføjes tildeles datasættet et project_id.
        ps.insertProjectIntoDatabase(currentProject);

        //Det nyligt oprettede project_id hentes fra databasen, idet der søges efter projektets titel
        int projectId = ps.getProjectIdFromTitle(title);
        currentProject.setProjectId(projectId);

        //Der oprettes et datasæt i link_table i DB, der indeholder employeeId og projectID.
        // Således forbindes brugeren, der opretter projektet, med projektet
        Employee employee = (Employee) session.getAttribute("employee");
        int employeeId = employee.getEmployeeId();
        lts.insertLinkTableWithEmployeeAndProjectIntoDatabase(employeeId, projectId);

        return "frontPage";
    }


    //Denne controller bruges til at modtage et projekt-id fra programmet, oprette et tilhørende projekt
    // og gemme dette til senere brug
    @GetMapping("/editProject/{thisProject}")
    public String editProjectGetProject(@PathVariable("thisProject") int thisProject, Model model, HttpSession session) {
        //Det tjekkes, om brugeren er logget ind. Hvis ikke, sendes denne til login-siden
        if (ls.notLoggedIn(session)) {
            return  "redirect:/";
        } else {

            //Et projekt-objekt oprettes på baggrund af projekt-id'et
            Project project = ps.getProjectObject(thisProject);

            //Projekt-objektet gemmes i session, så den næste controller kan tilgå det
            session.setAttribute("Project", project);

            //Projekt-objektet  gemmes i en model, som html-siden refererer til
            model.addAttribute("Project", project);

            //Det tjekkes, om brugeren er en manager. Hvis ikke, sendes denne til en error-page
            Employee employee = (Employee) session.getAttribute("employee");
            if (employee.getRole().equals("MANAGER")){
                return "project_html/editProject";
            }
            else{
                return "error";
            }
        }
    }

    //Denne controller bruges til at hente input fra brugeren og bruge det til at opdatere et projekt
    @RequestMapping("/editProjectChanges")
    public String editProjectGetChanges(WebRequest webr, HttpSession session) {

        //Input hentes fra brugeren og gemmes som String-variabler
        String title = webr.getParameter("project-title-input");
        String deadline = webr.getParameter("project-deadline-input");
        String basePriceString = webr.getParameter("project-baseprice-input");
        String description = webr.getParameter("project-description-input");
        String costumerName = webr.getParameter("project-costumer-input");
        String status = webr.getParameter("project-status-input");
        String startDate = webr.getParameter("project-startdate-input");
        String endDate = webr.getParameter("project-enddate-input");

        //Projekt-objektet fra session hentes.
        Project Project = (Project) session.getAttribute("Project");

        //Projekt-objektet opdateres med de nye oplsyninger. En attribut opdateres kun, hvis der er modtaget
        // input fra brugeren.
        if (title!="" && title!=null){
            Project.setProjectTitle(title);
        }

        if (deadline!="" && deadline!=null){
            Project.setProjectDeadline(deadline);
        }

        //basePriceString forsøges konverteret til en int. Har brugeren ikke indtastet et gyldigt tal, printes en
        // fejlmeddelselse. Ellers gemmes int'en som en ny variabel.
        if (basePriceString!="" && basePriceString!=null){
            double basePrice = 0;
            try {
                basePrice = Double.parseDouble(basePriceString);
                Project.setBasePrice(basePrice);
            } catch (Exception e) {
                System.out.println("Baseprice could not be converted from string to double. " +
                        "Check whether the input is a number.");
                e.printStackTrace();
            }
        }

        if (description!="" && description!=null){
            Project.setDescription(description);
        }
        if (costumerName!="" && costumerName!=null){
            int customerId = cs.getCustomerIdFromDatabase(costumerName);
            Project.setCustomerId(customerId);
        }

        //Status er i på siden autoudfyldt med information, så denne vil aldrig være tom
        Project.setStatus(status);

        if (startDate!="" && startDate!=null){
            Project.setStartDate(startDate);
        }

        if (endDate!="" && endDate!=null){
            Project.setEndDate(endDate);
        }

        //Projektet opdateres i DB
        ps.updateProjectInDatabase(Project);

        return "frontPage";
    }

    //Denne controller bruges til at slette et projekt via dets id
    @GetMapping("/deleteProject/{projectId}")
    public String deleteSubproject(@PathVariable("projectId") int projectId, HttpSession session){
        //Det tjekkes, om brugeren er logget ind. Hvis ikke, sendes denne til login-siden
        if (ls.notLoggedIn(session)) {
            return  "redirect:/";
        } else {
            //Det tjekkes, om brugeren er en manager. Hvis ikke, sendes denne til en error-page
            Employee employee = (Employee) session.getAttribute("employee");
            if (employee.getRole().equals("MANAGER")){

                //Projektet slettes fra databasen
                ps.deleteProjectFromDatabase(projectId);
                return "frontPage";
            }
            else{
                return "error";
            }
        }

    }

    //Nedenstående kan bruges til at vise alle projekter forbundet til den bruger, der er logget ind,
    // men den er ikke implementeret i programmets nuværende version. I stedet udvælges projekterne i en
    // anden visning via ThymeLeaf.
    /*
    @GetMapping("/getProjectsForEmployee")
    public String getProjectsForEmployee(HttpSession session, Model model) {
        Employee employee;
        employee = (Employee) session.getAttribute("employee");
        int employeeId = employee.getEmployeeId();
        ArrayList<Project> projects = lts.getActiveProjectsConnectedToEmployee(employeeId);
        model.addAttribute("Projects", projects);
        return "fragments/projectsConnectedToEmployee.html";
    }
     */

    //Nedenstående kan bruges til at vise alle projekter, men den er ikke implementeret i programmets nuværende version.
    /*
    @GetMapping("/showProjects")
    public String showProjects(HttpSession session, Model model) {
        Employee employee = (Employee) session.getAttribute("employee");
        ArrayList<Project> projects = lts.getActiveProjectsConnectedToEmployee(employee.getEmployeeId());
        //Arraylist subprojects
        ArrayList<Subproject> subprojects = new ArrayList<>();
        for (Project p : projects) {
            subprojects = lts.getSubprojectsConnectedToProjectsAndEmployee(p.getProjectId(), employee.getEmployeeId());
        }

        //Map 'subprojects' to model, name 'Subprojects'
        model.addAttribute("Projects", projects);
        model.addAttribute("Subprojects", subprojects);
        return "project_html/showProjects";
    }

     */

    //Denne controller bruges til at vise brugeren den side, hvor den kan tilføje en employee til et projekt
    @GetMapping("/addEmployeeToProject/{thisProject}")
    public String addEmployeeToProject(@PathVariable("thisProject") int thisProject, Model model, HttpSession session) {
        //Det tjekkes, om brugeren er logget ind. Hvis ikke, sendes denne til login-siden
        if (ls.notLoggedIn(session)) {
            return  "redirect:/";
        } else {
            //Det tjekkes, om brugeren er en manager. Hvis ikke, sendes denne til en error-page
            Employee employee = (Employee) session.getAttribute("employee");
            if (employee.getRole().equals("MANAGER")){

                //Der oprettes én arraylist med alle employees fra DB og én med alle employees, der er tilknyttet
                // projektet
                ArrayList<Employee> allEmployees = es.getAllEmployeesFromDatabase();
                ArrayList<Employee> projectEmployees = lts.getEmployeesFromProject(thisProject);

                //Projektet, der skal have tilføjet employees, oprettes som projekt og gemmes i session
                session.setAttribute("Project", ps.getProjectObject(thisProject));

                //Her fjernes alle de employees, der allerede er tilknyttet projektet, fra arraylisten med alle
                // employees. Således kommer allEmployees til kun at indeholde de employees, der ikke allerede er
                // tilknyttet projektet
                allEmployees.removeAll(projectEmployees);

                //De to arraylister og projekt-objekter gemmes i en model. Arraylisten med allerede tilknyttede
                // employees bruges til at vise disse på siden
                model.addAttribute("projectEmployees", projectEmployees);
                model.addAttribute("allEmployees", allEmployees);
                model.addAttribute("project", ps.getProjectObject(thisProject));
                return "project_html/addEmployeeToProject.html";
            } else{
                return "error";
            }

        }
    }

    //Denne controller bruges til at modtage brugerens valg af employee, der skal tilføjes
    @RequestMapping("/addEmployeeToProjectInput")
    public String addEmployeeToProject(WebRequest webr, HttpSession session) {
        String employeeIdString = webr.getParameter("project-employeeId-input");
        Project Project = (Project) session.getAttribute("Project");
        int employeeId = Integer.parseInt(employeeIdString);
        int projectId = Project.getProjectId();
        lts.insertLinkTableWithEmployeeAndProjectIntoDatabase(employeeId, projectId);
        return "frontPage";
    }

    //Denne controller bruges til at vise de projekter, brugeren er tilknyttet. Logikken kan findes i HTML'en.
    @GetMapping("/frontPage")
    public String frontPage(HttpSession session, Model model) {
        if (ls.notLoggedIn(session)) {
            return  "redirect:/";
        } else {
            return "frontPage";
        }

    }

    //Denne controller bruges til at tilgå en side, hvor man kan vælge, hvilken employee, der skal fjernes fra
    // et projekt
    @GetMapping("/removeEmployeeFromProject/{thisProject}")
    public String removeEmployeeFromProject(@PathVariable("thisProject") int thisProject, Model model, HttpSession session) {
        if (ls.notLoggedIn(session)) {
            return  "redirect:/";
        } else {
            ArrayList<Employee> projectEmployees = lts.getEmployeesFromProject(thisProject);
            ArrayList<Employee> projectManagers = lts.getManagersFromProject(thisProject);
            model.addAttribute("projectEmployees", projectEmployees);
            model.addAttribute("projectManagers", projectManagers);
            session.setAttribute("Project", ps.getProjectObject(thisProject));
            model.addAttribute("project", ps.getProjectObject(thisProject));

            return "project_html/removeEmployeeFromProject.html";
        }
    }

    //Denne controller bruges til at fjerne en valgt employee fra en liste ud fra dennes employeeId og et projekt, der
    // blev gemt i session i ovenstående controller
    @GetMapping("/removeEmployee/{employeeId}")
    public String removeEmployee(@PathVariable("employeeId") int employeeId, HttpSession session){

        if (ls.notLoggedIn(session)) {
            return  "redirect:/";
        } else {
            //Checks if the user is a manager and thus allowed to access the site
            Employee employee = (Employee) session.getAttribute("employee");
            if (employee.getRole().equals("MANAGER")){
                Project Project = (Project) session.getAttribute("Project");
                int projectId = Project.getProjectId();
               lts.removeEmployeeFromProject(employeeId, projectId);
                return "frontPage";
            }
            else{
                return "error";
            }
        }
    }

    //Denne controller bruges til at vise subprojects, tasks og i fremtiden subtasks knyttet til et projekt.
    // Logikken findes i HTML
    @GetMapping("/collapsible/{thisProject}")
    public String collabsible(@PathVariable("thisProject") int thisProject, Model model, HttpSession session) {
        if (ls.notLoggedIn(session)) {
            return  "redirect:/";
        } else {
            model.addAttribute("Project", ps.getProjectObject(thisProject));
            return "project_html/amandasCollapsible.html";
        }
    }


}
