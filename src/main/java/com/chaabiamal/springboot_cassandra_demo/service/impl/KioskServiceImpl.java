package com.chaabiamal.springboot_cassandra_demo.service.impl;

import com.chaabiamal.springboot_cassandra_demo.service.KioskService;
import com.chaabiamal.springboot_cassandra_demo.service.dto.KioskDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.util.Optional;
import java.util.UUID;

@Service
public class KioskServiceImpl implements KioskService {

    @Override
    public KioskDTO save(KioskDTO kioskDTO) {
        return null;
    }

    @Override
    public KioskDTO update(KioskDTO kioskDTO) {
        return null;
    }

    @Override
    public Optional<KioskDTO> partialUpdate(KioskDTO kioskDTO) {
        return Optional.empty();
    }

    @Override
    public Page<KioskDTO> findAll(Pageable pageable) {
        return null;
    }

    @Override
    public Optional<KioskDTO> findOne(UUID id) {
        return Optional.empty();
    }

    @Override
    public void delete(UUID id) {

    }
}
