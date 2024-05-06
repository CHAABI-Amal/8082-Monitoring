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
import java.util.UUID;
@JsonIdentityInfo(
		generator = ObjectIdGenerators.PropertyGenerator.class,
		property = "id",
		scope = Composant.class)
@Table(value = "composant")
public class Composant implements Serializable {
	@PrimaryKeyColumn(type = PrimaryKeyType.PARTITIONED)
	@CassandraType(type = CassandraType.Name.UUID)
	@Column(value = "id")
	private UUID id;

	@CassandraType(type = CassandraType.Name.UUID)
	@Column(value = "historiquecomposant")
	private UUID historiqueComposant;

	@CassandraType(type = CassandraType.Name.UUID)
	@Column(value = "status")
	private UUID status;

	@CassandraType(type = CassandraType.Name.TEXT)
	@Column(value = "additionalinfo")
	private String additionalInfo;

	@CassandraType(type = CassandraType.Name.TIMESTAMP)
	@Column(value = "laststatuschangetime")
	private LocalDateTime lastStatusChangeTime;

	@CassandraType(type = CassandraType.Name.TEXT)
	@Column(value = "laststatus")
	private String lastStatus;

	@CassandraType(type = CassandraType.Name.TEXT)
	@Column(value = "instancecode")
	private String instanceCode;

	@CassandraType(type = CassandraType.Name.UUID)
	@Column(value = "kioskid")
	private UUID kioskId;

	@CassandraType(type = CassandraType.Name.TEXT)
	@Column(value = "instancename")
	private String instanceName;

	@CassandraType(type = CassandraType.Name.INT)
	@Column(value = "componenttypeid")
	private Integer componentTypeId;

	@CassandraType(type = CassandraType.Name.TEXT)
	@Column(value = "modelnumber")
	private String modelNumber;

	@CassandraType(type = CassandraType.Name.TEXT)
	@Column(value = "componentstatus")
	private String componentStatus;

	@CassandraType(type = CassandraType.Name.TIMESTAMP)
	@Column(value = "statusdate")
	private LocalDateTime statusDate;

	@CassandraType(type = CassandraType.Name.BOOLEAN)
	@Column(value = "isdeleted")
	private boolean isdeleted;

	@CassandraType(type = CassandraType.Name.TIMESTAMP)
	@Column(value = "createddate")
	private LocalDateTime createdDate;

	@CassandraType(type = CassandraType.Name.TIMESTAMP)
	@Column(value = "modifieddate")
	private LocalDateTime modifiedDate;

	//****************************************
	// Constructeur
	public Composant() {
	}

	public Composant(UUID id, UUID historiqueComposant, UUID status, String additionalInfo, LocalDateTime lastStatusChangeTime, String lastStatus, String instanceCode, UUID kioskId, String instanceName, Integer componentTypeId, String modelNumber, String componentStatus, LocalDateTime statusDate, boolean isdeleted, LocalDateTime createdDate, LocalDateTime modifiedDate) {
		this.id = id;
		this.historiqueComposant = historiqueComposant;
		this.status = status;
		this.additionalInfo = additionalInfo;
		this.lastStatusChangeTime = lastStatusChangeTime;
		this.lastStatus = lastStatus;
		this.instanceCode = instanceCode;
		this.kioskId = kioskId;
		this.instanceName = instanceName;
		this.componentTypeId = componentTypeId;
		this.modelNumber = modelNumber;
		this.componentStatus = componentStatus;
		this.statusDate = statusDate;
		this.isdeleted = isdeleted;
		this.createdDate = createdDate;
		this.modifiedDate = modifiedDate;
	}

	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	public UUID getHistoriqueComposant() {
		return historiqueComposant;
	}

	public void setHistoriqueComposant(UUID historiqueComposant) {
		this.historiqueComposant = historiqueComposant;
	}

	public UUID getStatus() {
		return status;
	}

	public void setStatus(UUID status) {
		this.status = status;
	}

	public String getAdditionalInfo() {
		return additionalInfo;
	}

	public void setAdditionalInfo(String additionalInfo) {
		this.additionalInfo = additionalInfo;
	}

	public LocalDateTime getLastStatusChangeTime() {
		return lastStatusChangeTime;
	}

	public void setLastStatusChangeTime(LocalDateTime lastStatusChangeTime) {
		this.lastStatusChangeTime = lastStatusChangeTime;
	}

	public String getLastStatus() {
		return lastStatus;
	}

	public void setLastStatus(String lastStatus) {
		this.lastStatus = lastStatus;
	}

	public String getInstanceCode() {
		return instanceCode;
	}

	public void setInstanceCode(String instanceCode) {
		this.instanceCode = instanceCode;
	}

	public UUID getKioskId() {
		return kioskId;
	}

	public void setKioskId(UUID kioskId) {
		this.kioskId = kioskId;
	}

	public String getInstanceName() {
		return instanceName;
	}

	public void setInstanceName(String instanceName) {
		this.instanceName = instanceName;
	}

	public Integer getComponentTypeId() {
		return componentTypeId;
	}

	public void setComponentTypeId(Integer componentTypeId) {
		this.componentTypeId = componentTypeId;
	}

	public String getModelNumber() {
		return modelNumber;
	}

	public void setModelNumber(String modelNumber) {
		this.modelNumber = modelNumber;
	}

	public String getComponentStatus() {
		return componentStatus;
	}

	public void setComponentStatus(String componentStatus) {
		this.componentStatus = componentStatus;
	}

	public LocalDateTime getStatusDate() {
		return statusDate;
	}

	public void setStatusDate(LocalDateTime statusDate) {
		this.statusDate = statusDate;
	}

	public boolean isIsdeleted() {
		return isdeleted;
	}

	public void setIsdeleted(boolean isdeleted) {
		this.isdeleted = isdeleted;
	}

	public LocalDateTime getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(LocalDateTime createdDate) {
		this.createdDate = createdDate;
	}

	public LocalDateTime getModifiedDate() {
		return modifiedDate;
	}

	public void setModifiedDate(LocalDateTime modifiedDate) {
		this.modifiedDate = modifiedDate;
	}
}
