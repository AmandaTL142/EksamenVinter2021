package com.example.eksamenvinter2021.Controllers;

import com.example.eksamenvinter2021.Services.EmployeeService;
import com.example.eksamenvinter2021.Services.LoginService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpSession;

@Controller
public class EmployeeController {

    EmployeeService es = new EmployeeService();
    LoginService ls = new LoginService();

    @GetMapping("/employee")
    public String employee(HttpSession session) {
        if (ls.notLoggedIn(session)) {
            return  "redirect:/";
        } else {
            return "employee";
        }
    }
}
