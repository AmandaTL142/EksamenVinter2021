package com.example.eksamenvinter2021.Controllers;

import com.example.eksamenvinter2021.Models.Employee;
import com.example.eksamenvinter2021.Models.SubTask;
import com.example.eksamenvinter2021.Models.Task;
import com.example.eksamenvinter2021.Resporsitories.EmployeeRepo;
import com.example.eksamenvinter2021.Resporsitories.LinkTableRepo;
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

import javax.servlet.http.HttpSession;

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
    LinkTableRepo ltr = new LinkTableRepo();
    LoginService ls = new LoginService();

    @GetMapping("/showSubtask/{thisTask}")
    public  String subtasks(@PathVariable("thisTask") int thisTask, Model m){
        int tId = thisTask;

        m.addAttribute("subtasks", sr.getSubtaskInArray());
        m.addAttribute("task", tr.getTaskFromDB(tId));

        sharedTask = tr.getTaskFromDB(thisTask);

        return "subtask_html/showSubtask";
    }

    @GetMapping("/createSubtask/{thisTaskId}")
    public String createSubtask(@PathVariable("thisTaskId") int thisTaskId, Model m){

        Task t = tr.getTaskFromDB(thisTaskId);

        sharedTask = t;
        m.addAttribute("task",t);

        return "subtask_html/newSubtask";
    }

    @GetMapping("/test2/{thisTaskId}")
    public String project(@PathVariable("thisTaskId") int thisTaskId, Model m, HttpSession session) {

        if (ls.notLoggedIn(session)) {
            return  "redirect:/";
        } else {


            Task t = tr.getTaskFromDB(thisTaskId);

            sharedTask = t;


            m.addAttribute("task",t);
            return"frontPage";
        }
    }


    @GetMapping("/test}")
    public String test(Model m){
        //Task t = tr.getTaskFromDB(345);

        //sharedTask = t;
        //m.addAttribute("task",t);
        return "frontPage";
    }




    @PostMapping("/createNewSubtask")
    public String createNewSubtask(WebRequest wr, HttpSession session){
        String title=wr.getParameter("new-subtask-title");
        String description = wr.getParameter("new-subtask-description");

        String estimated_time = wr.getParameter("new-subtask-estimatedTime");

        String timeUsed = wr.getParameter("new-subtask-timeUsed");
        String status = wr.getParameter("new-subtask-status");

        String startDate = wr.getParameter("new-subtask-startDate");
        String endtDate = wr.getParameter("new-subtask-endDate");

        SubTask tempSubtask = ss.createNewSubtask(title,description,estimated_time,timeUsed,status, startDate, endtDate);

        int taskId = sharedTask.getTaskId();
        tempSubtask.setTaskId(taskId);


        sr.insertNewSubtaskToDB(tempSubtask);

        int subtaskId = sr.getSubtaskId(tempSubtask.getSubtaskTitle());

        Employee emp = (Employee) session.getAttribute("employee");
        int employeeId = emp.getEmployeeId();
        sr.insertLinkTTableWithEmployeeAndTaskInDB(employeeId, subtaskId, taskId);


        return "frontPage";
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
        String description = wr.getParameter("new-subtask-description");

        String estimated_time = wr.getParameter("new-subtask-estimatedTime");

        String timeUsed = wr.getParameter("new-subtask-timeUsed");
        String status = wr.getParameter("new-task-status");

        String startDate = wr.getParameter("new-subtask-startDate");
        String endtDate = wr.getParameter("new-subtask-endDate");

        if (title != "" && title != null) {
            editThisSubtask.setSubtaskTitle(title);
        }

        if (description != "" && description != null) {
            editThisSubtask.setSubtaskDescription(description);
        }

        if (estimated_time != "" && estimated_time != null) {
            editThisSubtask.setSubtaskEstimatedTime(estimated_time);
        }

        if (timeUsed != "" && timeUsed != null) {
            editThisSubtask.setSubtaskStatus(timeUsed);
        }

        if (status != "" && status != null) {
            editThisSubtask.setSubtaskStatus(status);
        }

        if (startDate != "" && startDate != null) {
            editThisSubtask.setSubtaskStartDate(startDate);
        }
        if (endtDate != "" && endtDate != null) {
            editThisSubtask.setSubtaskEndDate(endtDate);
        }

        editThisSubtask.setTaskId(sharedTask.getTaskId());

        sr.updateSubtask(editThisSubtask);

        return"subtask_html/updateSubtask";
    }

    @GetMapping("/deleteSubtask/{subtaskId}")
    public String deleteSubtask(@PathVariable("subtaskId") int subtaskId, Model m){
        int id = subtaskId;
        sr.deleteSubtaskFromDB(id);

        return "frontPage";
    }
}
