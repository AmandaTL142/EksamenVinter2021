package com.example.eksamenvinter2021.Controllers;

import com.example.eksamenvinter2021.Resporsitories.ProjectRepo;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
@Controller
public class GanttController {
    ProjectRepo pr = new ProjectRepo();

    @GetMapping("/gantt")
    public String gant(Model model) {

        model.addAttribute("projects", pr.getProjectsInArrayForGantt());
        return "/Gantt-chart";
    }
}


