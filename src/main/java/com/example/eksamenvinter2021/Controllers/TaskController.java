package com.example.eksamenvinter2021.Controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;

import java.sql.Time;

@RestController
public class TaskController {

    //TODO få samlet alle HTML der tilhører task i en mappe
    //TODO har det påvirkelse på hvordan man referer til html, når man skal returne??????

    @GetMapping("/newTask")
    public String newTask(){
        return "newTask";
    }

@GetMapping("/createNewTask")

    public String newTask(WebRequest wr){

        /*Først få task til at køre
        derefter skal tasken knyttes til project, sådan at man kun kan oprette en task
        inde i et projekt og dermed automatisk er koblet til et project*/
        String title=wr.getParameter("new-task-title");
        String description = wr.getParameter("new-task-description");
        //TODO kan man ændre, så den modtager en time-datatype i stedet???

        String estimated_time = wr.getParameter("new-task-estimatedTime");
        String status = wr.getParameter("new-task-status");
        return "redirect:/showTask";
}
}
