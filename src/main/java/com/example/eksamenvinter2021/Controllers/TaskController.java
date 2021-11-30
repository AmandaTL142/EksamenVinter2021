package com.example.eksamenvinter2021.Controllers;

import com.example.eksamenvinter2021.Models.Task;
import com.example.eksamenvinter2021.Resporsitories.TaskRepo;
import com.example.eksamenvinter2021.Services.TaskService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;

import java.sql.Time;
import java.text.DateFormat;
import java.time.LocalTime;

@Controller
public class TaskController {

    TaskService ts = new TaskService();

    //TODO få samlet alle HTML der tilhører task i en mappe
    //TODO har det påvirkelse på hvordan man referer til html, når man skal returne??????

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

    Task currentTask = ts.createNewTask(title,description,estimated_time,timeUsed,status);

        return "newTask";
}
}
