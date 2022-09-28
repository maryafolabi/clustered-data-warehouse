package com.cluster.data.controller;

import com.cluster.data.service.DealService;
import com.cluster.data.service.dto.DealDTO;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

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

    @PostMapping("/deals")
    public ResponseEntity<List<DealDTO>> createDeals(@Valid @RequestBody List<DealDTO> dealDTOs) throws URISyntaxException {
        List<DealDTO> savedDeals = dealService.batchSave(dealDTOs);
        if(CollectionUtils.isEmpty(savedDeals)) ResponseEntity.ok();
        return ResponseEntity
                .created(new URI(String.format("%s%s%s", VERSION, "/deal/", savedDeals.get(0).getUniqueId())))
                .body(savedDeals);
    }

    @GetMapping("/deal/{unique-id}")
    public ResponseEntity<DealDTO> fetchDeal( @PathVariable("unique-id") UUID uniqueId) {
        Optional<DealDTO> optionalDealDTO = dealService.findByUniqueId(uniqueId);
        return ResponseEntity.ok(optionalDealDTO.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND)));
    }

    @GetMapping("/deals")
    public ResponseEntity<Page<DealDTO>> fetchAllDeals(@PageableDefault(value = 20) Pageable pageable) {
        return ResponseEntity.ok().body(dealService.findAll(pageable));
    }

}
