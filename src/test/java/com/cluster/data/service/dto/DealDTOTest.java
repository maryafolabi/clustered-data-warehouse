package com.cluster.data.service.dto;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class DealDTOTest {
    @Test
    void testConstructor() {
        DealDTO actualDealDTO = new DealDTO();
        BigDecimal valueOfResult = BigDecimal.valueOf(42L);
        actualDealDTO.setAmount(valueOfResult);
        actualDealDTO.setTimestamp(null);
        actualDealDTO.setFromCurrency("GBP");
        actualDealDTO.setToCurrency("GBP");
        assertSame(valueOfResult, actualDealDTO.getAmount());
        assertNull(actualDealDTO.getTimestamp());
        assertEquals("GBP", actualDealDTO.getFromCurrency());
        assertEquals("GBP", actualDealDTO.getToCurrency());
    }
}
