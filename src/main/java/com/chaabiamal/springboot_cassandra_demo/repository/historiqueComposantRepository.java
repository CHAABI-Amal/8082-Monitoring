package com.chaabiamal.springboot_cassandra_demo.repository;

import com.chaabiamal.springboot_cassandra_demo.model.Composant;
import com.chaabiamal.springboot_cassandra_demo.model.historiqueComposant;
import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.data.cassandra.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface historiqueComposantRepository extends CassandraRepository<historiqueComposant, UUID> {

    @Query("SELECT * FROM mykeyspace.historiqueComposant WHERE id = ?0 ALLOW FILTERING") // Specify keyspace and table name
    Optional<historiqueComposant>  findByid(UUID id);
    @Query("DELETE FROM mykeyspace.historiqueComposant WHERE id = ?0")
    void deleteByid(UUID id);
    @Query("SELECT * FROM mykeyspace.historiqueComposant WHERE  composantid = ?0 ALLOW FILTERING")
    List<historiqueComposant> findByidComposant(UUID id);


}