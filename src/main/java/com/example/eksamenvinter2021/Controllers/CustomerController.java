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
    public String Customer(HttpSession session, Model model) {
        if (ls.notLoggedIn(session)) {
            return "reirect:/index";
        } else {
            Employee employee = (Employee) session.getAttribute("employee");
            if (employee.getRole().equals("MANAGER")) {
                model.addAttribute("customerList", cs.getAllCustomers());
                return "customer/Customer";
            } else {
                return "error";
            }
        }
    }

    @GetMapping("/newCustomer")
    public String newProject(HttpSession session) {
        if (ls.notLoggedIn(session)) {
            return  "redirect:/";
        } else {
            Employee employee = (Employee) session.getAttribute("employee");
            if (employee.getRole().equals("MANAGER")){
                return "customer/newCustomer";
            }
            else{
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

        return "customer/Customer";
    }

    @GetMapping("/editCustomer/{thisCustomer}")
    public String editProjectGetProject(@PathVariable("thisCustomer") int thisCustomer, Model model, HttpSession session) {
        if (ls.notLoggedIn(session)) {
            return  "redirect:/";
        } else {
            editThisCustomer = cs.getCustomerObject(thisCustomer);
            model.addAttribute("customer", cs.getCustomerObject(thisCustomer));

            //Checks if the user is a manager and thus allowed to access the site
            Employee employee = (Employee) session.getAttribute("employee");
            if (employee.getRole().equals("MANAGER")){
                return "customer/editCustomer";
            }
            else{
                return "error";
            }
        }
    }
    @RequestMapping("/editCustomer")
    public String editCustomer(WebRequest webr) {
        String title = webr.getParameter("name");

        if (title!="" && title!=null){
            editThisCustomer.setCustomerName(title);
        }
        //Update customer in DB
        cr.updateCustomerInDatabase(editThisCustomer);

        return "customer/Customer";
    }

    @GetMapping("/deleteCustomer/{customerId}")
    public String deleteCustomer(@PathVariable("customerId") String customerId, HttpSession session){

        //Checks if the user is a manager and thus allowed to delete a Customer
        Employee employee = (Employee) session.getAttribute("employee");
        if (employee.getRole().equals("MANAGER")){
            int id = Integer.parseInt(customerId);
            cs.deleteCustomer(id);
            return "customer/Customer";
        }
        else{
            return "error";
        }
    }

}
