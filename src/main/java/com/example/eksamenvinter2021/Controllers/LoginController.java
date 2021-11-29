package com.example.eksamenvinter2021.Controllers;

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
            session.setAttribute("id", employee_id);
            if (LoginService.isManager(employee_id)) {
                session.setAttribute("manager", true);//Tjekker om personens id er tilknyttet en Manager rolle
            }
            return "redirect:/show-projects";
            //Hvordan genkender siden en person som manager eller medarbejder hhv?
            //Objekt med userId og isManager
            //Vis forskellige sider til manager og medarbejder
            //Hvis ingen aktiv session --> websiden vises ikke, henviser til login
            //Alle sider implementerer metode der tjekker om logget ind
            //Mulighed: Implementer en variation af HTTP-session interface via klasse?

        }
        return "index";
    }
}
