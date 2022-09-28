package com.cluster.data.service;

import com.cluster.data.service.dto.DealDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

public interface DealService {

    DealDTO save(DealDTO dealDTO);

    List<DealDTO> batchSave(List<DealDTO> dealDTOList);

    Optional<DealDTO> findByUniqueId(UUID uniqueId);

    Page<DealDTO> findAll(Pageable pageable);

}
