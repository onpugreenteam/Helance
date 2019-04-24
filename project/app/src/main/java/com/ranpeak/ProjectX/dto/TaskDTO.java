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

    @SerializedName("subject")
    @ColumnInfo (name = "subject")
    @Expose
    private String subject;

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

    @SerializedName("price")
    @ColumnInfo (name = "price")
    @Expose
    private float price;

    @SerializedName("deadline")
    @ColumnInfo (name = "deadline")
    @Expose
    private String deadline;

    @SerializedName("status")
    @ColumnInfo (name = "status")
    @Expose
    private String status;

    @SerializedName("userLogin")
    @ColumnInfo (name = "userLogin")
    @Expose
    private String userLogin;

    @SerializedName("userEmail")
    @ColumnInfo (name = "userEmail")
    @Expose
    private String userEmail;

    @SerializedName("userName")
    @ColumnInfo (name = "userName")
    @Expose
    private String userName;

    @SerializedName("userAvatar")
    @ColumnInfo (name = "userAvatar")
    @Expose
    private String userAvatar;

    @SerializedName("userCountry")
    @ColumnInfo (name = "userCountry")
    @Expose
    private String userCountry;

    @SerializedName("fileTasks")
    @ColumnInfo (name = "fileTasks")
    @Expose
    private String fileTasks;


    public TaskDTO() {
    }

    @Ignore
    public TaskDTO(long id, String subject, String headLine, String description, String dateStart, float price, String deadline, String status, String userLogin, String userEmail, String userName, String userAvatar, String userCountry, String fileTasks) {
        this.id = id;
        this.subject = subject;
        this.headLine = headLine;
        this.description = description;
        this.dateStart = dateStart;
        this.price = price;
        this.deadline = deadline;
        this.status = status;
        this.userLogin = userLogin;
        this.userEmail = userEmail;
        this.userName = userName;
        this.userAvatar = userAvatar;
        this.userCountry = userCountry;
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

    public String getUserLogin() {
        return userLogin;
    }

    public void setUserLogin(String userLogin) {
        this.userLogin = userLogin;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserAvatar() {
        return userAvatar;
    }

    public void setUserAvatar(String userAvatar) {
        this.userAvatar = userAvatar;
    }

    public String getUserCountry() {
        return userCountry;
    }

    public void setUserCountry(String userCountry) {
        this.userCountry = userCountry;
    }
}
