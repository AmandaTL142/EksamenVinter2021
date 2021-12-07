package com.example.eksamenvinter2021.Controllers;

import com.example.eksamenvinter2021.Services.LoginService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

@Controller
public class TaskController {

    @GetMapping("/task")
    public String index (HttpSession session){
        if (LoginService.notLoggedIn(session)) {
            return  "redirect:/";
        } else {
            return "task";
        }

    }
}
