package com.example.eksamenvinter2021.Services;

import com.example.eksamenvinter2021.Models.Customer;
import com.example.eksamenvinter2021.Models.Project;
import com.example.eksamenvinter2021.Resporsitories.CustomerRepo;

import java.util.List;

//CAS

public class CustomerService {

    CustomerRepo customerRepo = new CustomerRepo();


    public Customer createNewCustomer(String customerName) {
        Customer customer = new Customer(customerName);
        return customer;
    }

    public void updateCustomer(Customer customer, String customerName) {
        customer.setCustomerName(customerName);
        customerRepo.updateCustomerInDatabase(customer);
    }

    public void deleteCustomer(int customerId){
        customerRepo.deleteCustomerFromDatabase(customerId);
    }

    public Customer getCustomerObject(int customerId) {
        return customerRepo.getCustomerFromDatabase(customerId);
    }

    public List<Customer> getAllCustomers()
    {
        List<Customer> customerList = customerRepo.allCustomers();
        return customerList;
    }

    public int getCustomerIdFromDatabase(String name) {
        return customerRepo.getCustomerIdFromDatabase(name);
    }

}
