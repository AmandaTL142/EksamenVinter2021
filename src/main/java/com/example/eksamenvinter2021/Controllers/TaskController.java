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

    //Employee
    EmployeeRepo er = new EmployeeRepo();
    EmployeeService es = new EmployeeService();

    //Methods
    LinkTabelRepo ltr = new LinkTabelRepo();
    LoginService ls = new LoginService();



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
    public String createNewTask(WebRequest wr, HttpSession session){
        //Først fortælles, at der ønskes input fra bruger via browser
        String title=wr.getParameter("new-task-title");
        String description = wr.getParameter("new-task-description");

        String estimated_time = wr.getParameter("new-task-estimatedTime");

        String timeUsed = wr.getParameter("new-task-timeUsed");
        String status = wr.getParameter("new-task-status");


        //Create task-object
        Task tempTask = ts.createNewTask(title,description,estimated_time,timeUsed,status);

        /*I objektet ligger metoden setProjectId, som betyder at vi setter projectId
        ProjectId sættes til i første omgang at være et tomt project, hvor det herefter er muligt at
        kalde på metoden som henter projectId*/
        tempTask.setProjectId(sharedProject.getProjectId());

        /*her gøres der brug af metoden insertNewTaskToDB, som er en metode fra Task repo.
        Metoden indsætter de givende informationer ind til DB.
        I parantesen siges der, at disse værdier, som er indtastet af brugeren, i task-objektet
        og på denne måde instanzieres objektet*/
        tr.insertNewTaskToDB(tempTask);

        return "task_html/newTask";
    }



    @GetMapping("/showTask/{thisProject}")
    public String tasks(@PathVariable("thisProject") int thisProject, Model m){
        int pID = thisProject;

        System.out.println(tr.getTaskLinkedToProject(pID));
        System.out.println(pID);

        m.addAttribute("tasks", tr.getTaskLinkedToProject());
        m.addAttribute("project", ps.getProjectObject(pID));

        ArrayList<Task> allTasks = ts.getAllTasks(pID);
        m.addAttribute("allTasks",allTasks);

        return "task_html/showTask";
    }



@GetMapping("/editTask/{thisTask}")
public String editTask(@PathVariable("thisTask") int thisTask, Model m){
    int id = thisTask;
    edithThisTask = ts.getTaskObject(id);

    m.addAttribute("tasks",ts.getTaskObject(id));
    m.addAttribute("project",pr.getProjectFromDatabase(edithThisTask.getProjectId()));

    return "task_html/showTask";
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


        if (title != "" && title != null) {
            t.setTitle(title);
        }

        if (description != "" && description != null) {
            t.setDescription(description);
        }

        if (estimated_time != "" && estimated_time != null) {
            t.setEstimatedTime(estimated_time);
        }

        if (timeUsed != "" && timeUsed != null) {
            t.setStatus(timeUsed);
        }

        if (status != "" && status != null) {
            t.setStatus(status);
        }

        tr.updateTask(edithThisTask);

        return "task_html/showTask";
    }


    @GetMapping("/deleteTask/{taskId}")
    public String deleteTask(@PathVariable("taskId") int taskId, HttpSession session) {
        //Checks if user is employee,
        Employee employee = (Employee) session.getAttribute("employee");
        if (employee.getRole().equals("Employee")){
            int id = taskId;
            tr.deleteTask(id);
            return "confirmPage";
        }
        else{
            return "error";
        }

    }
}

//transform string til datetime-java-format (loacltime) parToDateTime
//localTime time = LocalTime.parse("den aflveret String fra browser")
//evt. lav validation
//System.out.println(LocalTime.parse(estimated_time));


   /* TODO-liste:
        derefter skal tasken knyttes til project, sådan at man kun kan oprette en task
        inde i et projekt og dermed automatisk er koblet til et project*/
