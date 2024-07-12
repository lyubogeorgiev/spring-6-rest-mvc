package com.example.demo.controller;

import com.example.demo.entities.Customer;
import com.example.demo.mappers.CustomerMapper;
import com.example.demo.mappers.CustomerMapperImpl;
import com.example.demo.model.CustomerDTO;
import com.example.demo.repositories.CustomerRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
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
    @Autowired
    private CustomerMapper customerMapper;

    @Transactional
    @Rollback
    @Test
    void testDeleteByOdFound() {
        Customer customer = customerRepository.findAll().getFirst();

        ResponseEntity responseEntity = customerController.deleteCustomerById(customer.getId());
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(204));

        assertThat(customerRepository.findById(customer.getId()).isEmpty());
    }

    @Transactional
    @Rollback
    @Test
    void testListAllCustomers() {
        customerController.deleteAllCustomers();
        List<CustomerDTO> customerDTOList = customerController.getCustomers();

        assertThat(customerDTOList.size()).isEqualTo(3);
    }

    @Test
    void testDeleteNotFound() {
        assertThrows(NotFoundException.class, () -> customerController.deleteCustomerById(UUID.randomUUID()));
    }

    @Test
    void testUpdateNotFound() {
        assertThrows(NotFoundException.class, () -> {
            customerController.updateCustomer(UUID.randomUUID(), CustomerDTO.builder().build());
        });
    }

    @Test
    void testGetByIdNotFound() {

        assertThrows(NotFoundException.class, () -> {

            CustomerDTO customer = customerController.getCustomer(UUID.randomUUID());
        });

    }

    @Rollback
    @Transactional
    @Test
    void testUpdateExistingBeer() {
        Customer customer = customerRepository.findAll().getFirst();
        CustomerDTO customerDTO = customerMapper.customerToCustomerDTO(customer);

        customerDTO.setId(null);
        customerDTO.setVersion(null);
        final String customerName = "UPDATED";
        customerDTO.setName(customerName);

        ResponseEntity responseEntity = customerController.updateCustomer(customer.getId(), customerDTO);
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(204));

        Customer updatedCustomer = customerRepository.findById(customer.getId()).get();
        assertThat(updatedCustomer.getName()).isEqualTo(customerName);
    }

    @Transactional
    @Rollback
    @Test
    void saveNewBeerTest() {
        CustomerDTO customerDTO = CustomerDTO.builder()
                .name("TEST")
                .build();

        ResponseEntity responseEntity = customerController.createCustomer(customerDTO);

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(201));
        assertThat(responseEntity.getHeaders().getLocation()).isNotNull();

        String[] locationUUID = responseEntity.getHeaders().getLocation().getPath().split("/");
        UUID savedUUID = UUID.fromString(locationUUID[locationUUID.length - 1]);

        Customer customer = customerRepository.findById(savedUUID).get();
        assertThat(customer).isNotNull();
    }

    @Test
    @Transactional
    @Rollback
    void testListAllEmptyList() {
        customerRepository.deleteAll();
        List<CustomerDTO> customerDTOList = customerController.getCustomers();
        assertThat(customerDTOList.size()).isEqualTo(0);
    }

    @Test
    void testListAll() {
        List<CustomerDTO> customerDTOList = customerController.getCustomers();

        assertThat(customerDTOList.size()).isEqualTo(3);
    }

    @Test
    void testGetById() {
        Customer customer = customerRepository.findAll().getFirst();
        CustomerDTO customerDTO = customerController.getCustomer(customer.getId());

        assertThat(customerDTO).isNotNull();
    }
}