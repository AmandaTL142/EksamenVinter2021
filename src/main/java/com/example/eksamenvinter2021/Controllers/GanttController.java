package com.example.eksamenvinter2021.Controllers;

import com.example.eksamenvinter2021.Models.Employee;
import com.example.eksamenvinter2021.Resporsitories.ProjectRepo;
import com.example.eksamenvinter2021.Services.LoginService;
import com.example.eksamenvinter2021.Services.ProjectService;
import com.example.eksamenvinter2021.Services.TaskService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpSession;

@Controller
public class GanttController {
    ProjectRepo pr = new ProjectRepo();
    ProjectService ps = new ProjectService();
    LoginService ls = new LoginService();
    TaskService ts = new TaskService();

    @GetMapping("/gantt")
    public String gant(Model model, HttpSession session) {
        if (ls.notLoggedIn(session)) {
            return "redirect:/";
        } else {
            model.addAttribute("projects", ps.getAllProjectsAndSubprojects());

            return "/Gantt-chart";
        }

    }
}


