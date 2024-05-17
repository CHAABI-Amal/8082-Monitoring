package com.chaabiamal.springboot_cassandra_demo.service.dto;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public record historiqueComposantDTO(

        UUID id,
        UUID composantId,
        String status,
        String value,
        LocalDateTime datetime
) {}
