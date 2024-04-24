package com.chaabiamal.springboot_cassandra_demo.service.mapper;

import com.chaabiamal.springboot_cassandra_demo.model.Kiosk;
import com.chaabiamal.springboot_cassandra_demo.service.dto.KioskDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface KioskMapper {
    @Mapping(target = "kioskId", ignore = true)

    Kiosk toEntity(KioskDTO dto);

    KioskDTO toDto(Kiosk entity);
}