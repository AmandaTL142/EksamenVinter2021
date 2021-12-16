package com.example.eksamenvinter2021.Services;

import com.example.eksamenvinter2021.Resporsitories.EmployeeRepo;
import javax.servlet.http.HttpSession;

public class LoginService {
    //Christian Hundahl

    public static boolean login(int employee_id, String password) {
        return EmployeeRepo.login(employee_id, password);
    }

    //Evaluerer om bruger er manager
    //Benyttes ikke i projektet, men kan finde anvendelse på senere tidspunkt
    public static boolean isManager(int userId) {
        return EmployeeRepo.isManager (userId);
    }

    public static boolean notLoggedIn(HttpSession session) {
        return session.getAttribute("employee") == null;
    }
}
