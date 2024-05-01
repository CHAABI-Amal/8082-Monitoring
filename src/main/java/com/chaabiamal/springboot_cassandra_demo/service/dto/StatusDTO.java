package com.chaabiamal.springboot_cassandra_demo.service.dto;

import com.chaabiamal.springboot_cassandra_demo.model.TypeStatus;

public record StatusDTO(
        int ID,
        String Value,
        String Description
) {
}
