package com.cluster.data.service.impl;

import com.cluster.data.domain.Deal;
import com.cluster.data.repository.DealRepository;
import com.cluster.data.service.DealService;
import com.cluster.data.service.dto.DealDTO;
import com.cluster.data.service.mapper.DealMapper;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class DealServiceImpl implements DealService {

    private final DealRepository dealRepository;

    private final DealMapper dealMapper;

    public DealServiceImpl( DealRepository dealRepository, DealMapper dealMapper) {
        this.dealRepository = dealRepository;
        this.dealMapper = dealMapper;
    }

    @Override
    public DealDTO save(DealDTO dealDTO) {
        Deal deal = dealMapper.toEntity(dealDTO);
        deal.setUniqueId(UUID.randomUUID());
        deal = dealRepository.save(deal);
        return dealMapper.toDto(deal);
    }


}
