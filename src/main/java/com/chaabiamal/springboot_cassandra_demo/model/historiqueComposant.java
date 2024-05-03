package com.chaabiamal.springboot_cassandra_demo.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import org.springframework.data.cassandra.core.cql.PrimaryKeyType;
import org.springframework.data.cassandra.core.mapping.*;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id",
        scope = historiqueComposant.class)
@Table(value = "historiquecomposant")
public class historiqueComposant implements Serializable {
        @PrimaryKeyColumn(type = PrimaryKeyType.PARTITIONED)
        private UUID id;
         @Column("composantId")
        @CassandraType(type = CassandraType.Name.LIST)
        private List<Composant> composants;


    // Constructeur
    public historiqueComposant() {
    }

    public historiqueComposant(UUID id, List<Composant> composants) {
        this.id = id;
        this.composants = composants;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public List<Composant> getComposants() {
        return composants;
    }

    public void setComposants(List<Composant> composants) {
        this.composants = composants;
    }
}
