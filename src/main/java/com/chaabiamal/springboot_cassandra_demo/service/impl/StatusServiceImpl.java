package com.chaabiamal.springboot_cassandra_demo.service.impl;

import com.chaabiamal.springboot_cassandra_demo.model.Composant;
import com.chaabiamal.springboot_cassandra_demo.model.Status;
import com.chaabiamal.springboot_cassandra_demo.repository.StatusRepository;
import com.chaabiamal.springboot_cassandra_demo.service.StatusService;
import com.chaabiamal.springboot_cassandra_demo.service.dto.ComposantDTO;
import com.chaabiamal.springboot_cassandra_demo.service.dto.StatusDTO;
import com.chaabiamal.springboot_cassandra_demo.service.mapper.StatusMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

@Service
@Transactional
public class StatusServiceImpl implements StatusService {

    private final StatusRepository statusRepository;
    private final StatusMapper statusMapper;

    public StatusServiceImpl(StatusRepository statusRepository, StatusMapper statusMapper) {
        this.statusRepository = statusRepository;
        this.statusMapper = statusMapper;
    }

    @Override
    public StatusDTO save(StatusDTO statusDTO) {
        Status status = statusMapper.toEntity(statusDTO);
        status = statusRepository.save(status);
        return statusMapper.toDto(status);
    }

    @Override
    public StatusDTO update(StatusDTO sDTO) {

        Status status =  statusMapper.toEntity(sDTO);
        status = statusRepository.save(status);
        return statusMapper.toDto(status);
    }



    public Optional<StatusDTO> partialUpdate(UUID id, StatusDTO statusDTO) {
        return statusRepository.findByid(id)
                .map(existingStatus -> {
                    if (statusDTO.value() != null) {
                        existingStatus.setValue(statusDTO.value());
                    }
                    if (statusDTO.description()!= null) {
                        existingStatus.setDescription(statusDTO.description());
                    }
                    Status updatedStatus = statusRepository.save(existingStatus);
                    return statusMapper.toDto(updatedStatus);
                });
    }


    @Transactional(readOnly = true)
    public Page<StatusDTO> findAll(Pageable pageable) {
        return (Page<StatusDTO>) statusRepository.findAll(pageable)
                .map(statusMapper::toDto);
    }



    @Transactional(readOnly = true)
    public Optional<StatusDTO> findOne( UUID id) {
        return statusRepository.findById(id)
                .map(statusMapper::toDto);
    }

    public void delete(UUID id) {
        statusRepository.deleteById(id);
    }
}
