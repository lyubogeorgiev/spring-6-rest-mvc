package com.example.demo.services;

import com.example.demo.model.BeerDTO;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface BeerService {

    Optional<BeerDTO> getBeerById(UUID id);
    List<BeerDTO> listBeers();

    BeerDTO saveNewBeer(BeerDTO beer);
    Optional<BeerDTO> updateBeerById(UUID id, BeerDTO beer);
    void deleteBeerById(UUID id);
    void patchBeerById(UUID id, BeerDTO beer);
}
