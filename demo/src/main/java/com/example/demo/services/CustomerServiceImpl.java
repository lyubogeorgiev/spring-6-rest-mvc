package com.example.demo.services;

import com.example.demo.mappers.CustomerMapper;
import com.example.demo.mappers.CustomerMapperImpl;
import com.example.demo.model.CustomerDTO;
import com.example.demo.repositories.CustomerRepository;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.time.LocalDate;
import java.util.*;
import java.util.concurrent.atomic.AtomicReference;

@Service
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;
    private final CustomerMapper customerMapper;
    private Map<UUID, CustomerDTO> customers;

    public CustomerServiceImpl(CustomerRepository customerRepository, CustomerMapperImpl customerMapperImpl, CustomerMapper customerMapper) {
        this.customerMapper = customerMapper;
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
        this.customerRepository = customerRepository;
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
    public Optional<CustomerDTO> updateCustomerById(UUID customerId, CustomerDTO customer) {
        CustomerDTO existingCustomer = customers.get(customerId);
        existingCustomer.setName(customer.getName());
        existingCustomer.setLastModifiedDate(LocalDate.now());

        CustomerDTO updatedCustomer = customers.put(customer.getId(), existingCustomer);
        return Optional.ofNullable(updatedCustomer);
    }

    @Override
    public Boolean deleteCustomerById(UUID customerId) {
        if (customerRepository.existsById(customerId)) {
            customerRepository.deleteById(customerId);
            return true;
        }
//        customers.remove(customerId);
        return false;
    }

    @Override
    public Optional<CustomerDTO> updatePatchCustomerById(UUID customerId, CustomerDTO customer) {
        AtomicReference<Optional<CustomerDTO>> atomicReference = new AtomicReference<>();

        CustomerDTO existing = customers.get(customerId);

        customerRepository.findById(customerId).ifPresentOrElse(foundCustomer -> {
            if (StringUtils.hasText(customer.getName())) {
                existing.setName(customer.getName());
            }

            atomicReference.set(Optional.of(customerMapper.customerToCustomerDTO(customerRepository.save(foundCustomer))));
        }, () -> atomicReference.set(Optional.empty()));

        return atomicReference.get();

    }

    @Override
    public void deleteAllCustomers() {
        customers.clear();
    }
}
