package com.ranpeak.ProjectX.dto;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

@JsonIgnoreProperties(ignoreUnknown = true)
//@Entity(tableName = "UserEntity")
public class UserDTO implements Serializable {


    @SerializedName("login")
//    @PrimaryKey
    @Expose
    private String login;

    @SerializedName("name")
//    @ColumnInfo(name = "name")
    @Expose
    private String name;

    @SerializedName("email")
//    @ColumnInfo (name = "email")
    @Expose
    private String email;

    @SerializedName("country")
//    @ColumnInfo (name = "country")
    @Expose
    private String country;

    public UserDTO() {
    }

    @Ignore
    public UserDTO(String login, String name, String email, String country) {
        this.login = login;
        this.name = name;
        this.email = email;
        this.country = country;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }
}
