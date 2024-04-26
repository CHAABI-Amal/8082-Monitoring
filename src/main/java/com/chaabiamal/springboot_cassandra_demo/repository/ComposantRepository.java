package com.chaabiamal.springboot_cassandra_demo.repository;

import com.chaabiamal.springboot_cassandra_demo.model.Composant;
import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.data.cassandra.repository.Query;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ComposantRepository extends CassandraRepository<Composant, UUID> {

    @Query("SELECT * FROM mykeyspace.composant WHERE id = ?0") // Specify keyspace and table name
    Component findByProductId(UUID id);

    @Query("SELECT * FROM  mykeyspace.composant WHERE kioskId = ?0")
    List<Component> findByKioskId(UUID kioskId);

    @Query("SELECT * FROM mykeyspace.composant WHERE code = ?0")
    Optional<Composant> findByCode(String code);
}