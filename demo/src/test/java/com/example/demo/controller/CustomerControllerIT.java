package com.example.demo.controller;

import com.example.demo.entities.Customer;
import com.example.demo.model.CustomerDTO;
import com.example.demo.repositories.CustomerRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class CustomerControllerIT {

    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    CustomerController  customerController;

    @Transactional
    @Rollback
    @Test
    void testListAllCustomers() {
        customerController.deleteAllCustomers();
        List<CustomerDTO> customerDTOList = customerController.getCustomers();

        assertThat(customerDTOList.size()).isEqualTo(3);
    }

    @Test
    void testGetByIdNotFound() {

        assertThrows(NotFoundException.class, () -> {

            CustomerDTO customer = customerController.getCustomer(UUID.randomUUID());
        });

    }

    @Test
    void testGetById() {
        Customer customer = customerRepository.findAll().getFirst();
        CustomerDTO customerDTO = customerController.getCustomer(customer.getId());

        assertThat(customerDTO).isNotNull();
    }
}