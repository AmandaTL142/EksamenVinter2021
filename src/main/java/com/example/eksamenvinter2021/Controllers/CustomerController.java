package com.example.eksamenvinter2021.Controllers;

import com.example.eksamenvinter2021.Models.Customer;
import com.example.eksamenvinter2021.Models.Employee;
import com.example.eksamenvinter2021.Models.Project;
import com.example.eksamenvinter2021.Resporsitories.CustomerRepo;
import com.example.eksamenvinter2021.Services.CustomerService;
import com.example.eksamenvinter2021.Services.LoginService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.context.request.WebRequest;

import javax.servlet.http.HttpSession;

@Controller
public class CustomerController {

    CustomerService cs = new CustomerService();
    CustomerRepo cr = new CustomerRepo();
    LoginService ls = new LoginService();

    @GetMapping("/newCustomer")
    public String newCustomer(HttpSession session) {
        if (ls.notLoggedIn(session)) {
            return "reirect:/index";
        } else {
            Employee employee = (Employee) session.getAttribute("employee");
            if (employee.getRole().equals("MANAGER")) {
                return "newCustomer";
            } else {
                return "error";
            }
        }
    }

    @PostMapping("/createNewCustomer")
    public String createNewCustomer(WebRequest webr) {

        String costumerName = webr.getParameter("customer-name-input");


        //Create customer-object
        Customer currentCustomer = cs.createNewCustomer(costumerName);

        //Add customer to DB
        cr.insertCustomerIntoDatabase(currentCustomer);

        System.out.println(currentCustomer.toString());

        return "confirmationPage";
    }
}
