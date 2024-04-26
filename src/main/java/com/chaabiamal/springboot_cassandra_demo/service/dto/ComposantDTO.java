package com.chaabiamal.springboot_cassandra_demo.service.dto;

import com.chaabiamal.springboot_cassandra_demo.model.TypeStatus;

import java.time.LocalDateTime;
import java.util.UUID;

public record ComposantDTO(
        UUID id,
        int statusId,
        UUID historiqueId,
        TypeStatus lastStatus,
        UUID kioskId,
        String instanceName,
        String instanceCode,
        Integer componentTypeId,
        String modelNumber,
        String componentStatus,
        LocalDateTime statusDate,
        String additionalInfo,
        boolean isDeleted,
        LocalDateTime lastStatusChangeTime,
        LocalDateTime createdDate,
        LocalDateTime modifiedDate

) {}