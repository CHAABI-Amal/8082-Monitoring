package com.chaabiamal.springboot_cassandra_demo.service.dto;


import java.time.LocalDateTime;
import java.util.UUID;


public record ComposantDTO(
        UUID id,
        UUID historiqueComposant,
        UUID  status,
        String additionalInfo,
        LocalDateTime lastStatusChangeTime,
        String lastStatus,
        String instanceCode,
        UUID kioskId,
        String instanceName,
        Integer componentTypeId,
        String modelNumber,
        String componentStatus,
        LocalDateTime statusDate,
        boolean isdeleted,
        LocalDateTime createdDate,
        LocalDateTime modifiedDate
) {}