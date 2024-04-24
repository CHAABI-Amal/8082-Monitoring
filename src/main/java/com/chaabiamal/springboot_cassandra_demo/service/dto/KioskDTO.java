package com.chaabiamal.springboot_cassandra_demo.service.dto;

import java.net.Inet4Address;
import java.util.UUID;


public record KioskDTO(
        UUID kioskId,
        String name,
        String code,
        boolean isOnline,
        String description,
        String ipAddress,
        String machineName
) {}