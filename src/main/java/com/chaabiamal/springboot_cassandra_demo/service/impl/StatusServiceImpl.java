package com.chaabiamal.springboot_cassandra_demo.service.impl;

import com.chaabiamal.springboot_cassandra_demo.service.StatusService;
import com.chaabiamal.springboot_cassandra_demo.service.dto.StatusDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class StatusServiceImpl implements StatusService {
    @Override
    public StatusDTO save(StatusDTO sDTO) {
        return null;
    }

    @Override
    public StatusDTO update(StatusDTO sDTO) {
        return null;
    }

    @Override
    public Optional<StatusDTO> partialUpdate(int id, StatusDTO sDTO) {
        return Optional.empty();
    }

    @Override
    public Page<StatusDTO> findAll(Pageable pageable) {
        return null;
    }

    @Override
    public Optional<StatusDTO> findOne(UUID id) {
        return Optional.empty();
    }

    @Override
    public void delete(UUID id) {

    }
}
