package com.example.eksamenvinter2021.Services;

import com.example.eksamenvinter2021.Models.Customer;
import com.example.eksamenvinter2021.Resporsitories.CustomerRepo;

public class CustomerService {

    CustomerRepo customerRepo = new CustomerRepo();

    public Customer createNewCustomer(String customerName) {
        Customer customer = new Customer(customerName);
        //customerRepo.insertCustomerIntoDatabase(customer);
        return customer;
    }

    public void updateCustomer(Customer customer, String customerName) {
        customer.setCustomerName(customerName);
        customerRepo.updateCustomerInDatabase(customer);
    }

    public void deleteCustomer(int customerId){
        customerRepo.deleteCustomerFromDatabase(customerId);
    }


    public Customer showCustomer (int customerId) {
        return customerRepo.getCustomerFromDatabase(customerId);
    }

}
