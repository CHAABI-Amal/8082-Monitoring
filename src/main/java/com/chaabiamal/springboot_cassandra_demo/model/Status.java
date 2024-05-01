package com.chaabiamal.springboot_cassandra_demo.model;

import org.springframework.data.cassandra.core.cql.PrimaryKeyType;
import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyColumn;
import org.springframework.data.cassandra.core.mapping.Table;

import java.io.Serializable;
import java.util.UUID;
@Table(value = "Status")

public class Status implements Serializable {
    @PrimaryKeyColumn(type = PrimaryKeyType.PARTITIONED)
        private int ID;
        @Column("Value")

        private String Value;
        @Column("Description")
        private String Description;


//*******************************************


    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getValue() {
        return Value;
    }

    public void setValue(String value) {
        Value = value;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }


    }
