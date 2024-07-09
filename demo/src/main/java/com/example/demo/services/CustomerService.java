package com.example.demo.services;

import com.example.demo.model.CustomerDTO;

import java.util.List;
import java.util.UUID;

public interface CustomerService {
    List<CustomerDTO> getCustomers();
    CustomerDTO getCustomer(UUID id);
    CustomerDTO saveNewCustomer(CustomerDTO customer);
    void updateCustomerById(UUID customerId, CustomerDTO customer);
    void deleteCustomerById(UUID customerId);

    void updatePatchCustomerById(UUID customerId, CustomerDTO customer);
}
