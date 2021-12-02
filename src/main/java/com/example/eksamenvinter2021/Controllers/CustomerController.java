package com.example.eksamenvinter2021.Controllers;

import com.example.eksamenvinter2021.Models.Customer;
import com.example.eksamenvinter2021.Models.Project;
import com.example.eksamenvinter2021.Resporsitories.CustomerRepo;
import com.example.eksamenvinter2021.Services.CustomerService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.context.request.WebRequest;

@Controller
public class CustomerController {

    CustomerService cs = new CustomerService();
    CustomerRepo cr = new CustomerRepo();

    @GetMapping("/newCustomer")
    public String newCustomer() {
        return "newCustomer";
    }

    @PostMapping("/createNewCustomer")
    public String createNewCustomer(WebRequest webr) {
        String costumerName = webr.getParameter("costumer-name-input");

        //Create project-object
        Customer currentCustomer = cs.createNewCustomer(costumerName);

        //Add project to DB
        cr.insertCustomerIntoDatabase(currentCustomer);

        //Get project id
        int customerId = cr.getCustomerIdFromDatabase(costumerName);
        currentCustomer.setCustomerId(customerId);

        return "redirect:/newCustomer";
    }
}
