package com.chaabiamal.springboot_cassandra_demo.service.impl;

import com.chaabiamal.springboot_cassandra_demo.Exception.ResourceNotFoundException;
import com.chaabiamal.springboot_cassandra_demo.model.Composant;
import com.chaabiamal.springboot_cassandra_demo.model.historiqueComposant;
import com.chaabiamal.springboot_cassandra_demo.repository.ComposantRepository;
import com.chaabiamal.springboot_cassandra_demo.repository.historiqueComposantRepository;
import com.chaabiamal.springboot_cassandra_demo.service.ComposantService;
import com.chaabiamal.springboot_cassandra_demo.service.dto.ComposantDTO;
import com.chaabiamal.springboot_cassandra_demo.service.dto.historiqueComposantDTO;
import com.chaabiamal.springboot_cassandra_demo.service.historiqueComposantService;
import com.chaabiamal.springboot_cassandra_demo.service.mapper.ComposantMapper;
import com.chaabiamal.springboot_cassandra_demo.service.mapper.historiqueComposantMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;


@Service
public class ComposantServiceImpl implements ComposantService {
    @Autowired
    private  ComposantRepository composantRepository;
    @Autowired
    private ComposantMapper composantMapper;
    //************************88
    @Autowired
    private historiqueComposantService historiquecomposantService;
    @Autowired
    private historiqueComposantRepository historiquecomposantRepository;

    @Autowired
    private historiqueComposantMapper historiquecomposantMapper;
    @Autowired
    public ComposantServiceImpl(ComposantRepository composantRepository) {
        this.composantRepository = composantRepository;
    }

    public ResponseEntity<String> findByIdCheckaddInfo( UUID composantId,
                                                       int value) {
        Composant composant = composantRepository.findComposantById(composantId).orElseThrow(
                () -> new ResourceNotFoundException("Composant not found with ID: " + composantId));

        // Supprimer le dernier caractère '%' de la valeur du composant
        // String valueString = composantValue.substring(0, composantValue.length() - 1);

        try {


            // Vérifier si la valeur est inférieure à 20% ou supérieure à 80%
            if (value <= 20 || value >= 80) {
                //**************************************** si inf 20 & sup 80 *******************************************************
                historiqueComposant historiquecomposant = new historiqueComposant(); // Create a new instance
                // Set properties of historiquecomposant
                historiquecomposant.setId(UUID.randomUUID());
                historiquecomposant.setComposantId(composantId);
                historiquecomposant.setDatetime(LocalDateTime.now());
                historiquecomposant.setStatus(composant.getLastStatus());
                historiquecomposant.setValue(composant.getValue());
                //ajouter champs additionalInfo;
                historiqueComposant savedHistoriqueComposant = historiquecomposantRepository.save(historiquecomposant);
                historiqueComposantDTO savedHistoriqueComposantDTO = historiquecomposantMapper.toDto(savedHistoriqueComposant);

                //***********************
                composant.setValue(value+"%");
                composantRepository.save(composant); // Mettre à jour la valeur du composant
                if (value <= 20) {
                    return ResponseEntity.ok("La valeur du composant est inférieure à 20%");
                } else {
                    return ResponseEntity.ok("La valeur du composant est supérieure à 80%");
                }
            } else {
                composant.setValue(value+"%");
                composantRepository.save(composant); // Mettre à jour la valeur du composant
                return ResponseEntity.ok("La valeur du composant est dans la plage acceptable entre 20% et 80%");
            }

        } catch (NumberFormatException e) {
            // En cas d'erreur lors de la conversion ou si la valeur n'est pas un pourcentage valide
            return ResponseEntity.badRequest().body("La valeur du composant n'est pas un pourcentage valide");
        }
    }

   //****************************
   @Override
   public Optional<ComposantDTO> findById(UUID id) {
       return composantRepository.findComposantById(id).map(composantMapper::toDto);
   }
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
        return composantRepository.findComposantById(id)
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
                    if (0<composantDTO.statusId()&& composantDTO.statusId()<5) {
                        existingComposant.setStatusId(composantDTO.statusId());
                    }
                    if (composantDTO.name() != null) {
                        existingComposant.setName(composantDTO.name());
                    }
                    if (composantDTO.value() != null) {
                        existingComposant.setValue(composantDTO.value());
                    }
                    if (composantDTO.componentTypeId()>0) {
                        existingComposant.setComponentTypeId(composantDTO.componentTypeId());
                    }
                    if (composantDTO.code() != null) {
                        existingComposant.setCode(composantDTO.code());
                    }
                    if (composantDTO.machineId() != null) {
                        existingComposant.setMachineId(composantDTO.machineId());
                    }
                    if (composantDTO.model() != null) {
                        existingComposant.setModel(composantDTO.model());
                    }

                    if (composantDTO.lastStatusChangeTime() != null) {
                        existingComposant.setLastStatusChangeTime(composantDTO.lastStatusChangeTime());
                    }
                    if (composantDTO.composantCreatedDate() != null) {
                        existingComposant.setComposantCreatedDate(composantDTO.composantCreatedDate());
                    }
                    if (composantDTO.composantModifiedDate()!= null) {
                        existingComposant.setComposantModifiedDate(composantDTO.composantModifiedDate());
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
    public void delete(UUID composantId) {
        composantRepository.deleteByid(composantId);
    }
}
