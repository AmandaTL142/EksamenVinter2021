package com.example.eksamenvinter2021.Controllers;

import com.example.eksamenvinter2021.Models.Customer;
import com.example.eksamenvinter2021.Models.Employee;
import com.example.eksamenvinter2021.Resporsitories.CustomerRepo;
import com.example.eksamenvinter2021.Services.CustomerService;
import com.example.eksamenvinter2021.Services.LoginService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.request.WebRequest;

import javax.servlet.http.HttpSession;

@Controller
public class CustomerController {

    CustomerService cs = new CustomerService();
    CustomerRepo cr = new CustomerRepo();
    LoginService ls = new LoginService();
    Customer editThisCustomer = new Customer();


    @GetMapping("/Customer")
    public String Customer(HttpSession session, Model model, int customer) {
        if (ls.notLoggedIn(session)) {
            return "reirect:/";
        } else {
            Employee employee = (Employee) session.getAttribute("employee");
            model.addAttribute("customer", cs.showCustomer(customer));
            if (employee.getRole().equals("MANAGER")) {
                return "Customer";
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

        return "frontPage";
    }

    //Denne virker
    @RequestMapping("/editCustomer")
    public String editCustomer(WebRequest webr) {
        String title = webr.getParameter("name");

        if (title!="" && title!=null){
            editThisCustomer.setCustomerName(title);
        }
        //Update customer in DB
        cr.updateCustomerInDatabase(editThisCustomer);

        return "frontpage";
    }

    @GetMapping("/deleteCustomer/{customerId}")
    public String deleteCustomer(@PathVariable("customerId") String customerId, HttpSession session){

        //Checks if the user is a manager and thus allowed to delete a Customer
        Employee employee = (Employee) session.getAttribute("employee");
        if (employee.getRole().equals("MANAGER")){
            int id = Integer.parseInt(customerId);
            cs.deleteCustomer(id);
            return "frontPage";
        }
        else{
            return "error";
        }
    }

}
