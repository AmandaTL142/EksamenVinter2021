package com.example.eksamenvinter2021.Controllers;

import com.example.eksamenvinter2021.Models.Project;
import com.example.eksamenvinter2021.Models.Task;
import com.example.eksamenvinter2021.Resporsitories.TaskRepo;
import com.example.eksamenvinter2021.Services.ProjectService;
import com.example.eksamenvinter2021.Services.TaskService;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.request.WebRequest;

import java.sql.SQLException;
import java.util.ArrayList;

@Controller
public class TaskController {

    TaskService ts = new TaskService();
    ProjectService ps = new ProjectService();
    TaskRepo tr = new TaskRepo();
    Task t = new Task();
    Project sharedProject = new Project();

    //TODO få samlet alle HTML der tilhører task i en mappe

    @GetMapping("/showTask")
    public String allTasks(Model objectThatTransportsData){
        ArrayList<Task> allTasks = tr.getAllTasks(15);
        objectThatTransportsData.addAttribute("tasks",allTasks);

        return "showTask"; }


    @PostMapping("/showProjectName")
    public String showProjectName(){

        return "showTask";
    }

    @GetMapping("/newTask")
    public String newTask(){
        return "newTask";
    }

    @PostMapping("/createNewTask")
    public String createNewTask(WebRequest wr){

        String title=wr.getParameter("new-task-title");
        String description = wr.getParameter("new-task-description");

        String estimated_time = wr.getParameter("new-task-estimatedTime");

        String timeUsed = wr.getParameter("new-task-timeUsed");
        String status = wr.getParameter("new-task-status");
        //String projectID = wr.getParameter("new-task-projectID");

        Task tempTask = ts.createNewTask(title,description,estimated_time,timeUsed,status);

        tempTask.setProjectId(sharedProject.getProjectId());

        tr.insertNewTaskToDB(tempTask);

        return "newTask";
    }

    @GetMapping("/taskC/{thisProjectId}")
    public String project(@PathVariable("thisProjectId") int thisProjectId, Model m) {
        int id = thisProjectId;
        Project p = ps.showProject(thisProjectId);
        sharedProject = p;
        m.addAttribute("project",p);

        return"newTask";

    }

    @GetMapping("/showTasks/{thisProjectId}")
    public String showTasks(@PathVariable("thisProjectId") int thisProjectId, Model m){
        int id= thisProjectId;
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
