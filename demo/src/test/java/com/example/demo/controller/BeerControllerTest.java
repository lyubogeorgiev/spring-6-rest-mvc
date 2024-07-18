package com.example.demo.controller;

import com.example.demo.entities.Beer;
import com.example.demo.model.BeerDTO;
import com.example.demo.model.BeerStyle;
import com.example.demo.repositories.BeerRepository;
import com.example.demo.services.BeerService;
import com.example.demo.services.BeerServiceImpl;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.JsonPath;
//import org.hibernate.exception.ConstraintViolationException;
import jakarta.validation.ConstraintViolationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static com.example.demo.controller.BeerController.BEER_PATH;
import static com.example.demo.controller.BeerController.BEER_PATH_ID;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.core.Is.is;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

//@WebMvcTest(BeerController.class)
@DataJpaTest
class BeerControllerTest {

//    @Autowired
//    private MockMvc mockMvc;

//    @Autowired
//    ObjectMapper objectMapper;

    @MockBean
    BeerService beerService;

    BeerServiceImpl beerServiceImpl;

    @Captor
    ArgumentCaptor<UUID> uuidArgumentCaptor;

    @Captor
    ArgumentCaptor<BeerDTO> beerArgumentCaptor;

    @Autowired
    private BeerRepository beerRepository;

    @BeforeEach
    void setUp() {
        beerServiceImpl = new BeerServiceImpl();
    }

    @Test
    void testSaveBeerNameTooLong() {

        assertThrows(ConstraintViolationException.class, () -> {
            Beer savedBeer = beerRepository.save(
                Beer.builder()
                        .beerName("My beer name is too long, over 50 symbols 12345678901027638217643872463287643287648327648237648376483263822030040506948271282849374392857489654783583713982374932")
                        .beerStyle(BeerStyle.PALE_ALE)
                        .upc("2345874")
                        .price(new BigDecimal("11.99"))
                        .build());

                beerRepository.flush();
        });
    }

//    @Test
//    void testPatchBeer() throws Exception {
//        BeerDTO beer = beerServiceImpl.listBeers().getFirst();
//
//        Map<String, Object> beerMap = new HashMap<>();
//        beerMap.put("beerName", "New Name");
//
//        mockMvc.perform(patch("/api/v1/beer/" + beer.getId())
//                .accept(MediaType.APPLICATION_JSON)
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(beerMap)))
//                .andExpect(status().isNoContent());
//
//        verify(beerService).patchBeerById(uuidArgumentCaptor.capture(), beerArgumentCaptor.capture());
//
//        assertThat(beer.getId()).isEqualTo(uuidArgumentCaptor.getValue());
//        assertThat(beerMap.get("beerName")).isEqualTo(beerArgumentCaptor.getValue().getBeerName());
//    }

//    @Test
//    void testDeleteBeer() throws Exception {
//        BeerDTO beer = beerServiceImpl.listBeers().get(0);
//
//        mockMvc.perform(delete("/api/v1/beer/" + beer.getId())
//                        .accept(MediaType.APPLICATION_JSON))
//                .andExpect(status().isNoContent());
//
//        verify(beerService).deleteBeerById(uuidArgumentCaptor.capture());
//
//        assertThat(beer.getId()).isEqualTo(uuidArgumentCaptor.getValue());
//    }

//    @Test
//    void testUpdateBeer() throws Exception {
//        BeerDTO beer = beerServiceImpl.listBeers().getFirst();
//
//        given(beerService.updateBeerById(any(), any())).willReturn(Optional.of(beer));
//
//        mockMvc.perform(put(BEER_PATH_ID, beer.getId())
//                .accept(MediaType.APPLICATION_JSON)
//                .contentType(MediaType.APPLICATION_JSON)
//                .content(objectMapper.writeValueAsString(beer)))
//                .andExpect(status().isNoContent());
//
//        verify(beerService).updateBeerById(any(UUID.class), any(BeerDTO.class));
//    }

//    @Test
//    void testCreateNewBeer() throws Exception {
//        BeerDTO beer = beerServiceImpl.listBeers().get(0);
//
//        beer.setVersion(null);
//        beer.setId(null);
//
//        given(beerService.saveNewBeer(any(BeerDTO.class))).willReturn(beerServiceImpl.listBeers().get(1));
//
//        mockMvc.perform(post("/api/v1/beer")
//                .accept(MediaType.APPLICATION_JSON)
//                .contentType(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(beer)))
//                .andExpect(status().isCreated())
//                .andExpect(header().exists("Location"));
//    }

//    @Test
//    void testCreateBeerNullBeerName() throws Exception {
//        BeerDTO beerDTO = BeerDTO.builder().build();
//
//        given(beerService.saveNewBeer(beerDTO)).willReturn(beerServiceImpl.listBeers().getFirst());
//
//        MvcResult mvcResult = mockMvc.perform(post(BeerController.BEER_PATH)
//                .accept(MediaType.APPLICATION_JSON)
//                .contentType(MediaType.APPLICATION_JSON)
//                .content(objectMapper.writeValueAsString(beerDTO)))
//                .andExpect(status().isBadRequest())
//                .andExpect(jsonPath("$.length()", is(6)))
//                .andReturn();
//
//        System.out.println(mvcResult.getResponse().getContentAsString());
//    }

//    @Test
//    void testListBeers() throws Exception {
//        given(beerService.listBeers()).willReturn(beerServiceImpl.listBeers());
//
//        mockMvc.perform(get("/api/v1/beer")
//                .accept(MediaType.APPLICATION_JSON))
//                .andExpect(status().isOk())
//                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
//                .andExpect(jsonPath("$.length()", is(3)));
//
//    }

//    @Test
//    void getBeerByIdNotFound() throws Exception {
//        given(beerService.getBeerById(any(UUID.class))).willReturn(Optional.empty());
//
//        mockMvc.perform(get(BEER_PATH_ID, UUID.randomUUID()))
//                .andExpect(status().isNotFound());
//    }

//    @Test
//    void getBeerById() throws Exception {
//
//        BeerDTO testBeer = beerServiceImpl.listBeers().get(0);
//
//        given(beerService.getBeerById(testBeer.getId())).willReturn(Optional.of(testBeer));
//
//        mockMvc.perform(get("/api/v1/beer/" + testBeer.getId())
//                .accept(MediaType.APPLICATION_JSON))
//                .andExpect(status().isOk())
//                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
//                .andExpect(jsonPath("$.id", is(testBeer.getId().toString())))
//                .andExpect(jsonPath("$.beerName", is(testBeer.getBeerName())));
//    }
}
