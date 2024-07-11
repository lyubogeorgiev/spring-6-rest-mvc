package com.example.demo.services;

import com.example.demo.model.CustomerDTO;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.time.LocalDate;
import java.util.*;

@Service
public class CustomerServiceImpl implements CustomerService {

    private Map<UUID, CustomerDTO> customers;

    public CustomerServiceImpl() {
        this.customers = new HashMap<>();

        CustomerDTO customer1 = CustomerDTO.builder()
                .id(UUID.randomUUID())
                .name("Customer 1")
                .createdDate(LocalDate.now())
                .lastModifiedDate(LocalDate.now())
                .build();

        CustomerDTO customer2 = CustomerDTO.builder()
                .id(UUID.randomUUID())
                .name("Customer 2")
                .createdDate(LocalDate.now())
                .lastModifiedDate(LocalDate.now())
                .build();

        CustomerDTO customer3 = CustomerDTO.builder()
                .id(UUID.randomUUID())
                .name("Customer 3")
                .createdDate(LocalDate.now())
                .lastModifiedDate(LocalDate.now())
                .build();

        customers.put(customer1.getId(), customer1);
        customers.put(customer2.getId(), customer2);
        customers.put(customer3.getId(), customer3);
    }

    @Override
    public ArrayList<CustomerDTO> getCustomers() {
        ArrayList<CustomerDTO> customersList = new ArrayList<>(customers.values());
        return customersList;
    }

    @Override
    public Optional<CustomerDTO> getCustomer(UUID id) {
        return Optional.of(customers.get(id));
    }

    @Override
    public CustomerDTO saveNewCustomer(CustomerDTO customer) {
        CustomerDTO savedCustomer = CustomerDTO.builder()
                .id(UUID.randomUUID())
                .name(customer.getName())
                .createdDate(LocalDate.now())
                .lastModifiedDate(LocalDate.now())
                .build();

        customers.put(savedCustomer.getId(), savedCustomer);

        return savedCustomer;
    }

    @Override
    public void updateCustomerById(UUID customerId, CustomerDTO customer) {
        CustomerDTO existingCustomer = customers.get(customerId);
        existingCustomer.setName(customer.getName());
        existingCustomer.setLastModifiedDate(LocalDate.now());

        customers.put(customer.getId(), existingCustomer);
    }

    @Override
    public void deleteCustomerById(UUID customerId) {
        customers.remove(customerId);
    }

    @Override
    public void updatePatchCustomerById(UUID customerId, CustomerDTO customer) {
        CustomerDTO existing = customers.get(customerId);

        if (StringUtils.hasText(customer.getName())) {
            existing.setName(customer.getName());
        }
    }

    @Override
    public void deleteAllCustomers() {
        customers.clear();
    }
}
