package com.chaabiamal.springboot_cassandra_demo.service.impl;

import com.chaabiamal.springboot_cassandra_demo.model.Composant;
import com.chaabiamal.springboot_cassandra_demo.repository.ComposantRepository;
import com.chaabiamal.springboot_cassandra_demo.service.ComposantService;
import com.chaabiamal.springboot_cassandra_demo.service.dto.ComposantDTO;
import com.chaabiamal.springboot_cassandra_demo.service.mapper.ComposantMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;


@Service
public class ComposantServiceImpl implements ComposantService {

    private  ComposantRepository composantRepository;
    private ComposantMapper composantMapper;


    @Override
    public ComposantDTO save(ComposantDTO composantDTO) {

        Composant composant = (Composant) composantMapper.toEntity(composantDTO);
        composant.setId(UUID.randomUUID());
        composant = composantRepository.save(composant);
        return composantMapper.toDto(composant);
    }

    @Override
    public ComposantDTO update(ComposantDTO composantDTO) {

        Composant composant = (Composant) composantMapper.toEntity(composantDTO);
        composant = composantRepository.save(composant);
        return composantMapper.toDto(composant);
    }

    public Optional<ComposantDTO> partialUpdate(UUID id, ComposantDTO composantDTO) {
        return composantRepository.findById(id)
                .map(existingComposant -> {
                    if (composantDTO.id() != null) {
                        existingComposant.setId(composantDTO.id());
                    }
                    if (composantDTO.lastStatus() != null) {
                        existingComposant.setLastStatus(composantDTO.lastStatus());
                    }
                    if (composantDTO.isDeleted()) {
                        existingComposant.setDeleted(composantDTO.isDeleted());
                    }
                    if (composantDTO.status()!= null) {
                        existingComposant.setStatus(composantDTO.status());
                    }
                    if (composantDTO.instanceName() != null) {
                        existingComposant.setInstanceName(composantDTO.instanceName());
                    }
                    if (composantDTO.additionalInfo() != null) {
                        existingComposant.setAdditionalInfo(composantDTO.additionalInfo());
                    }
                    if (composantDTO.componentTypeId() != null) {
                        existingComposant.setComponentTypeId(composantDTO.componentTypeId());
                    }
                    if (composantDTO.instanceCode() != null) {
                        existingComposant.setInstanceCode(composantDTO.instanceCode());
                    }
                    if (composantDTO.kioskId() != null) {
                        existingComposant.setKioskId(composantDTO.kioskId());
                    }
                    if (composantDTO.modelNumber() != null) {
                        existingComposant.setModelNumber(composantDTO.modelNumber());
                    }
                    if (composantDTO.componentStatus() != null) {
                        existingComposant.setComponentStatus(composantDTO.componentStatus());
                    }
                    if (composantDTO.statusDate() != null) {
                        existingComposant.setStatusDate(composantDTO.statusDate());
                    }
                    if (composantDTO.createdDate() != null) {
                        existingComposant.setCreatedDate(composantDTO.createdDate());
                    }
                    if (composantDTO.modifiedDate() != null) {
                        existingComposant.setModifiedDate(composantDTO.modifiedDate());
                    }

                    Composant updatedComposant = composantRepository.save(existingComposant);
                    return composantMapper.toDto(updatedComposant);
                });
    }


    @Override
    public Page<ComposantDTO> findAll(Pageable pageable) {

        return (Page)composantRepository.findAll(pageable).map(composantMapper::toDto);
    }

    @Override
    public Optional<ComposantDTO> findOne(UUID id) {

        return composantRepository.findById(id).map(composantMapper::toDto);
    }

    @Override
    public void delete(UUID id) {

        composantRepository.deleteById(id);
    }
}
