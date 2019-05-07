package com.ranpeak.ProjectX.dataBase.local.dto;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

@JsonIgnoreProperties(ignoreUnknown = true)
@Entity(tableName = "Resume")
public class Resume implements Serializable {

    @JsonProperty("id_resume")
    @PrimaryKey(autoGenerate = true)
    private long id;
    @JsonProperty("opportunities")
    @ColumnInfo(name = "opportunities")
    private String opportunities;
    @JsonProperty("dateStart")
    @ColumnInfo (name = "dateStart")
    private String dateStart;
    @JsonProperty("employee")
    @ColumnInfo (name = "employee")
    private String employee;
    @JsonProperty("subject")
    @ColumnInfo (name = "subject")
    private String subject;
    @JsonProperty("status")
    @ColumnInfo (name = "status")
    private String status;

    @Ignore
    public Resume() {
    }

    public Resume(String opportunities, String dateStart, String employee, String subject, String status) {
        this.opportunities = opportunities;
        this.dateStart = dateStart;
        this.employee = employee;
        this.subject = subject;
        this.status = status;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getOpportunities() {
        return opportunities;
    }

    public void setOpportunities(String opportunities) {
        this.opportunities = opportunities;
    }

    public String getDateStart() {
        return dateStart;
    }

    public void setDateStart(String dateStart) {
        this.dateStart = dateStart;
    }

    public String getEmployee() {
        return employee;
    }

    public void setEmployee(String employee) {
        this.employee = employee;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}

