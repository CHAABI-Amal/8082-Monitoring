package com.chaabiamal.springboot_cassandra_demo.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.cassandra.core.cql.Ordering;
import org.springframework.data.cassandra.core.cql.PrimaryKeyType;
import org.springframework.data.cassandra.core.mapping.*;

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
    private UUID id;
    @Column("historiqueId")
    @CassandraType(type = CassandraType.Name.UDT,userTypeName = "historiqueComposant")
    private historiqueComposant historique; // Cette variable sera utilisée pour la liaison avec HistoriqueComposant
    @Column("status")
    @CassandraType(type = CassandraType.Name.UUID)
    private UUID status;//??? mnba3d
    @Column("additionalInfo")
    @CassandraType(type = CassandraType.Name.TEXT)
    private String additionalInfo;
    @Column("lastStatusChangeTime")
    @CassandraType(type = CassandraType.Name.TIMESTAMP)
    private LocalDateTime lastStatusChangeTime;
    @Column("lastStatus")
    @CassandraType(type = CassandraType.Name.TEXT)
    private String lastStatus;
    @Column("instanceCode")
    @CassandraType(type = CassandraType.Name.TEXT)
    private String instanceCode;
    @Column("kioskId")
    @CassandraType(type = CassandraType.Name.UUID)
    private UUID kioskId;
    @Column("instanceName")
    @CassandraType(type = CassandraType.Name.TEXT)
    private String instanceName;
    @Column("componentTypeId")
    @CassandraType(type = CassandraType.Name.INT)
    private Integer componentTypeId;
    @Column("modelNumber")
    @CassandraType(type = CassandraType.Name.TEXT)
    private String modelNumber;
    @Column("componentStatus")
    @CassandraType(type = CassandraType.Name.TEXT)
    private String componentStatus;
    @Column("statusDate")
    @CassandraType(type = CassandraType.Name.TIMESTAMP)
    private LocalDateTime statusDate;
    @Column("isDeleted")
    @CassandraType(type = CassandraType.Name.BOOLEAN)
    private boolean isDeleted;
    // cluster key, so we can get automatically sorted data
    //@PrimaryKeyColumn(ordinal = 1, type = PrimaryKeyType.CLUSTERED, ordering = Ordering.DESCENDING, value = "created_at")
    @Column("createdDate")
    @CassandraType(type = CassandraType.Name.TIMESTAMP)
    private LocalDateTime createdDate;
    @Column("modifiedDate")
    @CassandraType(type = CassandraType.Name.TIMESTAMP)
    private LocalDateTime modifiedDate;
//****************************************
    // Constructeur
    public Composant() {
        this.id = UUID.randomUUID();
    }

    public Composant(UUID id, historiqueComposant historique, UUID  status, String additionalInfo, LocalDateTime lastStatusChangeTime, String lastStatus, String instanceCode, UUID kioskId, String instanceName, Integer componentTypeId, String modelNumber, String componentStatus, LocalDateTime statusDate, boolean isDeleted, LocalDateTime createdDate, LocalDateTime modifiedDate) {
        System.out.println("Additional Info received: " + additionalInfo); // Debugging log
        this.id = id;
        this.historique = historique;
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
        this.isDeleted = isDeleted;
        this.createdDate = createdDate;
        this.modifiedDate = modifiedDate;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public historiqueComposant getHistorique() {
        return historique;
    }

    public void setHistorique(historiqueComposant historique) {
        this.historique = historique;
    }

    public UUID  getStatus() {
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

    public boolean isDeleted() {
        return isDeleted;
    }

    public void setDeleted(boolean deleted) {
        isDeleted = deleted;
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
