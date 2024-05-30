package com.chaabiamal.springboot_cassandra_demo.service.dto;

import java.util.UUID;

public record StatusDTO(
        UUID id,
        String value,
        String description
) {
}
