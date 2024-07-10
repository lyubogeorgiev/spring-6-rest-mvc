package com.example.demo.bootstrap;

import com.example.demo.entities.Beer;
import com.example.demo.entities.Customer;
import com.example.demo.model.BeerStyle;
import com.example.demo.model.CustomerDTO;
import com.example.demo.repositories.BeerRepository;
import com.example.demo.repositories.CustomerRepository;
import com.example.demo.services.BeerService;
import com.example.demo.services.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;

@Component
@RequiredArgsConstructor
public class BootstrapData implements CommandLineRunner {
    private final BeerRepository beerRepository;
    private final CustomerRepository customerRepository;

    @Override
    public void run(String... args) throws Exception {
        loadBeerData();
        loadCustomerData();
    }

    private void loadCustomerData() {

        if (customerRepository.count() == 0) {
            Customer customer1 = Customer.builder()
                    .name("John Doe")
                    .createdDate(LocalDateTime.now())
                    .lastModifiedDate(LocalDateTime.now())
                    .build();

            Customer customer2 = Customer.builder()
                    .name("Allisa Smith")
                    .createdDate(LocalDateTime.now())
                    .lastModifiedDate(LocalDateTime.now())
                    .build();

            Customer customer3 = Customer.builder()
                    .name("Jeniffer Paul")
                    .createdDate(LocalDateTime.now())
                    .lastModifiedDate(LocalDateTime.now())
                    .build();

            customerRepository.saveAll(Arrays.asList(customer1, customer2, customer3));
        }
    }

    private void loadBeerData() {

        if (beerRepository.count() == 0) {
            Beer beer1 = Beer.builder()
                    .beerName("Dead Horse")
                    .beerStyle(BeerStyle.AMBER_ALE)
                    .upc("123345")
                    .price(new BigDecimal("11.99"))
                    .quantityOnHand(123)
                    .createdDate(LocalDateTime.now())
                    .updatedDate(LocalDateTime.now())
                    .build();

            Beer beer2 = Beer.builder()
                    .beerName("Porcupine")
                    .beerStyle(BeerStyle.PILSENER)
                    .upc("34521")
                    .price(new BigDecimal("9.99"))
                    .quantityOnHand(23)
                    .createdDate(LocalDateTime.now())
                    .updatedDate(LocalDateTime.now())
                    .build();

            Beer beer3 = Beer.builder()
                    .beerName("Johnnys IPA")
                    .beerStyle(BeerStyle.IPA)
                    .upc("32091")
                    .price(new BigDecimal("12.99"))
                    .quantityOnHand(543)
                    .createdDate(LocalDateTime.now())
                    .updatedDate(LocalDateTime.now())
                    .build();

            beerRepository.save(beer1);
            beerRepository.save(beer2);
            beerRepository.save(beer3);
        }
    }
}
