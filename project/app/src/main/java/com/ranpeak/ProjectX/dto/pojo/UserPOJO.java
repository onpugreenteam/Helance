package com.ranpeak.ProjectX.dto.pojo;

public class UserPOJO {

    private String login;
    private String name;
    private String email;
    private String country;
    private String avatar;
    private String telephone;
    private boolean active;

    public UserPOJO() {
    }

    public UserPOJO(String login, String name, String email, String country, String avatar, String telephone, boolean active) {
        this.login = login;
        this.name = name;
        this.email = email;
        this.country = country;
        this.avatar = avatar;
        this.telephone = telephone;
        this.active = active;
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

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

}
