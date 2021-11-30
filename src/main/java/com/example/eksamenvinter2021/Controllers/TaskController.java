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

        /*Først få task til at køre
        derefter skal tasken knyttes til project, sådan at man kun kan oprette en task
        inde i et projekt og dermed automatisk er koblet til et project*/
        String title=wr.getParameter("new-task-title");
        String description = wr.getParameter("new-task-description");

        //TODO //Hvordan håndtere jeg den som en time-type i stedet for en string type?
    String estimated_time = wr.getParameter(DateFormat.getDateInstance().format("new-task-estimatedTime"));
        String status = wr.getParameter("new-task-status");

      ts.createNewTask();

    Task currentTask = ts.createNewTask();

        return "newTask";
}
}
