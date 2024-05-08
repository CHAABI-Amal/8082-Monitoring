package com.chaabiamal.springboot_cassandra_demo.service.mapper;

import com.chaabiamal.springboot_cassandra_demo.model.Status;
import com.chaabiamal.springboot_cassandra_demo.service.dto.StatusDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface StatusMapper {
    @Mapping(target = "ID", ignore = true)
    Status toEntity( StatusDTO dto);
    StatusDTO toDto( Status entity);
}

