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
@Entity(tableName = "ResumeEntity")
public class ResumeDTO implements Serializable {

    @SerializedName("id")
    @PrimaryKey(autoGenerate = false)
    @Expose
    private long id;

    @JsonProperty("subject")
    @ColumnInfo (name = "subject")
    @Expose
    private String subject;

    @JsonProperty("dateStart")
    @ColumnInfo (name = "dateStart")
    @Expose
    private String dateStart;

    @JsonProperty("opportunities")
    @ColumnInfo (name = "opportunities")
    @Expose
    private String opportunities;

    @JsonProperty("status")
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

    @SerializedName("views")
    @ColumnInfo (name = "views")
    @Expose
    private String views;

    @SerializedName("telephone")
    @ColumnInfo (name = "telephone")
    @Expose
    private String telephone;

    public ResumeDTO() {
    }

    @Ignore
    public ResumeDTO(long id, String subject, String dateStart, String opportunities, String status, String userLogin, String userEmail, String userName, String userAvatar, String userCountry, String views, String telephone) {
        this.id = id;
        this.subject = subject;
        this.dateStart = dateStart;
        this.opportunities = opportunities;
        this.status = status;
        this.userLogin = userLogin;
        this.userEmail = userEmail;
        this.userName = userName;
        this.userAvatar = userAvatar;
        this.userCountry = userCountry;
        this.views = views;
        this.telephone = telephone;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getDateStart() {
        return dateStart;
    }

    public void setDateStart(String dateStart) {
        this.dateStart = dateStart;
    }

    public String getOpportunities() {
        return opportunities;
    }

    public void setOpportunities(String opportunities) {
        this.opportunities = opportunities;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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

    public String getViews() {
        return views;
    }

    public void setViews(String views) {
        this.views = views;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }
}
