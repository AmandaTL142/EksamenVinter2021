package com.example.eksamenvinter2021.Controllers;

import com.example.eksamenvinter2021.Models.Employee;
import com.example.eksamenvinter2021.Models.Project;
import com.example.eksamenvinter2021.Models.Task;
import com.example.eksamenvinter2021.Resporsitories.EmployeeRepo;
import com.example.eksamenvinter2021.Resporsitories.LinkTabelRepo;
import com.example.eksamenvinter2021.Resporsitories.ProjectRepo;
import com.example.eksamenvinter2021.Resporsitories.TaskRepo;
import com.example.eksamenvinter2021.Services.EmployeeService;
import com.example.eksamenvinter2021.Services.LoginService;
import com.example.eksamenvinter2021.Services.ProjectService;
import com.example.eksamenvinter2021.Services.TaskService;
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
    Task edithThisTask = new Task();
    Task t = new Task();

    //Project methods
    ProjectRepo pr = new ProjectRepo();
    ProjectService ps = new ProjectService();

    //Project objects
    Project sharedProject = new Project();
    Project editThisProject = new Project();

    //Employee
    EmployeeRepo er = new EmployeeRepo();
    EmployeeService es = new EmployeeService();

    //Methods
    LinkTabelRepo ltr = new LinkTabelRepo();
    LoginService ls = new LoginService();



    @GetMapping("/showTask/{thisProject}")
    public String tasks(@PathVariable("thisProject") int thisProject, Model m){
        int pID = thisProject;

        m.addAttribute("tasks", tr.getTasksInArray());
        m.addAttribute("project", ps.getProjectObject(pID));

        sharedProject = ps.getProjectObject(thisProject);

        return "task_html/showTask";
    }


    //man skal indtaste et projectId, for at komme på det rigtige projekt
    @GetMapping("/createTask/{thisProjectId}")
    public String project(@PathVariable("thisProjectId") int thisProjectId, Model m) {
        //Her gemmer vi projektID, som en int
        int id = thisProjectId;

        //ønsker at hente projektet, specifik dets ID, så vi kan connecte task til dette Project
        Project p = ps.getProjectObject(thisProjectId);


        sharedProject = p;
        m.addAttribute("project",p);

        return"task_html/newTask";

    }

    @PostMapping("/createNewTask")
    //For at få adgang til denne, skal man igennem showProject.Html
    public String createNewTask(WebRequest wr){
        //Først fortælles, at der ønskes input fra bruger via browser
        String title=wr.getParameter("new-task-title");
        String description = wr.getParameter("new-task-description");

        String estimated_time = wr.getParameter("new-task-estimatedTime");

        String timeUsed = wr.getParameter("new-task-timeUsed");
        String status = wr.getParameter("new-task-status");
        String startDate = wr.getParameter("new-subtask-startDate");
        String endtDate = wr.getParameter("new-subtask-endDate");


        //Create task-object
        Task tempTask = ts.createNewTask(title,description,estimated_time,timeUsed,status, startDate, endtDate);

        /*I objektet ligger metoden setProjectId, som betyder at vi setter projectId
        ProjectId sættes til i første omgang at være et tomt project, hvor det herefter er muligt at
        kalde på metoden som henter projectId*/
        tempTask.setProjectId(sharedProject.getProjectId());

        /*her gøres der brug af metoden insertNewTaskToDB, som er en metode fra Task repo.
        Metoden indsætter de givende informationer ind til DB.
        I parantesen siges der, at disse værdier, som er indtastet af brugeren, i task-objektet
        og på denne måde instanzieres objektet*/
        tr.insertNewTaskToDB(tempTask);

        return "confirmationPage";
    }

@GetMapping("/editTask/{thisTask}")
public String editTask(@PathVariable("thisTask") int thisTask, Model m){
    int id = thisTask;
    edithThisTask = tr.getTaskFromDB(id);


    m.addAttribute("tasks",edithThisTask);
    m.addAttribute("project",sharedProject);


    return "task_html/editTask";
    }

    @PostMapping("/editTaskChanges")
    public String editTask(WebRequest wr){
        //TODO få den connected med det rigtige taskID/projectID
        //@PathVariable("thisTask")

        String title=wr.getParameter("new-task-title");
        String description = wr.getParameter("new-task-description");

        String estimated_time = wr.getParameter("new-task-estimatedTime");

        String timeUsed = wr.getParameter("new-task-timeUsed");
        String status = wr.getParameter("new-task-status");

        String startDate = wr.getParameter("new-subtask-startDate");
        String endtDate = wr.getParameter("new-subtask-endDate");


        if (title != "" && title != null) {
            edithThisTask.setTitle(title);
        }

        if (description != "" && description != null) {
            edithThisTask.setDescription(description);
        }

        if (estimated_time != "" && estimated_time != null) {
            edithThisTask.setEstimatedTime(estimated_time);
        }

        if (timeUsed != "" && timeUsed != null) {
            edithThisTask.setStatus(timeUsed);
        }

        if (status != "" && status != null) {
            edithThisTask.setStatus(status);
        }

        if (startDate != "" && startDate != null) {
            edithThisTask.setStartDate(startDate);
        }
        if (endtDate != "" && endtDate != null) {
            edithThisTask.setEndDate(endtDate);
        }

        tr.updateTask(edithThisTask);

        return "task_html/editTask";
    }


    @GetMapping("/deleteTask/{taskId}")
    public String deleteTask(@PathVariable("taskId") int taskId, Model m) {
        int id = taskId;
        tr.deleteTask(id);

        return "confirmationPage";

    }





    @GetMapping("/getTaskForEmployee")
    public String getTaskForEmployee(HttpSession session, Model m){
        Employee emp;
        emp = (Employee) session.getAttribute("employee");
        int employeeID = emp.getEmployeeId();
        ArrayList<Task> tasks = tr.getTaskConnectedToEmployee(employeeID);
        m.addAttribute("tasks",tasks);
        return "fragments/taskConnectedToEmployee";
    }

    @GetMapping("/addEmployeeToTask/{thisTask}")
    public String addEmployeeToTask(@PathVariable("thisTask") int thisTask, Model m){
        //manage employee
        ArrayList<Employee> allEmployees = er.getAllEmployeesFromDatabase();
        m.addAttribute("alllEmployees",allEmployees);

        //connects task to employee
        ArrayList<Employee> taskEmployee = tr.getEmployeeFromTask(thisTask);
        m.addAttribute("taskEMployee",taskEmployee);

        //manage task
        edithThisTask = ts.getTaskObject(thisTask);
        m.addAttribute("task",edithThisTask);

        return "task_html/addEmployeeToTask";
    }


    @PostMapping("/addEmployeeToTaskInput")
    public String addEmployeeToTask(WebRequest wr){
        String employeeIDString = wr.getParameter("task-employeeID-input");

        int employeeID = Integer.parseInt(employeeIDString);

        int taskID = edithThisTask.getId();

        tr.insertLinkTableWithEmployeeAndTaskInDB(employeeID,taskID);

        return "confirmationPage";
    }


}

//transform string til datetime-java-format (loacltime) parToDateTime
//localTime time = LocalTime.parse("den aflveret String fra browser")
//evt. lav validation
//System.out.println(LocalTime.parse(estimated_time));


   /* TODO-liste:
        derefter skal tasken knyttes til project, sådan at man kun kan oprette en task
        inde i et projekt og dermed automatisk er koblet til et project*/
