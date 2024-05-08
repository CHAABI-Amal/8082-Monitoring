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
    @Autowired
    private  ComposantRepository composantRepository;
    @Autowired
    private ComposantMapper composantMapper;
    @Autowired
    public ComposantServiceImpl(ComposantRepository composantRepository) {
        this.composantRepository = composantRepository;
    }


//**********************


    public String checkAdditionalInfo(String additionalInfo, String valueToCheck) {
        if (additionalInfo == null || valueToCheck == null) {
            return "AdditionalInfo or valueToCheck is null";
        }
        String[] valuesToCheck = valueToCheck.split(",");
        int index = valueToCheck.indexOf(",");
        valueToCheck= valueToCheck.substring(index + 1);
        if(index<0) valueToCheck=null;
        for (String value : valuesToCheck) {
            String[] keyValue = value.split(":");
            String value1 = keyValue[0].trim();
            if (additionalInfo.contains(value1)) {
                return checkValue(additionalInfo, value, valueToCheck);
            }
        }
        return "None of the specified values found in AdditionalInfo";
    }

    private String checkValue(String additionalInfo, String valueToCheck,String reste) {
        int check=0;
        String[] parts = additionalInfo.split(",valueToCheck");
        for (String part : parts) {

            String[] keyValue1 = part.split(":");
            String key1 = keyValue1[0].trim();
            //***
            String[] keyValue2 = valueToCheck.split(":");
            String key2 = keyValue2[0].trim();
            if (key1.contains(key2)) {
                String value = keyValue2[1].trim();
                try {
                    int intValue = Integer.parseInt(value);
                    if (valueToCheck.equals("RAM") || valueToCheck.equals("CPU")) {
                        if (intValue < 20) {
                            System.out.println(valueToCheck + " usage is less than 20%"); ;
                            check=1;
                        } else if (intValue > 80) {
                            System.out.println(valueToCheck + " usage is more than 80%");check=2;

                        } else {
                            System.out.println(valueToCheck + " usage is within acceptable range");check=3;
                        }
                    } else if (key1.equals("C") || key1.equals("D")) {
                        // Assuming it's disk usage, same logic as RAM and CPU
                        if (intValue < 20) {
                            System.out.println(valueToCheck + " disk usage is less than 20%");check=4;
                        } else if (intValue > 80) {
                            System.out.println(valueToCheck + " disk usage is more than 80%");check=5;
                        } else {
                            System.out.println(valueToCheck + " disk usage is within acceptable range");check=6;
                        }
                    }
                } catch (NumberFormatException e) {
                    // Not a valid integer value
                    System.out.println("Invalid value format in AdditionalInfo");check=7;
                }
                checkAdditionalInfo(part,reste);
            }
        }
        if(check==0) {
            return "Value not found in AdditionalInfo";
        }
        return check+"done check";
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
                    if (composantDTO.isdeleted()) {
                        existingComposant.setIsdeleted(composantDTO.isdeleted());
                    }
                    if (0<composantDTO.status()&& composantDTO.status()<5) {
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
    public void delete(UUID composantId) {
        composantRepository.deleteByid(composantId);
    }
}
