package com.example.demo.controller;

import com.example.demo.model.Customer;
import com.example.demo.services.CustomerService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/customers")
public class CustomerController {

    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @PutMapping("/{customerId}")
    public ResponseEntity updateCustomer(@PathVariable UUID customerId, @RequestBody Customer customer) {

        customerService.updateCustomerById(customerId, customer);

         return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @PostMapping
    public ResponseEntity createCustomer(@RequestBody Customer customer) {

        System.out.println(customer);
        Customer savedCustomer = customerService.saveNewCustomer(customer);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Location", String.format("/api/v1/customers/%s", savedCustomer.getId()));

        return  new ResponseEntity(headers, HttpStatus.CREATED);
    }

    @GetMapping
    public List<Customer> getCustomers() {
        return customerService.getCustomers();
    }

    @GetMapping("/{customerId}")
    public Customer getCustomer(@PathVariable("customerId") UUID customerId) {
        return customerService.getCustomer(customerId);
    }
}
