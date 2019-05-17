package com.company.Helance.dto.pojo;

public class SocialNetworkPOJO {

    private long id;

    private String networkName;

    private String networkLogin;

    private String user;

    public SocialNetworkPOJO() {
    }

    public SocialNetworkPOJO(long id, String networkName, String networkLogin, String user) {
        this.id = id;
        this.networkName = networkName;
        this.networkLogin = networkLogin;
        this.user = user;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }
}
