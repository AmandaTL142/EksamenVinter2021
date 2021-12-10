package com.example.eksamenvinter2021.Controllers;

import com.example.eksamenvinter2021.Resporsitories.ProjectRepo;
import com.example.eksamenvinter2021.Services.LoginService;
import com.example.eksamenvinter2021.Services.ProjectService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.HttpSession;

@Controller
public class GanttController {
    ProjectRepo pr = new ProjectRepo();
    ProjectService ps = new ProjectService();
    LoginService ls = new LoginService();

    @GetMapping("/gantt")
    public String gant(Model model, HttpSession session) {
        if (ls.notLoggedIn(session)) {
            return "redirect:/";
        } else {
            model.addAttribute("projects", pr.getProjectsInArrayForGantt());
            return "/Gantt-chart";
        }



    /*
    //Denne virker
    @GetMapping("/gantt/{thisProject}")
    public String ganttProject(@PathVariable("thisProject") int thisProject, Model model, HttpSession session) {
        if (ls.notLoggedIn(session)) {
            return  "redirect:/";
        } else {
            //int id = Integer.parseInt(thisProject);
            model.addAttribute("Project", ps.getProjectObject(thisProject));
            return "/Gantt-chart";
        }
    }

     */
    }
}


