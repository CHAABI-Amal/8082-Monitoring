package com.chaabiamal.springboot_cassandra_demo.repository;

import com.chaabiamal.springboot_cassandra_demo.model.Composant;
import com.chaabiamal.springboot_cassandra_demo.model.historiqueComposant;
import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.data.cassandra.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;
@Repository
public interface ComposantRepository extends CassandraRepository<Composant, UUID> {
    @Query("SELECT * FROM mykeyspace.composant WHERE id = ?0 ALLOW FILTERING")
    Optional<Composant> findComposantById(UUID id);;

    @Query("SELECT * FROM mykeyspace.composant WHERE id = ?0 AND lastStatus = ?1 ALLOW FILTERING")
    Optional<Composant> findComposantByIdAndLastStatusNotLike(UUID id, String status);
    void deleteById(UUID id);

    @Query("DELETE FROM mykeyspace.composant WHERE id = ?0")
    void deleteByid(UUID id);

    @Query("SELECT value FROM mykeyspace.status WHERE id = ?0 ALLOW FILTERING")
    String findLastStatusByStatusID(int id);

    @Query("SELECT lastStatus FROM mykeyspace.composant WHERE id = ?0 ALLOW FILTERING")
    String findLastStatusBycomposantID(UUID id);

    @Query("SELECT value FROM mykeyspace.composant WHERE id = ?0 ALLOW FILTERING")
    String findValueBycomposantID(UUID id);
}
