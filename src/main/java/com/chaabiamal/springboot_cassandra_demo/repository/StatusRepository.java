package com.chaabiamal.springboot_cassandra_demo.repository;

import com.chaabiamal.springboot_cassandra_demo.model.Status;
import com.chaabiamal.springboot_cassandra_demo.model.TypeStatus;
import com.datastax.oss.driver.shaded.guava.common.io.Files;
import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.data.cassandra.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface StatusRepository extends CassandraRepository<Status, UUID> {

    @Query("SELECT * FROM mykeyspace.status WHERE Value = ?0") // Specify keyspace and table name
    Status findByValue(TypeStatus statu);
    @Query("SELECT * FROM mykeyspace.status WHERE id = ?0 ALLOW FILTERING") // Specify keyspace and table name
    Optional<Status> findByid(UUID id);
    @Query("DELETE FROM mykeyspace.status WHERE id = ?0")
    void deleteByid(UUID id);

    @Query("SELECT * FROM mykeyspace.status WHERE id = ?0 ALLOW FILTERING") // Specify keyspace and table name
    Status findById2(UUID id);
}
