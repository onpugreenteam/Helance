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
import java.util.List;

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

    @SerializedName("description")
    @ColumnInfo (name = "description")
    @Expose
    private String description;

    @SerializedName("dateStart")
    @ColumnInfo (name = "dateStart")
    @Expose
    private String dateStart;

    @SerializedName("deadline")
    @ColumnInfo (name = "deadline")
    @Expose
    private String deadline;

    @SerializedName("author")
    @ColumnInfo (name = "author")
    @Expose
    private String author;

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

    @SerializedName("fileTasks")
    @ColumnInfo (name = "fileTasks")
    @Expose
    private String fileTasks;


    public TaskDTO() {
    }


    @Ignore
    public TaskDTO(long id, String headLine, String description, String dateStart, String deadline, String author, String subject, float price, String status, String fileTasks) {
        this.id = id;
        this.headLine = headLine;
        this.description = description;
        this.dateStart = dateStart;
        this.deadline = deadline;
        this.author = author;
        this.subject = subject;
        this.price = price;
        this.status = status;
        this.fileTasks = fileTasks;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDateStart() {
        return dateStart;
    }

    public void setDateStart(String dateStart) {
        this.dateStart = dateStart;
    }

    public String getDeadline() {
        return deadline;
    }

    public void setDeadline(String deadline) {
        this.deadline = deadline;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
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

    public String getFileTasks() {
        return fileTasks;
    }

    public void setFileTasks(String fileTasks) {
        this.fileTasks = fileTasks;
    }
}
