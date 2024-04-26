package com.chaabiamal.springboot_cassandra_demo.model;

import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

import java.time.LocalDateTime;
import java.util.UUID;

@Table(value = "composant")
public class Composant {
    @PrimaryKey
    private UUID id;
    private int statusId;
    private UUID historiqueId;
    private String additionalInfo;
    private LocalDateTime lastStatusChangeTime;
    private TypeStatus lastStatus;
    private String instanceCode;
    private UUID kioskId;
    private String instanceName;

    private Integer componentTypeId;
    private String modelNumber;
    private String componentStatus;
    private LocalDateTime statusDate;

    private boolean isDeleted;

    private LocalDateTime createdDate;
    private LocalDateTime modifiedDate;
//****************************************
    // Constructeur
    public Composant() {
    }

    public Composant(UUID id, int statusId, UUID historiqueId, String additionalInfo, LocalDateTime lastStatusChangeTime, TypeStatus lastStatus, String instanceCode, UUID kioskId, String instanceName, Integer componentTypeId, String modelNumber, String componentStatus, LocalDateTime statusDate, boolean isDeleted, LocalDateTime createdDate, LocalDateTime modifiedDate) {
        this.id = id;
        this.statusId = statusId;
        this.historiqueId = historiqueId;
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

    public int getStatusId() {
        return statusId;
    }

    public void setStatusId(int statusId) {
        this.statusId = statusId;
    }

    public UUID getHistoriqueId() {
        return historiqueId;
    }

    public void setHistoriqueId(UUID historiqueId) {
        this.historiqueId = historiqueId;
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

    public TypeStatus getLastStatus() {
        return lastStatus;
    }

    public void setLastStatus(TypeStatus lastStatus) {
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
