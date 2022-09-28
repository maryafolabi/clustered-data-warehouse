package com.cluster.data.service.mapper;

import com.cluster.data.domain.Deal;
import com.cluster.data.service.dto.DealDTO;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface DealMapper {

    Deal toEntity(DealDTO dto);

    DealDTO toDto(Deal entity);

    List<Deal> toEntity(List<DealDTO> dtoList);

    List<DealDTO> toDto(List<Deal> entityList);
}
