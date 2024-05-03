package com.chaabiamal.springboot_cassandra_demo.model;

import org.springframework.data.cassandra.core.mapping.CassandraType;
import org.springframework.data.cassandra.core.mapping.UserDefinedType;

@UserDefinedType("type_status")
public enum TypeStatus {
    @CassandraType(type = CassandraType.Name.TEXT)
    WORKING("working"),
    @CassandraType(type = CassandraType.Name.TEXT)
    WARNING("warning"),
    @CassandraType(type = CassandraType.Name.TEXT)
    FAULTY("faulty"),
    @CassandraType(type = CassandraType.Name.TEXT)
    UNKNOWN("unknown"),
    @CassandraType(type = CassandraType.Name.TEXT)
    DISCONNECTED("disconnected");

    private final String value;

    TypeStatus(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}