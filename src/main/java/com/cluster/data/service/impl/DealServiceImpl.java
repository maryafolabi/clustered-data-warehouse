package com.cluster.data.service.impl;

import com.cluster.data.domain.Deal;
import com.cluster.data.repository.DealRepository;
import com.cluster.data.service.DealService;
import com.cluster.data.service.dto.DealDTO;
import com.cluster.data.service.error.DealValidator;
import com.cluster.data.service.error.ErrorValidator;
import com.cluster.data.service.error.ExceptionValidation;
import com.cluster.data.service.mapper.DealMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.*;

@Service
public class DealServiceImpl implements DealService {

    private final DealValidator dealValidator;

    private final DealRepository dealRepository;

    private final DealMapper dealMapper;

    public DealServiceImpl( DealRepository dealRepository, DealValidator dealValidator, DealMapper dealMapper) {
        this.dealRepository = dealRepository;
        this.dealValidator = dealValidator;
        this.dealMapper = dealMapper;
    }

    @Override
    public DealDTO save(DealDTO dealDTO) {
        List<ErrorValidator> errorValidators = dealValidator.validateNewDeal(dealDTO);
        if(!CollectionUtils.isEmpty(errorValidators)) throw new ExceptionValidation(errorValidators);
        Deal deal = dealMapper.toEntity(dealDTO);
        deal.setUniqueId(UUID.randomUUID());
        deal = dealRepository.save(deal);
        return dealMapper.toDto(deal);
    }

    @Override
    public List<DealDTO> batchSave(List<DealDTO> dealDTOList){
        if(CollectionUtils.isEmpty(dealDTOList)) return List.of();
        List<DealDTO> responseDeal = new ArrayList<>();
        for(int counter = 0; counter < dealDTOList.size(); counter++) {
            try{
                DealDTO dealDTO = dealDTOList.get(counter);
                responseDeal.add(save(dealDTO));
            } catch (ExceptionValidation exceptionValidation) {
                exceptionValidation.getErrorValidators().add(0, dealValidator.addListingError(counter) );
                throw exceptionValidation;
            }
        }
        return responseDeal;
    }

    @Override
    public Optional<DealDTO> findByUniqueId(UUID uniqueId) {
        return dealRepository.getDealByUniqueId(uniqueId).map(dealMapper::toDto);
    }

    @Override
    public Page<DealDTO> findAll(Pageable pageable) {
        return dealRepository.findAll(pageable).map(dealMapper::toDto);
    }
}
