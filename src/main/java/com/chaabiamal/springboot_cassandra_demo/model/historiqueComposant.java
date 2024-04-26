package com.chaabiamal.springboot_cassandra_demo.model;

import org.springframework.data.cassandra.core.mapping.Frozen;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
@Table(value = "historiqueComposant")
public class historiqueComposant {
    @PrimaryKey

    private UUID id;
    @Frozen
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
