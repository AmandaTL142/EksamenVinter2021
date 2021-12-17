package com.example.eksamenvinter2021.Controllers;

import com.example.eksamenvinter2021.Models.Employee;
import com.example.eksamenvinter2021.Models.Project;
import com.example.eksamenvinter2021.Models.Subproject;
import com.example.eksamenvinter2021.Models.Task;
import com.example.eksamenvinter2021.Resporsitories.EmployeeRepo;
import com.example.eksamenvinter2021.Resporsitories.LinkTableRepo;
import com.example.eksamenvinter2021.Resporsitories.ProjectRepo;
import com.example.eksamenvinter2021.Resporsitories.TaskRepo;
import com.example.eksamenvinter2021.Services.*;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.context.request.WebRequest;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;

@Controller
public class TaskController {

    //Task methods
    TaskService ts = new TaskService();
    TaskRepo tr = new TaskRepo();

    //Task objects
    Task t = new Task();

    //Project methods
    ProjectRepo pr = new ProjectRepo();
    ProjectService ps = new ProjectService();
    SubprojectService sps = new SubprojectService();

    //Employee
    EmployeeRepo er = new EmployeeRepo();
    EmployeeService es = new EmployeeService();

    //Methods
    LinkTableRepo ltr = new LinkTableRepo();
    LoginService ls = new LoginService();


    //lavet af Andrea
    @GetMapping("/showTask/{thisProject}")
    public String tasks(@PathVariable("thisProject") int thisProject, Model m, HttpSession session){

        if (ls.notLoggedIn(session)) {
            return  "redirect:/";
        } else {
            Employee employee = (Employee) session.getAttribute("employee");
            if (employee.getRole().equals("EMPLOYEE")){

                int pId = thisProject;

                m.addAttribute("tasks", ts.getAllTasksInArray());

                m.addAttribute("project", ps.getProjectObject(pId));


                //her hentes et project, som er baseret på hvilket id der er opgivet i url
                Project project = ps.getProjectObject(thisProject);
                /*Dette project gemmes i session og netop dette bestemte projekt tages med videre til bland andet
                @GetMapping("/createTask/{thisProjectId}") og @PostMapping("/createNewTask") */

                session.setAttribute("Project", project);

                return "task_html/showTask";
            }
            else{
                return "error";
            }
        }

    }


    //Ændret af Amanda
    //projectId skal tages med videre i url, for at komme på det rigtige projekt
    @GetMapping("/createTask/{thisProjectId}")
    public String project(@PathVariable("thisProjectId") int thisProjectId, Model m, HttpSession session) {

        if (ls.notLoggedIn(session)) {
            return  "redirect:/";
        } else {

            /*Her defineres et project ud fra hvilket ID, der indtastes i url*/
            Project p = ps.getProjectObject(thisProjectId);

            session.setAttribute("Project", p);

            m.addAttribute("project",p);
            return"task_html/newTask";
        }
    }

    //lavet af Andrea
    @PostMapping("/createNewTask")
    //For at få adgang til denne, skal man igennem showProject.Html
    public String createNewTask(WebRequest wr, HttpSession session){
        //Input i browser
        String title = wr.getParameter("new-task-title");
        String description = wr.getParameter("new-task-description");
        String estimated_time = wr.getParameter("new-task-estimatedTime");
        String timeUsed = wr.getParameter("new-task-timeUsed");
        String status = wr.getParameter("new-task-status");
        String startDate = wr.getParameter("new-task-startDate");
        String endtDate = wr.getParameter("new-task-endDate");

        //Create task-object
        Task tempTask = ts.createNewTask(title,description,estimated_time,timeUsed,status, startDate, endtDate);

        //Henter projektet, som blev instanzieret i showTask
        Project project = (Project) session.getAttribute("Project");

        //Gemmer projectet fra session i variablen projectId
        int projectId = project.getProjectId();

        //Connecter nyoprettet task til project fra session
        tempTask.setTaskProjectId(projectId);

        tr.insertNewTaskToDB(tempTask);
        int taskId = tr.getTaskId(tempTask.getTaskTitle());
        Employee emp = (Employee) session.getAttribute("employee");
        int employeeID = emp.getEmployeeId();
        tr.insertTaskToLinktable(employeeID, taskId, projectId);

        return "frontPage";
    }


   //Lavet af Andrea
    @GetMapping("/createTaskFromSubproject/{thisSubprojectId}")
    public String v(@PathVariable("thisSubprojectId") int thisSubprojectId, Model m, HttpSession session) {
        //Her gemmer vi projektID, som en int

        if (ls.notLoggedIn(session)) {
            return  "redirect:/";
        } else {
            //ønsker at hente projektet, specifik dets ID, så vi kan connecte task til dette Project
            Subproject sp = sps.getSubprojectObject(thisSubprojectId);

            session.setAttribute("Subproject", sp);

            int projectId = sp.getProjectId();
            Project p = pr.getProjectFromDatabase(projectId);

            m.addAttribute("project",p);
            return"task_html/newTask2";
        }
    }

