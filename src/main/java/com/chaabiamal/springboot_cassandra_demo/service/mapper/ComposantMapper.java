package com.chaabiamal.springboot_cassandra_demo.service.mapper;

import com.chaabiamal.springboot_cassandra_demo.model.Composant;
import com.chaabiamal.springboot_cassandra_demo.service.dto.ComposantDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ComposantMapper {
    @Mapping(target = "id", ignore = true)
    Composant toEntity(ComposantDTO dto);


    ComposantDTO toDto(Composant entity);
}
