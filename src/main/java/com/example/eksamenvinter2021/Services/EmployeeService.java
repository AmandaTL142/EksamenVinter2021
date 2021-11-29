package com.example.eksamenvinter2021.Services;

import com.example.eksamenvinter2021.Models.Employee;
import com.example.eksamenvinter2021.Resporsitories.EmployeeRepo;

public class EmployeeService {

    EmployeeRepo employeeRepo = new EmployeeRepo();

    public void createNewEmployee(String employeeName, String competence, String role, String password) {
        Employee employee = new Employee(employeeName, competence, role, password);
        employeeRepo.insertEmployeeIntoDatabase(employee);
    }

    public void updateEmployee(Employee employee, String employeeName, String competence, String role, String password) {
        employee.setEmployeeName(employeeName);
        employee.setCompetence(competence);
        employee.setRole(role);
        employee.setPassword(password);
        employeeRepo.updateEmployeeInDatabase(employee);
    }

    public void deleteCustomerFromDatabase(int employeeId){
        employeeRepo.deleteEmployeeFromDatabase(employeeId);
    }


    public Employee showEmployee (int employeeId) {
        return employeeRepo.getEmployeeFromDatabase(employeeId);
    }

}
