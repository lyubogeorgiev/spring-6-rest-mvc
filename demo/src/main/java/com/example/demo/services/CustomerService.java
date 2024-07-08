package com.example.demo.services;

import com.example.demo.model.Customer;

import java.util.List;
import java.util.UUID;

public interface CustomerService {
    List<Customer> getCustomers();
    Customer getCustomer(UUID id);
    Customer saveNewCustomer(Customer customer);
    void updateCustomerById(UUID customerId, Customer customer);
}
