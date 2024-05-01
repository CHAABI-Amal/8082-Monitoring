package com.chaabiamal.springboot_cassandra_demo.repository;

import com.chaabiamal.springboot_cassandra_demo.model.Kiosk;
import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.data.cassandra.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.UUID;
@Repository
public interface KioskRepository extends CassandraRepository<Kiosk, UUID> {

    @Query("SELECT * FROM mykeyspace.Kiosk WHERE id = ?0") // Specify keyspace and table name
    Kiosk findByProductId(UUID id);
}