    //Lavet af Amanda
    @PostMapping("/createNewTaskFromSubproject")
    public String createNewTaskFromSubproject(WebRequest wr, HttpSession session){
        //Først fortælles, at der ønskes input fra bruger via browser
        String title = wr.getParameter("new-task-title");
        String description = wr.getParameter("new-task-description");

        String estimated_time = wr.getParameter("new-task-estimatedTime");

        String timeUsed = wr.getParameter("new-task-timeUsed");
        String status = wr.getParameter("new-task-status");
        String startDate = wr.getParameter("new-task-startDate");
        String endtDate = wr.getParameter("new-task-endDate");


        Task tempTask = ts.createNewTask(title,description,estimated_time,timeUsed,status, startDate, endtDate);

        Subproject subproject = (Subproject) session.getAttribute("Subproject");

        int projectId = subproject.getProjectId();
        tempTask.setTaskProjectId(projectId);

        int subprojectId = subproject.getSubprojectId();
        tempTask.setTaskSubprojectId(subprojectId);

        tr.insertNewTaskToDB(tempTask);
        int taskId = tr.getTaskId(tempTask.getTaskTitle());
        Employee emp = (Employee) session.getAttribute("employee");
        int employeeId = emp.getEmployeeId();
        tr.insertTaskToLinktableWithSubproject(employeeId, taskId, projectId, subprojectId);

        return "frontPage";
    }

    //Amanda
    @GetMapping("/editTask/{thisTask}")
    public String editTask(@PathVariable("thisTask") int thisTask, Model m, HttpSession session){

        if (ls.notLoggedIn(session)) {
            return  "redirect:/";
        } else {
            Project project = (Project) session.getAttribute("Project");

            int id = thisTask;
            session.setAttribute("Task", ts.getTaskObject(thisTask));
            Task task = ts.getTaskObject(id);

            m.addAttribute("tasks",task);
            m.addAttribute("project",project);

            return "task_html/editTask";
        }

    }

    //lavet af Andrea
    @PostMapping("/editTaskChanges")
    public String editTask(WebRequest wr, HttpSession session){
        /*Her gøres der klar til input fra GUI via Webrequest, da det skal være muligt for
        bruger at kunne indtaste det der skal ændres.*/

        String title=wr.getParameter("new-task-title");
        String description = wr.getParameter("new-task-description");

        String estimated_time = wr.getParameter("new-task-estimatedTime");

        String timeUsed = wr.getParameter("new-task-timeUsed");
        String status = wr.getParameter("new-task-status");

        String startDate = wr.getParameter("new-task-startDate");
        String endtDate = wr.getParameter("new-task-endDate");

        //Henter tasken fra session, som blev instanzieret i @GetMapping("/editTask/{thisTask}")
        Task task = (Task) session.getAttribute("Task");

        /*Disse if-statements sørger for, at hvis alle felter ikke udfyldes/skal ændres skal programmet ikke lave det om til et tomt felt,
        men bare lade den allerede eksisterende data være*/
        if (title != "" && title != null) {
            task.setTaskTitle(title);
        }

        if (description != "" && description != null) {
            task.setTaskDescription(description);
        }

        if (estimated_time != "" && estimated_time != null) {
            task.setTaskEstimatedTime(estimated_time);
        }

        if (timeUsed != "" && timeUsed != null) {
            task.setTaskStatus(timeUsed);
        }

        if (status != "" && status != null) {
            task.setTaskStatus(status);
        }

        if (startDate != "" && startDate != null) {
            task.setTaskStartDate(startDate);
        }
        if (endtDate != "" && endtDate != null) {
            task.setTaskEndDate(endtDate);
        }

        /*Her henter vi projektet som er blevet bestemt fra  @GetMapping("/showTask/{thisProject}"), */
        Project project = (Project) session.getAttribute("Project");

        //tilknytter Tasken, der er gemt i session og projectet der er gemt i session
        task.setTaskProjectId(project.getProjectId());

        //opdatere taske via denne metode, som indeholder et SET-statment, der går ind og ændre allerede eksiterende data i databasen
        ts.updateTask(task);

        return "frontPage";
    }


    //Lavet af Andrea
    @GetMapping("/deleteTask/{taskId}")
    public String deleteTask(@PathVariable("taskId") int taskId, HttpSession session) {

        if (ls.notLoggedIn(session)) {
            return  "redirect:/";
        } else {
            ts.deleteTask(taskId);

            return "frontPage";
        }

    }

//Andrea inspireret af Amanda
    @GetMapping("/getTaskForEmployee")
    public String getTaskForEmployee(HttpSession session, Model m){
        Employee emp;
        emp = (Employee) session.getAttribute("employee");
        int employeeId = emp.getEmployeeId();

        ArrayList<Task> tasks = tr.getTaskConnectedToEmployee(employeeId);
        m.addAttribute("tasks",tasks);
        return "fragments/taskConnectedToEmployee";
    }

    //Andrea inspireret af Amanda
    @GetMapping("/addEmployeeToTask/{thisTask}")
    public String addEmployeeToTask(@PathVariable("thisTask") int thisTask, Model m, HttpSession session){
        //manage employee
        ArrayList<Employee> allEmployees = er.getAllEmployeesFromDatabase();
        m.addAttribute("alllEmployees",allEmployees);

        //connects task to employee
        ArrayList<Employee> taskEmployee = tr.getEmployeeFromTask(thisTask);
        m.addAttribute("taskEMployee",taskEmployee);

        //manage task
        session.setAttribute("Task", ts.getTaskObject(thisTask));
        Task task = ts.getTaskObject(thisTask);

        m.addAttribute("task",task);

        return "frontPage";
    }

    //Andrea inspireret af Amanda
    @PostMapping("/addEmployeeToTaskInput")
    public String addEmployeeToTask(WebRequest wr, HttpSession session){
        String employeeIdString = wr.getParameter("task-employeeId-input");
        Task task = (Task) session.getAttribute("Task");

        int employeeId = Integer.parseInt(employeeIdString);

        int taskId = task.getTaskId();

        tr.insertLinkTableWithEmployeeAndTaskInDB(employeeId,taskId);

        return "frontPage";
    }
}

