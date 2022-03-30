package com.leb.app.service;

import com.leb.app.service.dto.PointDTO;

import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface PointService {

    PointDTO save(PointDTO pointDTO);

    Optional<PointDTO> partialUpdate(PointDTO pointDTO);

    Page<PointDTO> findAll(Pageable pageable);

    Optional<PointDTO> findOne(Long id);

    void delete(Long id);

    PointDTO prepareNewPoint(PointDTO point, Long userId);

    List<PointDTO> findAllList();
}
