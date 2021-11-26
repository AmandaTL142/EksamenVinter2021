package com.example.eksamenvinter2021.Services;

import com.example.eksamenvinter2021.Models.Employee;
import com.example.eksamenvinter2021.Resporsitories.EmployeeRepo;

public class EmployeeService {

    EmployeeRepo employeeRepo = new EmployeeRepo();

    public void createNewEmployee(String employeeName, String competence) {
        Employee employee = new Employee(employeeName, competence);
        employeeRepo.insertEmployeeIntoDatabase(employee);
    }

    public void updateEmployee(Employee employee, String employeeName, String competence) {
        employee.setEmployeeName(employeeName);
        employee.setCompetence(competence);
        employeeRepo.updateEmployeeInDatabase(employee);
    }

    public void deleteCustomerFromDatabase(int employeeId){
        employeeRepo.deleteEmployeeFromDatabase(employeeId);
    }


    public Employee showEmployee (int employeeId) {
        return employeeRepo.getEmployeeFromDatabase(employeeId);
    }

}
