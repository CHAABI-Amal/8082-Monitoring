package com.chaabiamal.springboot_cassandra_demo.service;

import com.chaabiamal.springboot_cassandra_demo.service.dto.ComposantDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ComposantService {
    ///*************************************************
    ResponseEntity<String> findByIdCheckaddInfo2(UUID composantId,
                                                 ComposantDTO composantDTO);

    ///*************************************************
    List<ComposantDTO> fetchStudents();

    ///*************************************************
    List<ComposantDTO> fetchStudents(int nbr);

    ResponseEntity<ComposantDTO> findByIdCheckStatus(UUID composantId, int status);

    ResponseEntity<String> findByIdCheckaddInfo(UUID composantId,
                                                int value);

    //****************************
    Optional<ComposantDTO> findById(UUID id);

    ComposantDTO save(ComposantDTO kioskDTO);

    ComposantDTO update(ComposantDTO kioskDTO);

    Optional<ComposantDTO> partialUpdate(UUID id,ComposantDTO kioskDTO);

    Page<ComposantDTO> findAll(Pageable pageable);

    Optional<ComposantDTO> findOne(UUID id);

    void delete(UUID id);

}
