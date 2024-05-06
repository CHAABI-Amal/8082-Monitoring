package com.chaabiamal.springboot_cassandra_demo.repository;

import com.chaabiamal.springboot_cassandra_demo.model.historiqueComposant;
import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.data.cassandra.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface historiqueComposantRepository extends CassandraRepository<historiqueComposant, UUID> {

    @Query("SELECT * FROM mykeyspace.historiqueComposant WHERE id = ?0") // Specify keyspace and table name
    historiqueComposant findByid(UUID id);

}