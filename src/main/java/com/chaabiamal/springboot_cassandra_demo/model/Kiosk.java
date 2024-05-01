package com.chaabiamal.springboot_cassandra_demo.model;

import org.springframework.data.cassandra.core.cql.PrimaryKeyType;
import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyColumn;
import org.springframework.data.cassandra.core.mapping.Table;

import java.util.UUID;

@Table(value = "kiosk") // Nom de la table
public class Kiosk {
    @PrimaryKeyColumn(type = PrimaryKeyType.PARTITIONED)
    private UUID kioskId;
    @Column("Value")
    private String name;
    @Column("Value")
    private String code;
    @Column("Value")
    private boolean isOnline;
    @Column("Value")
    private String description;
    @Column("ipAddress")
    private String ipAddress; // Modifier le type en String
    @Column("machineName")
    private String machineName;

    public UUID getKioskId() {
        return kioskId;
    }

    public void setKioskId(UUID kioskId) {
        this.kioskId = kioskId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public boolean isOnline() {
        return isOnline;
    }

    public void setOnline(boolean online) {
        isOnline = online;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public String getMachineName() {
        return machineName;
    }

    public void setMachineName(String machineName) {
        this.machineName = machineName;
    }
}
