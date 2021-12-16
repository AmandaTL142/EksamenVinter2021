package com.example.eksamenvinter2021.Controllers;

import com.example.eksamenvinter2021.Models.Employee;
import com.example.eksamenvinter2021.Services.EmployeeService;
import com.example.eksamenvinter2021.Services.LoginService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.context.request.WebRequest;

import javax.servlet.http.HttpSession;
import java.sql.SQLException;
import java.util.Objects;

@Controller
public class LoginController {
    //Christian Hundahl

    EmployeeService es = new EmployeeService();

    @GetMapping("/")
    public String login( HttpSession session) {
        if(LoginService.notLoggedIn(session)){
            return "index";
        }
        return "frontPage";
    }

    @PostMapping("/login")
    public String login(WebRequest wr, HttpSession session) throws SQLException {
        int employee_id = Integer.parseInt(wr.getParameter("employee_id"));
        String password = wr.getParameter("password");

        //Evaluer om login-oplysninger matcher database
        if (LoginService.login(employee_id, password)) {
            //Hvis if-statement == true
            //Hent employee fra database
            Employee employee = es.showEmployee(employee_id);
            //Sæt employee som attribut til sessionen
            session.setAttribute("employee", employee);
            return "redirect:/frontPage";
        }
        return "/index";
    }

    @GetMapping("/frontpage")
    public String employee(HttpSession session) {
        if (LoginService.notLoggedIn(session)) {
            return  "redirect:/";
        } else {
            return "frontPage";
        }
    }

    @PostMapping("/logout")
    public String logout(HttpSession session) {
        //Afslutter den nuværende session
        session.invalidate();
        return "redirect:/";
    }
}
