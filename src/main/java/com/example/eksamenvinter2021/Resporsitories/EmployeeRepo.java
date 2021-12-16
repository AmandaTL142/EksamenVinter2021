package com.example.eksamenvinter2021.Resporsitories;

import com.example.eksamenvinter2021.Models.Employee;
import com.example.eksamenvinter2021.Utility.ConnectionManager;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class EmployeeRepo {
    public void insertEmployeeIntoDatabase(Employee employee) {
        try {
            PreparedStatement stmt = ConnectionManager.getConnection().prepareStatement
                    ("INSERT INTO heroku_7aba49c42d6c0f0.employee (`name`,`competence`) VALUES (?,?);");
            stmt.setString(1, employee.getEmployeeName());
            stmt.setString(2, employee.getCompetence());
            stmt.executeUpdate();

        } catch (Exception e) {
            System.out.println("Employee could not be inserted into database");
            System.out.println(e.getMessage());
        }
    }
    public Employee getEmployeeFromDatabase(int id) {//TODO: Se igennem og ryd op for unødvendig kode
        Employee employee = new Employee();
        String name = "";
        String competence = "";
        String password = "";
        String role = "";
        try {
            PreparedStatement stmt = ConnectionManager.getConnection().prepareStatement(
                    "SELECT * FROM heroku_7aba49c42d6c0f0.employees WHERE employee_id=?;");
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                name = rs.getString("name");
                competence = rs.getString("competence");
                password = rs.getString("password");
                role = rs.getString("role");
            }
            employee = new Employee(name, password, competence, role);
            employee.setEmployeeId(id); //Indsat af Amanda

        } catch(SQLException e){
            System.out.println("Couldn't find the employee with id: " + id + " from the database");
            System.out.println(e.getMessage());
        }
        return employee;
    }

    public void deleteEmployeeFromDatabase(int id) {
        try {
            PreparedStatement stmt = ConnectionManager.getConnection().prepareStatement
                    ("DELETE FROM `heroku_7aba49c42d6c0f0`.`employee` WHERE (`employee_id` = '" + id + "');");
            stmt.executeUpdate();
        } catch (Exception e) {
            System.out.println("Couldn't delete the employee with id: " + id + " from database");
            System.out.println(e.getMessage());
        }

    }

    public void updateEmployeeInDatabase(Employee employee) {
        try {
            PreparedStatement stmt = ConnectionManager.getConnection().prepareStatement
                    ("UPDATE `heroku_7aba49c42d6c0f0`.`employee` SET `name` = ?, `competence` = ? WHERE (`employee_id` = ?, `competence` = ? ;");

            stmt.setString(1, employee.getEmployeeName());
            stmt.setString(2,employee.getCompetence());
            stmt.executeUpdate();

        } catch (Exception e) {
            System.out.println("Couldn't update Employee: " + employee.getEmployeeName() + '(' + employee.getEmployeeId() + ')' + " in database");
            System.out.println(e.getMessage());
        }

    }

    //Lavet af Amanda
    public ArrayList<Employee> getAllEmployeesFromDatabase() {
        Employee employee = new Employee();
        ArrayList<Employee> employeeList = new ArrayList<>();
        try {
            PreparedStatement stmt = ConnectionManager.getConnection().prepareStatement(
                    "SELECT * FROM heroku_7aba49c42d6c0f0.employees;");
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("employee_id");;
                String name = rs.getString("name");
                String competence = rs.getString("competence");
                String password = rs.getString("password");
                String role = rs.getString("role");
                employee = new Employee(name, password, competence, role);
                employee.setEmployeeId(id);
                employeeList.add(employee);
            }

        } catch(SQLException e){
            System.out.println("Couldn't find employees in database");
            System.out.println(e.getMessage());
        }
        return employeeList;
    }

    //Login methods
    public static boolean login(int employee_id, String password) {
        int checkId = 0;
        String checkPassword = "";
        try {
            PreparedStatement stmt = ConnectionManager.getConnection().prepareStatement("SELECT * FROM employees WHERE employee_id= ? AND password= ?;");
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
            PreparedStatement stmt = ConnectionManager.getConnection().prepareStatement("SELECT * FROM employees where employee_id=?;");
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

}
