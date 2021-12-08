package com.example.eksamenvinter2021.Controllers;

import com.example.eksamenvinter2021.Models.Project;
import com.example.eksamenvinter2021.Models.Task;
import com.example.eksamenvinter2021.Resporsitories.TaskRepo;
import com.example.eksamenvinter2021.Services.LoginService;
import com.example.eksamenvinter2021.Services.ProjectService;
import com.example.eksamenvinter2021.Services.TaskService;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.request.WebRequest;

import javax.servlet.http.HttpSession;
import java.sql.SQLException;
import java.util.ArrayList;

@Controller
public class TaskController {

    TaskService ts = new TaskService();
    ProjectService ps = new ProjectService();
    TaskRepo tr = new TaskRepo();
    Task t = new Task();
    Project sharedProject = new Project();

    LoginService ls = new LoginService();


    //vi skal i endpoint navngive, og definere at vi ønsker at en variable skal medfølge
    @GetMapping("/task/{thisProject}")
    /* Pathvariable, fortæller, at vi ønsker en variable, der skal føres med videre,
    hvor vi herefter definere den som en int */

    /*public String newTask(@PathVariable("thisProject") int thisProject, Model m, HttpSession session){

        if (ls.notLoggedIn(session)) {
            return  "redirect:/";
        } else {
            //int id = Integer.parseInt(thisProject);
            model.addAttribute("Project", ps.getProjectObject(thisProject));
            return "project_html/showProject";
        }
        return "newTask";
    }*/


    /* @GetMapping("/newProject")
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

    } */

    @PostMapping("/createNewTask")
    public String createNewTask(WebRequest wr){
        //Først fortælles, at der ønskes input fra bruger via browser
        String title=wr.getParameter("new-task-title");
        String description = wr.getParameter("new-task-description");

        String estimated_time = wr.getParameter("new-task-estimatedTime");

        String timeUsed = wr.getParameter("new-task-timeUsed");
        String status = wr.getParameter("new-task-status");


        //Her oprettes task-objectet
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

        return "newTask";
    }

    @GetMapping("/taskC/{thisProjectId}")
    public String project(@PathVariable("thisProjectId") int thisProjectId, Model m) {
        //Her omdefinere vi thisProhecjtId som værende id
        int id = thisProjectId;

        //
        Project p = ps.showProject(thisProjectId);
        sharedProject = p;
        m.addAttribute("project",p);

        return"newTask";

    }

    @GetMapping("/showTask")
    public String allTasks(Model objectThatTransportsData){
        ArrayList<Task> allTasks = tr.getAllTasks(15);
        objectThatTransportsData.addAttribute("tasks",allTasks);

        return "showTask"; }


    @PostMapping("/showProjectName")
    public String showProjectName(){

        return "showTask";
    }

    @GetMapping("/showTasks/{thisProjectId}")
    public String showTasks(@PathVariable("thisProjectId") int thisProjectId, Model m){
        int id= thisProjectId;

        //
        Project p = ps.showProject(thisProjectId);
        sharedProject = p;
        m.addAttribute("project",p);

        ArrayList<Task> allTasks = tr.getAllTasks(thisProjectId);
        m.addAttribute("allTasks",allTasks);

        return "showTask";
    }



    @PostMapping("/editTask")
    public String getTask(WebRequest wr){
        //TODO få den connected med det rigtige taskID/projectID
        //@PathVariable("thisTask")

        String title=wr.getParameter("new-task-title");
        String description = wr.getParameter("new-task-description");

        String estimated_time = wr.getParameter("new-task-estimatedTime");
        String timeUsed = wr.getParameter("new-task-timeUsed");
        String status = wr.getParameter("new-task-status");

        ts.updateTask(title,description,estimated_time,timeUsed,status);

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

        tr.updateTask(t);

        return "/";
    }


    @GetMapping("/deleteTask/{taskId}")
    public String deleteWish(@PathVariable int taskId) throws SQLException {
        tr.deleteTask(taskId);
        return "confirmPage";
    }
}

//transform string til datetime-java-format (loacltime) parToDateTime
//localTime time = LocalTime.parse("den aflveret String fra browser")
//evt. lav validation
//System.out.println(LocalTime.parse(estimated_time));


   /* TODO-liste:
        derefter skal tasken knyttes til project, sådan at man kun kan oprette en task
        inde i et projekt og dermed automatisk er koblet til et project*/
