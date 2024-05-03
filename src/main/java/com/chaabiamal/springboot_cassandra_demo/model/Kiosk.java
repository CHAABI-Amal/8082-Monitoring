package com.chaabiamal.springboot_cassandra_demo.model;

import org.springframework.data.cassandra.core.cql.PrimaryKeyType;
import org.springframework.data.cassandra.core.mapping.*;

import java.util.UUID;

@Table(value = "kiosk") // Nom de la table
public class Kiosk {
    @PrimaryKeyColumn(type = PrimaryKeyType.PARTITIONED)
    @CassandraType(type = CassandraType.Name.UUID)
    private UUID kioskId;
    @Column("Value")
    @CassandraType(type = CassandraType.Name.TEXT)
    private String name;
    @Column("Value")
    @CassandraType(type = CassandraType.Name.TEXT)
    private String code;
    @Column("Value")
    @CassandraType(type = CassandraType.Name.BOOLEAN)
    private boolean isOnline;
    @Column("Value")
    @CassandraType(type = CassandraType.Name.TEXT)
    private String description;
    @Column("ipAddress")
    @CassandraType(type = CassandraType.Name.TEXT)
    private String ipAddress; // Modifier le type en String
    @Column("machineName")
    @CassandraType(type = CassandraType.Name.TEXT)
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
