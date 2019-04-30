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
@Entity(tableName = "NetworksEntity")
public class SocialNetworkDTO implements Serializable {


    @SerializedName("id_network")
    @PrimaryKey(autoGenerate = true)
    @Expose
    private long idNetwork;

    @JsonProperty("networkName")
    @ColumnInfo(name = "networkName")
    @Expose
    private String networkName;

    @JsonProperty("networkLogin")
    @ColumnInfo(name = "networkLogin")
    @Expose
    private String networkLogin;

    public SocialNetworkDTO() {
    }

    @Ignore
    public SocialNetworkDTO(long idNetwork, String networkName, String networkLogin) {
        this.idNetwork = idNetwork;
        this.networkName = networkName;
        this.networkLogin = networkLogin;
    }

    public long getIdNetwork() {
        return idNetwork;
    }

    public void setIdNetwork(long idNetwork) {
        this.idNetwork = idNetwork;
    }

    public String getNetworkName() {
        return networkName;
    }

    public void setNetworkName(String networkName) {
        this.networkName = networkName;
    }

    public String getNetworkLogin() {
        return networkLogin;
    }

    public void setNetworkLogin(String networkLogin) {
        this.networkLogin = networkLogin;
    }
}