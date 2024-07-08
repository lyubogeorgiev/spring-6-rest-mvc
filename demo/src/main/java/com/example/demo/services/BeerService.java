package com.example.demo.services;

import com.example.demo.model.Beer;

import java.util.List;
import java.util.UUID;

public interface BeerService {

    Beer getBeerById(UUID id);
    List<Beer> listBeers();

    Beer saveNewBeer(Beer beer);
    void updateBeerById(UUID id, Beer beer);
    void deleteBeerById(UUID id);
    void patchBeerById(UUID id, Beer beer);
}
