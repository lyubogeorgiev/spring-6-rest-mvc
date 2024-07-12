package com.example.demo.services;

import com.example.demo.model.CustomerDTO;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CustomerService {
    List<CustomerDTO> getCustomers();
    Optional<CustomerDTO> getCustomer(UUID id);
    CustomerDTO saveNewCustomer(CustomerDTO customer);
    Optional<CustomerDTO> updateCustomerById(UUID customerId, CustomerDTO customer);
    Boolean deleteCustomerById(UUID customerId);

    Optional<CustomerDTO> updatePatchCustomerById(UUID customerId, CustomerDTO customer);
    void deleteAllCustomers();
}
