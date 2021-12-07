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
    //Her laver vi noget log-in logik
    //Benyt sessions
    EmployeeService es = new EmployeeService();
    LoginService ls = new LoginService();

    @GetMapping("/")
    public String login() {
        return "index";
    }

    @PostMapping("/login")
    public String login(WebRequest wr, HttpSession session) throws SQLException {
        int employee_id = Integer.parseInt(Objects.requireNonNull(wr.getParameter("employee_id")));
        String password = wr.getParameter("password");

        //Evaluer hvis login matcher database
        boolean validPass = LoginService.login(employee_id, password);

        //Sæt en bruger som enten Manager eller Medarbejder allerede ved login
        if (validPass) {
            Employee employee = es.showEmployee(employee_id);
            session.setAttribute("employee", employee);
            return "redirect:/employee"; //Mangler projekt-id for at vise korrekt projekt
            //Vis forskellige sider til manager og medarbejder
            //Hvis ingen aktiv session --> websiden vises ikke, henviser til login
            //Alle sider implementerer metode der tjekker om logget ind
            //Mulighed: Implementer en variation af HTTP-session interface via klasse?

        }
        return "index";
    }

    @GetMapping("/employee")
    public String employee(HttpSession session) {
        if (ls.notLoggedIn(session)) {
            return  "redirect:/";
        } else {
            return "employee";
        }
    }


}
