package com.chaabiamal.springboot_cassandra_demo.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import org.springframework.data.annotation.Transient;
import org.springframework.data.cassandra.core.cql.Ordering;
import org.springframework.data.cassandra.core.cql.PrimaryKeyType;
import org.springframework.data.cassandra.core.mapping.CassandraType;
import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyColumn;
import org.springframework.data.cassandra.core.mapping.Table;

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
	@CassandraType(type = CassandraType.Name.UUID)
	private UUID id;
	@CassandraType(type = CassandraType.Name.UUID)
	@PrimaryKeyColumn(type = PrimaryKeyType.CLUSTERED, ordinal = 1,ordering = Ordering.ASCENDING)
	@Column(value = "composantid")
	private UUID composantId;

	@CassandraType(type = CassandraType.Name.TEXT)
	@Column(value = "status")
	private String status;  // status precedent de composant1

	@CassandraType(type = CassandraType.Name.TEXT)
	@Column(value = "value")
	private String value;

	@CassandraType(type = CassandraType.Name.TIMESTAMP)
	@PrimaryKeyColumn(type = PrimaryKeyType.CLUSTERED, ordinal = 0, ordering = Ordering.DESCENDING)
	private LocalDateTime datetime;


	// Constructeur
	public historiqueComposant() {
	}

	public historiqueComposant(UUID id, UUID composantId, String status, String value, LocalDateTime datetime) {
		this.id = id;
		this.composantId = composantId;
		this.status = status;
		this.value = value;
		this.datetime = datetime;
	}

	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	public UUID getComposantId() {
		return composantId;
	}

	public void setComposantId(UUID composantId) {
		this.composantId = composantId;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public LocalDateTime getDatetime() {
		return datetime;
	}

	public void setDatetime(LocalDateTime datetime) {
		this.datetime = datetime;
	}
}