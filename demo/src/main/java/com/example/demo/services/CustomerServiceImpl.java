package com.example.demo.services;

import com.example.demo.model.Customer;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.time.LocalDate;
import java.util.*;

@Service
public class CustomerServiceImpl implements CustomerService {

    private Map<UUID, Customer> customers;

    public CustomerServiceImpl() {
        this.customers = new HashMap<>();

        Customer customer1 = Customer.builder()
                .id(UUID.randomUUID())
                .name("Customer 1")
                .createdDate(LocalDate.now())
                .lastModifiedDate(LocalDate.now())
                .build();

        Customer customer2 = Customer.builder()
                .id(UUID.randomUUID())
                .name("Customer 2")
                .createdDate(LocalDate.now())
                .lastModifiedDate(LocalDate.now())
                .build();

        Customer customer3 = Customer.builder()
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
    public ArrayList<Customer> getCustomers() {
        ArrayList<Customer> customersList = new ArrayList<>(customers.values());
        return customersList;
    }

    @Override
    public Customer getCustomer(UUID id) {
        return customers.get(id);
    }

    @Override
    public Customer saveNewCustomer(Customer customer) {
        Customer savedCustomer = Customer.builder()
                .id(UUID.randomUUID())
                .name(customer.getName())
                .createdDate(LocalDate.now())
                .lastModifiedDate(LocalDate.now())
                .build();

        customers.put(savedCustomer.getId(), savedCustomer);

        return savedCustomer;
    }

    @Override
    public void updateCustomerById(UUID customerId, Customer customer) {
        Customer existingCustomer = customers.get(customerId);
        existingCustomer.setName(customer.getName());
        existingCustomer.setLastModifiedDate(LocalDate.now());

        customers.put(customer.getId(), existingCustomer);
    }

    @Override
    public void deleteCustomerById(UUID customerId) {
        customers.remove(customerId);
    }

    @Override
    public void updatePatchCustomerById(UUID customerId, Customer customer) {
        Customer existing = customers.get(customerId);

        if (StringUtils.hasText(customer.getName())) {
            existing.setName(customer.getName());
        }
    }
}
