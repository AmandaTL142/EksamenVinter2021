package com.example.eksamenvinter2021.Controllers;

import com.example.eksamenvinter2021.Models.Employee;
import com.example.eksamenvinter2021.Models.Project;
import com.example.eksamenvinter2021.Models.Subproject;
import com.example.eksamenvinter2021.Models.Task;
import com.example.eksamenvinter2021.Resporsitories.EmployeeRepo;
import com.example.eksamenvinter2021.Resporsitories.LinkTableRepo;
import com.example.eksamenvinter2021.Resporsitories.ProjectRepo;
import com.example.eksamenvinter2021.Resporsitories.TaskRepo;
import com.example.eksamenvinter2021.Services.*;
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
    SubprojectService sps = new SubprojectService();

    //Subproject objects
    Subproject sharedSubproject = new Subproject();

    //Project objects
    Project sharedProject = new Project();
    Project editThisProject = new Project();

    //Employee
    EmployeeRepo er = new EmployeeRepo();
    EmployeeService es = new EmployeeService();

    //Methods
    LinkTableRepo ltr = new LinkTableRepo();
    LoginService ls = new LoginService();



    /*thisProject, der står i curly brackets, indikere, at der skal opgives et projectID for at kunne benytte showTask*/
    @GetMapping("/showTask/{thisProject}")
    public String tasks(@PathVariable("thisProject") int thisProject, Model m, HttpSession session){

        if (ls.notLoggedIn(session)) {
            return  "redirect:/";
        } else {
            Employee employee = (Employee) session.getAttribute("employee");
            if (employee.getRole().equals("EMPLOYEE")){

                int pID = thisProject;

                m.addAttribute("tasks", ts.getAllTasksInArray());
                m.addAttribute("project", ps.getProjectObject(pID));


                /*Sharedproject er i første omgang et tomt projekt, men ved brug af getProjectObject, hentes
                der et allerede eksisterende projekt fra Databasen. Hvilket projekt der hentes er baseret på
                hvilket projectID der indtastes. Dette project tages med videre, til @Posttmapping "createNewTask*/
                sharedProject = ps.getProjectObject(thisProject);

                return "task_html/showTask";
            }
            else{
                return "error";
            }
        }

    }


    //Ændret af Amanda
    //man skal indtaste et projectId, for at komme på det rigtige projekt
    @GetMapping("/createTask/{thisProjectId}")
    public String project(@PathVariable("thisProjectId") int thisProjectId, Model m, HttpSession session) {

        if (ls.notLoggedIn(session)) {
            return  "redirect:/";
        } else {

            /*Her defineres et project ud fra hvilket ID, der indtastes i url*/
            Project p = ps.getProjectObject(thisProjectId);
            /*Her connnectes dette project til sharedProject, så projektet er det samme som det project der vises  */
            sharedProject = p;


            m.addAttribute("project",p);
            return"task_html/newTask";
        }
    }

    @PostMapping("/createNewTask")
    //For at få adgang til denne, skal man igennem showProject.Html
    public String createNewTask(WebRequest wr, HttpSession session){
        //Først fortælles, at der ønskes input fra bruger via browser
        String title = wr.getParameter("new-task-title");
        String description = wr.getParameter("new-task-description");

        String estimated_time = wr.getParameter("new-task-estimatedTime");

        String timeUsed = wr.getParameter("new-task-timeUsed");
        String status = wr.getParameter("new-task-status");
        String startDate = wr.getParameter("new-subtask-startDate");
        String endtDate = wr.getParameter("new-subtask-endDate");


        //Create task-object
        Task tempTask = ts.createNewTask(title,description,estimated_time,timeUsed,status, startDate, endtDate);

        /*Her sætter vi projectID, som skal være det ID, som kommer fra projektet der hedder sharedProject*/
        int projectID = sharedProject.getProjectId();
        /*Ved at defineret projectID, som værende ID fra sharedProject, er det muligt at tage dette ID med videre,
        når der oprettes en task.
        På denne måde oprettes en task, som med det samme får et projectID tilknyttet. Dette gør, at så snart
        task opretes tilhører den med det samme et project*/

        tempTask.setProjectId(projectID);

        /*her gøres der brug af metoden insertNewTaskToDB, som er en metode fra Task repo.
        Metoden indsætter de givende informationer ind til DB.
        I parantesen siges der, at disse værdier, som er indtastet af brugeren, i task-objektet
        og på denne måde instanzieres objektet*/


        tr.insertNewTaskToDB(tempTask);
        int taskID = tr.getTaskID(tempTask.getTitle());
        Employee emp = (Employee) session.getAttribute("employee");
        int employeeID = emp.getEmployeeId();
        tr.insertTaskToLinktable(employeeID, taskID, projectID);

        return "frontPage";
    }


    @GetMapping("/createTaskFromSubproject/{thisSubprojectId}")
    public String v(@PathVariable("thisSubprojectId") int thisSubprojectId, Model m, HttpSession session) {
        //Her gemmer vi projektID, som en int

        if (ls.notLoggedIn(session)) {
            return  "redirect:/";
        } else {
            //ønsker at hente projektet, specifik dets ID, så vi kan connecte task til dette Project
            Subproject sp = sps.getSubprojectObject(thisSubprojectId);

            sharedSubproject = sp;

            int projectId = sp.getProjectId();
            Project p = pr.getProjectFromDatabase(projectId);

            m.addAttribute("project",p);
            return"task_html/newTask2";
        }
    }

    //Lavet af Amanda
    @PostMapping("/createNewTaskFromSubproject")
    public String createNewTaskFromSubproject(WebRequest wr, HttpSession session){
        //Først fortælles, at der ønskes input fra bruger via browser
        String title = wr.getParameter("new-task-title");
        String description = wr.getParameter("new-task-description");

        String estimated_time = wr.getParameter("new-task-estimatedTime");

        String timeUsed = wr.getParameter("new-task-timeUsed");
        String status = wr.getParameter("new-task-status");
        String startDate = wr.getParameter("new-subtask-startDate");
        String endtDate = wr.getParameter("new-subtask-endDate");


        Task tempTask = ts.createNewTask(title,description,estimated_time,timeUsed,status, startDate, endtDate);

        int projectID = sharedSubproject.getProjectId();
        tempTask.setProjectId(projectID);

        int subprojectId = sharedSubproject.getSubprojectId();
        tempTask.setSubprojectId(subprojectId);

        tr.insertNewTaskToDB(tempTask);
        int taskID = tr.getTaskID(tempTask.getTitle());
        Employee emp = (Employee) session.getAttribute("employee");
        int employeeID = emp.getEmployeeId();
        tr.insertTaskToLinktableWithSubproject(employeeID, taskID, projectID, subprojectId);

        return "frontPage";
    }

    @GetMapping("/editTask/{thisTask}")
    public String editTask(@PathVariable("thisTask") int thisTask, Model m, HttpSession session){

        if (ls.notLoggedIn(session)) {
            return  "redirect:/";
        } else {
            int id = thisTask;
            edithThisTask = ts.getTaskObject(id);

            m.addAttribute("tasks",edithThisTask);
            m.addAttribute("project",sharedProject);

            return "task_html/editTask";
        }

    }

    @PostMapping("/editTaskChanges")
    public String editTask(WebRequest wr){

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

        edithThisTask.setProjectId(sharedProject.getProjectId());

        ts.updateTask(edithThisTask);

        return "task_html/editTask";
    }


    @GetMapping("/deleteTask/{taskId}")
    public String deleteTask(@PathVariable("taskId") int taskId, HttpSession session) {

        if (ls.notLoggedIn(session)) {
            return  "redirect:/";
        } else {
            ts.deleteTask(taskId);

            return "frontPage";
        }

    }


    @GetMapping("/getTaskForEmployee")

    //TODO skal den implementeres eller gør chrisitan det???
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

        return "frontPage";
    }


}

//transform string til datetime-java-format (loacltime) parToDateTime
//localTime time = LocalTime.parse("den aflveret String fra browser")
//evt. lav validation
//System.out.println(LocalTime.parse(estimated_time));


   /* TODO-liste:
        derefter skal tasken knyttes til project, sådan at man kun kan oprette en task
        inde i et projekt og dermed automatisk er koblet til et project*/
