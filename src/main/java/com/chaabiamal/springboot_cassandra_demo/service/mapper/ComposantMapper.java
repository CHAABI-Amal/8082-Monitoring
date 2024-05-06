package com.chaabiamal.springboot_cassandra_demo.service.mapper;

import com.chaabiamal.springboot_cassandra_demo.model.Composant;
import com.chaabiamal.springboot_cassandra_demo.service.dto.ComposantDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = historiqueComposantMapper.class)
public interface ComposantMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "historique", source = "historique")
    Composant toEntity(ComposantDTO dto);

    @Mapping(target = "historique", ignore = true)
    ComposantDTO toDto(Composant entity);
}
