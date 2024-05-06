package com.chaabiamal.springboot_cassandra_demo.service.dto;

import java.util.List;
import java.util.UUID;

public record historiqueComposantDTO(
            UUID id,
            List<ComposantDTO> composants

    ) {}

