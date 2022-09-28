package com.cluster.data.controller;

import com.cluster.data.service.DealService;
import com.cluster.data.service.dto.DealDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

@RestController
@RequestMapping("/api")
public class DealController {

    private static final String VERSION = "/api";

    private final DealService dealService;

    public DealController(DealService dealService) {
        this.dealService = dealService;
    }

    @PostMapping("/deal")
    public ResponseEntity<DealDTO> createDeal(@Valid @RequestBody DealDTO dealDTO) throws URISyntaxException {
        DealDTO savedDeal = dealService.save(dealDTO);
        return ResponseEntity.created(new URI(String.format("%s%s%s", VERSION, "/deal/", savedDeal.getId()))).body(savedDeal);
    }

}
