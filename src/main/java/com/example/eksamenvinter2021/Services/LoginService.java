package com.example.eksamenvinter2021.Services;

import com.example.eksamenvinter2021.Models.Project;
import com.example.eksamenvinter2021.Resporsitories.EmployeeRepo;
import com.example.eksamenvinter2021.Utility.JDBC;
import javax.servlet.http.HttpSession;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

public class LoginService {
    //Christian Hundahl
    //TODO:
    //LoginService should contact database via repository, not in this class
    //This will be implemented when EmployeeRepo finished
    public static boolean login(int employee_id, String password) {
        return EmployeeRepo.login(employee_id, password);
    }

    public static boolean isManager(int userId) {
        return EmployeeRepo.isManager (userId);
    }

    public static boolean validLogin(HttpSession session) {
        //All GetMappings implements validLogin method to find if user is logged in
        return session.getAttribute("id") != null;
    }

    public static boolean validManagerLogin(HttpSession session) {
        return (boolean) session.getAttribute("manager");
    }

    public static boolean notLoggedIn(HttpSession session) {
        return session.getAttribute("employee") == null;
    }
}
