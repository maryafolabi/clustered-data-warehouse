package com.cluster.data.domain;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

public class DealTest {
    @Test
    void testConstructor() {
        Deal actualDeal = new Deal();
        BigDecimal valueOfResult = BigDecimal.valueOf(42L);
        actualDeal.setAmount(valueOfResult);
        actualDeal.setTimestamp(null);
        actualDeal.setFromCurrency(null);
        actualDeal.setId(123L);
        actualDeal.setToCurrency(null);
        assertSame(valueOfResult, actualDeal.getAmount());
        assertNull(actualDeal.getTimestamp());
        assertNull(actualDeal.getFromCurrency());
        assertEquals(123L, actualDeal.getId().longValue());
        assertNull(actualDeal.getToCurrency());
    }
}
