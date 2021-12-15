package com.example.eksamenvinter2021.Controllers;

import com.example.eksamenvinter2021.Models.Employee;
import com.example.eksamenvinter2021.Models.SubTask;
import com.example.eksamenvinter2021.Models.Task;
import com.example.eksamenvinter2021.Resporsitories.EmployeeRepo;
import com.example.eksamenvinter2021.Resporsitories.LinkTabelRepo;
import com.example.eksamenvinter2021.Resporsitories.SubTaskRepo;
import com.example.eksamenvinter2021.Resporsitories.TaskRepo;
import com.example.eksamenvinter2021.Services.EmployeeService;
import com.example.eksamenvinter2021.Services.LoginService;
import com.example.eksamenvinter2021.Services.SubTaskService;
import com.example.eksamenvinter2021.Services.TaskService;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.context.request.WebRequest;

public class SubTaskController {

    //Subtask methods
    SubTaskService ss = new SubTaskService();
    SubTaskRepo sr = new SubTaskRepo();

    //Subproject objects
    SubTask editThisSubtask = new SubTask();
    SubTask subT = new SubTask();

    //Task methods
    TaskRepo tr = new TaskRepo();
    TaskService ts = new TaskService();

    //task object
    Task eitThisTask = new Task();
    Task sharedTask = new Task();

    //employee
    EmployeeRepo er = new EmployeeRepo();
    EmployeeService es = new EmployeeService();

    //Methods
    LinkTabelRepo ltr = new LinkTabelRepo();
    LoginService ls = new LoginService();

    @GetMapping("/showSubtask/{thisTask}")
    public  String subtasks(@PathVariable("thisTask") int thisTask, Model m){
        int tID = thisTask;

        m.addAttribute("subtasks", sr.getSubtaskInArray());
        m.addAttribute("task", tr.getTaskFromDB(tID));

        return "subtask_html/readSubtask";
    }

    @GetMapping("/createSubtask/{thisTaskID}")
    public String task(@PathVariable("thisTaskID") int thisTaskID, Model m){
        int id = thisTaskID;

        Task t = tr.getTaskFromDB(thisTaskID);

        sharedTask = t;
        m.addAttribute("task",t);

        return "subtask_html/createSubtask";
    }
    @PostMapping("/createNewSubtask")
    public String createNewSubtask(WebRequest wr){
        String title=wr.getParameter("new-task-title");
        String description = wr.getParameter("new-task-description");

        String estimated_time = wr.getParameter("new-task-estimatedTime");

        String timeUsed = wr.getParameter("new-task-timeUsed");
        String status = wr.getParameter("new-task-status");

        String startDate = wr.getParameter("new-subtask-startDate");
        String endtDate = wr.getParameter("new-subtask-endDate");

        SubTask tempSubtask = ss.createNewSubtask(title,description,estimated_time,timeUsed,status, startDate, endtDate);

        tempSubtask.setTaskID(sharedTask.getId());

        sr.insertNewSubtaskToDB(tempSubtask);

        return "subtask_html/readSubtask";
    }

    @GetMapping("/editSubtask/{thisSubtask}")
    public String editSubtask(@PathVariable("thisSubtask") int thisSubtask, Model m){
        int id = thisSubtask;

        editThisSubtask = sr.getSubtaskFromDB(id);

        m.addAttribute("subtasks", editThisSubtask);
        m.addAttribute("task", sharedTask);

        return "subtask_html/updateSubtask";
    }

    @PostMapping("/editSubtaskChanges")
    public String editSubtask(WebRequest wr){


        String title=wr.getParameter("new-task-title");
        String description = wr.getParameter("new-task-description");

        String estimated_time = wr.getParameter("new-task-estimatedTime");

        String timeUsed = wr.getParameter("new-task-timeUsed");
        String status = wr.getParameter("new-task-status");

        String startDate = wr.getParameter("new-subtask-startDate");
        String endtDate = wr.getParameter("new-subtask-endDate");

        if (title != "" && title != null) {
            editThisSubtask.setTitle(title);
        }

        if (description != "" && description != null) {
            editThisSubtask.setDescription(description);
        }

        if (estimated_time != "" && estimated_time != null) {
            editThisSubtask.setEstimatedTime(estimated_time);
        }

        if (timeUsed != "" && timeUsed != null) {
            editThisSubtask.setStatus(timeUsed);
        }

        if (status != "" && status != null) {
            editThisSubtask.setStatus(status);
        }

        if (startDate != "" && startDate != null) {
            editThisSubtask.setStartDate(startDate);
        }
        if (endtDate != "" && endtDate != null) {
            editThisSubtask.setEndDate(endtDate);
        }

        sr.updateSubtask(editThisSubtask);

        return"subtask_html/updateSubtask";
    }

    @GetMapping("/deleteSubtask/{subtaskID}")
    public String deleteSubtask(@PathVariable("subtaskID") int subtaskID, Model m){
        int id = subtaskID;
        sr.deleteSubtaskFromDB(id);

        return "confirmationPage";
    }
}
