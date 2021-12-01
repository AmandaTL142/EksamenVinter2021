package com.example.eksamenvinter2021.Controllers;

import com.example.eksamenvinter2021.Models.Task;
import com.example.eksamenvinter2021.Resporsitories.TaskRepo;
import com.example.eksamenvinter2021.Services.TaskService;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.request.WebRequest;

@Controller
public class TaskController {

    TaskService ts = new TaskService();
    TaskRepo tr = new TaskRepo();
    Task t = new Task();

    //TODO få samlet alle HTML der tilhører task i en mappe
    //TODO har det påvirkelse på hvordan man referer til html, når man skal returne??????

    @GetMapping("/")
    public String taskPage(){
        return "/taskPage";
    }

    @GetMapping("/taskEditor")
    public String taskEditor(){
        return "/taskEditor";
    }

    @PostMapping("/editTask")
    public String getOneTask(Model m){
        //TODO hvordan skal den opsættes i taskService?
        //TODO få den connected med det rigtige taskID
        //TODO HVordan får jeg initialized task object inde i html???

        Integer OneTask = tr.fetchSingleTask();
        m.addAttribute("OneTask", OneTask);

        ts.updateTask();
        return "redirect://";
    }



    @GetMapping("/newTask")
    public String newTask(){
        return "newTask";
    }

@PostMapping("/createNewTask")

    public String createNewTask(WebRequest wr){

        /* TODO-liste:
        Først få task til at køre
        derefter skal tasken knyttes til project, sådan at man kun kan oprette en task
        inde i et projekt og dermed automatisk er koblet til et project*/


        String title=wr.getParameter("new-task-title");
        String description = wr.getParameter("new-task-description");

        String estimated_time = wr.getParameter("new-task-estimatedTime");

        //transform string til datetime-java-format (loacltime) parToDateTime
    //localTime time = LocalTime.parse("den aflveret String fra browser")
    //evt. lav validation
    //System.out.println(LocalTime.parse(estimated_time));

        String timeUsed = wr.getParameter("new-task-timeUsed");
        String status = wr.getParameter("new-task-status");
        //String projectID = wr.getParameter("new-task-projectID");

        ts.createNewTask(title,description,estimated_time,timeUsed,status);

        return "newTask";
}
}
