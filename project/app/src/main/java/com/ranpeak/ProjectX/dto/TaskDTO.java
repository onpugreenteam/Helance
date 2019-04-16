package com.ranpeak.ProjectX.dto;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

@JsonIgnoreProperties(ignoreUnknown = true)
@Entity(tableName = "TaskEntity")
public class TaskDTO implements Serializable {

    @SerializedName("id")
    @PrimaryKey(autoGenerate = true)
    @Expose
    private long id;

    @SerializedName("headLine")
    @ColumnInfo(name = "headline")
    @Expose
    private String headLine;

    @SerializedName("text")
    @ColumnInfo (name = "text")
    @Expose
    private String text;

    @SerializedName("dateStart")
    @ColumnInfo (name = "dateStart")
    @Expose
    private String dateStart;

    @SerializedName("dateEnd")
    @ColumnInfo (name = "dateEnd")
    @Expose
    private String dateEnd;

    @SerializedName("customer")
    @ColumnInfo (name = "customer")
    @Expose
    private String customer;

    @SerializedName("employee")
    @ColumnInfo (name = "employee")
    @Expose
    private String employee;

    @SerializedName("subject")
    @ColumnInfo (name = "subject")
    @Expose
    private String subject;

    @SerializedName("price")
    @ColumnInfo (name = "price")
    @Expose
    private float price;

    @SerializedName("status")
    @ColumnInfo (name = "status")
    @Expose
    private String status;

    @SerializedName("type")
    @ColumnInfo (name = "type")
    @Expose
    private String type;

    @SerializedName("file_path")
    @ColumnInfo (name = "file_path")
    @Expose
    private String filePath;

    @SerializedName("correspondence")
    @ColumnInfo (name = "correspondence")
    @Expose
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
