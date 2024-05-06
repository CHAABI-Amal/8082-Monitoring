package com.chaabiamal.springboot_cassandra_demo.service.mapper;

import com.chaabiamal.springboot_cassandra_demo.model.historiqueComposant;
import com.chaabiamal.springboot_cassandra_demo.service.dto.historiqueComposantDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = ComposantMapper.class)
public interface historiqueComposantMapper {
    @Mapping(target = "id", ignore = true)
    historiqueComposant toEntity(historiqueComposantDTO dto);

    @Mapping(target = "composants", source = "composants")
    historiqueComposantDTO toDto(historiqueComposant entity);
}
