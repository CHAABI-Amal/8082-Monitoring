package com.chaabiamal.springboot_cassandra_demo.service.impl;

import com.chaabiamal.springboot_cassandra_demo.model.Composant;
import com.chaabiamal.springboot_cassandra_demo.model.historiqueComposant;
import com.chaabiamal.springboot_cassandra_demo.repository.historiqueComposantRepository;
import com.chaabiamal.springboot_cassandra_demo.service.dto.historiqueComposantDTO;
import com.chaabiamal.springboot_cassandra_demo.service.historiqueComposantService;
import com.chaabiamal.springboot_cassandra_demo.service.mapper.historiqueComposantMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class historiqueComposantServiceImpl implements historiqueComposantService {

    private final historiqueComposantRepository historiquecomposantRepository;
    private final historiqueComposantMapper historiquecomposantMapper;

    public historiqueComposantServiceImpl(historiqueComposantRepository historiqueRepo, historiqueComposantMapper historiqueMapper) {
        this.historiquecomposantRepository = historiqueRepo;
        this.historiquecomposantMapper = historiqueMapper;
    }

    @Override
    public historiqueComposantDTO save(historiqueComposantDTO historiqueComposantDTO) {

        historiqueComposant historiqueComposant1 = (historiqueComposant) historiquecomposantMapper.toEntity(historiqueComposantDTO);
        historiqueComposant1.setId(UUID.randomUUID());
        historiqueComposant1 = historiquecomposantRepository.save(historiqueComposant1);
        return historiquecomposantMapper.toDto(historiqueComposant1);
    }

    @Override
    public historiqueComposantDTO update(historiqueComposantDTO historiqueComposantDTO) {

        historiqueComposant composant = (historiqueComposant) historiquecomposantMapper.toEntity(historiqueComposantDTO);
        composant = historiquecomposantRepository.save(composant);
        return historiquecomposantMapper.toDto(composant);
    }



    @Override
    public Optional<historiqueComposantDTO> partialUpdate(UUID id, historiqueComposantDTO historiqueComposantDTO) {
        return historiquecomposantRepository.findById(id)
                .map(existingComposant -> {
                    if (historiqueComposantDTO.id() != null) {
                        existingComposant.setId(historiqueComposantDTO.id());
                    }

                    if (historiqueComposantDTO.composantId() != null) {
                        existingComposant.setComposantId(historiqueComposantDTO.composantId());
                    }
                    if (historiqueComposantDTO.datetime()!= null) {
                        existingComposant.setDatetime(historiqueComposantDTO.datetime());
                    }
                    if (historiqueComposantDTO.status()!= null) {
                        existingComposant.setStatus(historiqueComposantDTO.status());
                    }
                    if (historiqueComposantDTO.value()!= null) {
                        existingComposant.setValue(historiqueComposantDTO.value());
                    }
                    historiqueComposant updatedComposant = historiquecomposantRepository.save(existingComposant);
                    return historiquecomposantMapper.toDto(updatedComposant);
                });
    }

    @Override
    public Page<historiqueComposantDTO> findAll(Pageable pageable) {

        return (Page)historiquecomposantRepository.findAll(pageable).map(historiquecomposantMapper::toDto);
    }

    @Override
    public Optional<historiqueComposantDTO> findOne(UUID id) {

        return historiquecomposantRepository.findById(id).map(historiquecomposantMapper::toDto);
    }

    @Override
    public void delete(UUID id) {

        historiquecomposantRepository.deleteByid(id);
    }
}