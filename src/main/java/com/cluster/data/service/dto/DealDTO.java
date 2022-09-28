package com.cluster.data.service.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class DealDTO implements Serializable {

    private Long id;

    private UUID uniqueId;

    private String fromCurrency;

    private String toCurrency;

    private Instant timestamp;

    private BigDecimal amount;

}
