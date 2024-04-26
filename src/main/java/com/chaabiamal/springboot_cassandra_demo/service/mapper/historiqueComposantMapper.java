package com.chaabiamal.springboot_cassandra_demo.service.mapper;
import com.chaabiamal.springboot_cassandra_demo.model.historiqueComposant;
import com.chaabiamal.springboot_cassandra_demo.service.dto.historiqueComposantDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
@Mapper(componentModel = "spring")
public interface historiqueComposantMapper {
    @Mapping(target = "id", ignore = true)
    historiqueComposant toEntity(historiqueComposantDTO dto);

    historiqueComposantDTO toDto(historiqueComposant entity);
}
