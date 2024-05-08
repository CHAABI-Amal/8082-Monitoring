package com.chaabiamal.springboot_cassandra_demo.model;

import org.springframework.data.cassandra.core.mapping.CassandraType;
import org.springframework.data.cassandra.core.mapping.UserDefinedType;

public enum TypeStatus {

    WORKING,

    WARNING,

    FAULTY,

    UNKNOWN,

    DISCONNECTED;


}