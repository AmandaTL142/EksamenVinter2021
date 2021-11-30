package com.example.eksamenvinter2021.Resporsitories;

import com.example.eksamenvinter2021.Models.Employee;
import com.example.eksamenvinter2021.Utility.JDBC;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class EmployeeRepo {
    public void insertEmployeeIntoDatabase(Employee employee) {
        try {
            PreparedStatement stmt = JDBC.getConnection().prepareStatement
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
            PreparedStatement stmt = JDBC.getConnection().prepareStatement(
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

        } catch(SQLException e){
            System.out.println("Couldn't find the employee with id: " + id + " from the database");
            System.out.println(e.getMessage());
        }
        return employee;
    }

    public void deleteEmployeeFromDatabase(int id) {
        try {
            PreparedStatement stmt = JDBC.getConnection().prepareStatement
                    ("DELETE FROM `heroku_7aba49c42d6c0f0`.`employee` WHERE (`employee_id` = '" + id + "');");
            stmt.executeUpdate();
        } catch (Exception e) {
            System.out.println("Couldn't delete the employee with id: " + id + " from database");
            System.out.println(e.getMessage());
        }

    }

    public void updateEmployeeInDatabase(Employee employee) {
        try {
            PreparedStatement stmt = JDBC.getConnection().prepareStatement
                    ("UPDATE `heroku_7aba49c42d6c0f0`.`employee` SET `name` = ?, `competence` = ? WHERE (`employee_id` = ?, `competence` = ? ;");

            stmt.setString(1, employee.getEmployeeName());
            stmt.setString(2,employee.getCompetence());
            stmt.executeUpdate();

        } catch (Exception e) {
            System.out.println("Couldn't update Employee: " + employee.getEmployeeName() + '(' + employee.getEmployeeId() + ')' + " in database");
            System.out.println(e.getMessage());
        }

    }
}
