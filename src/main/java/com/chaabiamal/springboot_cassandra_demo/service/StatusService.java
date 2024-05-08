package com.chaabiamal.springboot_cassandra_demo.service;

import com.chaabiamal.springboot_cassandra_demo.service.dto.StatusDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;
import java.util.UUID;

public interface StatusService {

    StatusDTO save( StatusDTO sDTO);

    StatusDTO update( StatusDTO sDTO);

    Optional< StatusDTO> partialUpdate(int id,  StatusDTO sDTO);

    Page< StatusDTO> findAll(Pageable pageable);

    Optional< StatusDTO> findOne(UUID id);

    void delete(UUID id);


}

