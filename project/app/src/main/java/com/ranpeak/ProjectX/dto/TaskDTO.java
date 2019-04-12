package com.ranpeak.ProjectX.dto;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

@JsonIgnoreProperties(ignoreUnknown = true)
@Entity(tableName = "TaskEntity")
public class TaskDTO implements Serializable {

    @JsonProperty("id")
    @PrimaryKey(autoGenerate = true)
    private long id;
    @JsonProperty("headLine")
    @ColumnInfo(name = "headline")
    private String headLine;
    @JsonProperty("text")
    @ColumnInfo (name = "text")
    private String text;
    @JsonProperty("dateStart")
    @ColumnInfo (name = "dateStart")
    private String dateStart;
    @JsonProperty("dateEnd")
    @ColumnInfo (name = "dateEnd")
    private String dateEnd;
    @JsonProperty("customer")
    @ColumnInfo (name = "customer")
    private String customer;
    @JsonProperty("employee")
    @ColumnInfo (name = "employee")
    private String employee;
    @JsonProperty("subject")
    @ColumnInfo (name = "subject")
    private String subject;
    @JsonProperty("price")
    @ColumnInfo (name = "price")
    private float price;
    @JsonProperty("status")
    @ColumnInfo (name = "status")
    private String status;
    @JsonProperty("type")
    @ColumnInfo (name = "type")
    private String type;
    @JsonProperty("file_path")
    @ColumnInfo (name = "file_path")
    private String filePath;
    @JsonProperty("correspondence")
    @ColumnInfo (name = "correspondence")
    private String correspondence;

    public TaskDTO() {
    }

    @Ignore
    public TaskDTO(String headLine, String text, String dateStart, String dateEnd, String customer, String employee, String subject, float price, String status, String type, String filePath, String correspondence) {
        this.headLine = headLine;
        this.text = text;
        this.dateStart = dateStart;
        this.dateEnd = dateEnd;
        this.customer = customer;
        this.employee = employee;
        this.subject = subject;
        this.price = price;
        this.status = status;
        this.type = type;
        this.filePath = filePath;
        this.correspondence = correspondence;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getHeadLine() {
        return headLine;
    }

    public void setHeadLine(String headLine) {
        this.headLine = headLine;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getDateStart() {
        return dateStart;
    }

    public void setDateStart(String dateStart) {
        this.dateStart = dateStart;
    }

    public String getDateEnd() {
        return dateEnd;
    }

    public void setDateEnd(String dateEnd) {
        this.dateEnd = dateEnd;
    }

    public String getCustomer() {
        return customer;
    }

    public void setCustomer(String customer) {
        this.customer = customer;
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

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public String getCorrespondence() {
        return correspondence;
    }

    public void setCorrespondence(String correspondence) {
        this.correspondence = correspondence;
    }
}
