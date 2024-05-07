package com.chaabiamal.springboot_cassandra_demo.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import org.springframework.data.annotation.Transient;
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
	private UUID id;
	@CassandraType(type = CassandraType.Name.UUID)
	@Column(value = "composantid")
	private UUID composantId;

	@CassandraType(type = CassandraType.Name.TEXT)
	@Column(value = "laststatus")
	private String lastStatus;

	@CassandraType(type = CassandraType.Name.TIMESTAMP)
	@Column(value = "date")
	private LocalDateTime date;




	// Constructeur
	public historiqueComposant() {
	}

	public historiqueComposant(UUID id, UUID composantId,LocalDateTime i) {
		this.id = id;
		this.composantId = composantId;
		this.date=i;
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


	public LocalDateTime getDate() {
		return date;
	}

	public void setDate(LocalDateTime date) {
		this.date = date;
	}

	public String getLastStatus() {
		return lastStatus;
	}

	public void setLastStatus(String lastStatus) {
		this.lastStatus = lastStatus;
	}
}
