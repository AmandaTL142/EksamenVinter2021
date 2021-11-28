package com.example.eksamenvinter2021.Controllers;

import com.example.eksamenvinter2021.Services.ProjectService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@Controller
public class ProjectController {

    ProjectService ps = new ProjectService();

    @GetMapping("/project/{thisProject}")//Path variables: /{}
    public String project(@PathVariable("thisProject") String thisProject, Model model) {
        //int id = Integer.parseInt(thisProject);
        model.addAttribute("ProjectID", ps.showProject(15));
        return "project";
    }


}
