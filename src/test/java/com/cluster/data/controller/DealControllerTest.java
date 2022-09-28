package com.cluster.data.controller;

import com.cluster.data.ClusteredDataApplication;
import com.cluster.data.domain.Deal;
import com.cluster.data.repository.DealRepository;
import com.cluster.data.service.dto.DealDTO;
import com.cluster.data.service.mapper.DealMapper;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.Currency;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(classes = ClusteredDataApplication.class)
@AutoConfigureMockMvc
public class DealControllerTest {

    @Autowired
    DealRepository dealRepository;

    @Autowired
    DealMapper dealMapper;

    @Autowired
    private MockMvc restDealMockMvc;

    private Deal deal;

    private static final ObjectMapper mapper = createObjectMapper();


    public static byte[] convertObjectToJsonBytes(Object object) throws IOException {
        return mapper.writeValueAsBytes(object);
    }

    private static ObjectMapper createObjectMapper() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRITE_DURATIONS_AS_TIMESTAMPS, false);
        mapper.setSerializationInclusion(JsonInclude.Include.NON_EMPTY);
        mapper.registerModule(new JavaTimeModule());
        return mapper;
    }


    private final static BigDecimal DEFAULT_AMOUNT = BigDecimal.ONE;
    private final static Currency DEFAULT_FROM_CURRENCY = Currency.getInstance("AUD");
    private final static Currency DEFAULT_TO_CURRENCY = Currency.getInstance("USD");
    private final static Instant DEFAULT_TIMESTAMP = Instant.now();

    @BeforeEach
    public void initTest() {
        deal = createDeal();
    }

    public static Deal createDeal() {
        Deal deal = new Deal();
        deal.setUniqueId(UUID.randomUUID());
        deal.setAmount(DEFAULT_AMOUNT);
        deal.setFromCurrency(DEFAULT_FROM_CURRENCY);
        deal.setToCurrency(DEFAULT_TO_CURRENCY);
        deal.setTimestamp(DEFAULT_TIMESTAMP);
        return deal;
    }

    @Test
    @Transactional
    void createOneDeal() throws Exception {
        int databaseSizeBeforeCreate = dealRepository.findAll().size();
        // Create the Deal
        DealDTO dealDTO = dealMapper.toDto(deal);
        restDealMockMvc
            .perform(post("/api/deal").contentType(MediaType.APPLICATION_JSON)
                .content(convertObjectToJsonBytes(dealDTO)))
            .andExpect(status().isCreated());

        // Validate the Deal in the database
        List<Deal> dealList = dealRepository.findAll();
        assertThat(dealList).hasSize(databaseSizeBeforeCreate + 1);
        Deal testDeal = dealList.get(dealList.size() - 1);
        assertThat(testDeal.getAmount().intValue()).isEqualTo(DEFAULT_AMOUNT.intValue());
        assertThat(testDeal.getTimestamp()).isEqualTo(DEFAULT_TIMESTAMP);
        assertThat(testDeal.getFromCurrency()).isEqualTo(DEFAULT_FROM_CURRENCY);
        assertThat(testDeal.getToCurrency()).isEqualTo(DEFAULT_TO_CURRENCY);
    }

    @Test
    @Transactional
    void createDeals() throws Exception {
        int databaseSizeBeforeCreate = dealRepository.findAll().size();
        // Create the Deal
        List<DealDTO> dealDTOs = dealMapper.toDto(List.of(deal, deal));
        restDealMockMvc
            .perform(post("/api/deals").contentType(MediaType.APPLICATION_JSON)
                .content(convertObjectToJsonBytes(dealDTOs)))
            .andExpect(status().isCreated());

        // Validate the Deal in the database
        List<Deal> dealList = dealRepository.findAll();
        assertThat(dealList).hasSize(databaseSizeBeforeCreate + 2);
        Deal testDeal = dealList.get(dealList.size() - 1);
        assertThat(testDeal.getAmount().intValue()).isEqualTo(DEFAULT_AMOUNT.intValue());
        assertThat(testDeal.getTimestamp()).isEqualTo(DEFAULT_TIMESTAMP);
        assertThat(testDeal.getFromCurrency()).isEqualTo(DEFAULT_FROM_CURRENCY);
        assertThat(testDeal.getToCurrency()).isEqualTo(DEFAULT_TO_CURRENCY);
    }

    @Test
    @Transactional
    void getAllDeals() throws Exception {
        // Initialize the database
        dealRepository.saveAndFlush(deal);

        // Get all the deals
        restDealMockMvc
            .perform(get("/api/deals" + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.content.[*].fromCurrency").value(hasItem(DEFAULT_FROM_CURRENCY.getCurrencyCode())))
            .andExpect(jsonPath("$.content.[*].toCurrency").value(hasItem(DEFAULT_TO_CURRENCY.getCurrencyCode())));
    }

    @Test
    @Transactional
    void badDeal() throws Exception {
        restDealMockMvc
            .perform(post("/api/deal").contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isInternalServerError());
    }

    @Test
    void getDealByUniqueId() throws Exception {
        // Initialize the database
        dealRepository.saveAndFlush(deal);

        // Get all the deals
        restDealMockMvc
            .perform(get("/api/deal/"+deal .getUniqueId().toString()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.fromCurrency").value(DEFAULT_FROM_CURRENCY.getCurrencyCode()))
            .andExpect(jsonPath("$.toCurrency").value(DEFAULT_TO_CURRENCY.getCurrencyCode()));
    }

}
