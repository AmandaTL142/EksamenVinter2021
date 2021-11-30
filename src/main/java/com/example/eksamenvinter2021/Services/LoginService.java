package com.example.eksamenvinter2021.Services;

import com.example.eksamenvinter2021.Utility.JDBC;

import javax.servlet.http.HttpSession;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginService {
    //LoginService should contact database via repository, not in this class
    //This will be implemented when EmployeeRepo finished
    public static boolean login(int employee_id, String password) {
        int checkId = 0;
        String checkPassword = "";
        try {
            PreparedStatement stmt = JDBC.getConnection().prepareStatement("SELECT * FROM employees WHERE employee_id= ? AND password= ?;");
            stmt.setInt(1, employee_id);
            stmt.setString(2, password);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                checkId = rs.getInt("employee_id");
                checkPassword = rs.getString("password");
            }
            if (checkId == employee_id) {
                if (checkPassword.equals(password)) {
                    return true;
                }
            }
        } catch (SQLException e) {
            System.out.println("Something went wrong");
            System.out.println(e.getMessage());
        }
        return false;
    }

    public static boolean isManager(int userId) {
        try {
            PreparedStatement stmt = JDBC.getConnection().prepareStatement("SELECT * FROM employees where employee_id=?;");
            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                String role = String.valueOf(rs.getInt(1));//Henviser til kolonne hvor 'rolle' står som MANAGER
                if (role.matches("MANAGER")) {
                    return true;
                }
            }

        } catch (SQLException e) {
            System.out.println("Something went wrong");
            System.out.println(e.getMessage());
        }
        return false;
    }

    public static boolean validLogin(HttpSession session) {
        //All GetMappings implements validLogin method to find if user is logged in
        return session.getAttribute("id") != null;
    }

    public static boolean validManagerLogin(HttpSession session) {
        return (boolean) session.getAttribute("manager");
    }
}
