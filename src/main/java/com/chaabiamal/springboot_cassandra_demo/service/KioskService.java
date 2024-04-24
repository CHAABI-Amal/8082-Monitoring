package com.chaabiamal.springboot_cassandra_demo.service;

import com.chaabiamal.springboot_cassandra_demo.service.dto.KioskDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;

public interface KioskService {

    KioskDTO save(KioskDTO kioskDTO);

    KioskDTO update(KioskDTO kioskDTO);

    Optional<KioskDTO> partialUpdate(KioskDTO kioskDTO);

    Page<KioskDTO> findAll(Pageable pageable);

    Optional<KioskDTO> findOne(UUID id);

    void delete(UUID id);


}
