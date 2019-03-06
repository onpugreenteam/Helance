package com.ranpeak.ProjectX.dto;

public class Users {

    private String login;
    private String password;
    private String name;
    private String email;
    private String country;
    private String avatar;

    public Users() {
    }

    public Users(String login, String password, String name, String email, String country, String avatar) {
        this.login = login;
        this.password = password;
        this.name = name;
        this.email = email;
        this.country = country;
        this.avatar = avatar;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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
}
