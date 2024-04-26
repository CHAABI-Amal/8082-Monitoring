package com.chaabiamal.springboot_cassandra_demo.service;

import com.chaabiamal.springboot_cassandra_demo.service.dto.historiqueComposantDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;
import java.util.UUID;

public interface historiqueComposantService {

        historiqueComposantDTO save(historiqueComposantDTO kioskDTO);

        historiqueComposantDTO update(historiqueComposantDTO kioskDTO);

        Optional<historiqueComposantDTO> partialUpdate(UUID id, historiqueComposantDTO kioskDTO);

        Page<historiqueComposantDTO> findAll(Pageable pageable);

        Optional<historiqueComposantDTO> findOne(UUID id);

        void delete(UUID id);


    }
