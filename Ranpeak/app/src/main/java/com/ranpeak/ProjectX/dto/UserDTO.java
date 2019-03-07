package com.ranpeak.ProjectX.dto;

public class UserDTO {

    private String login;
    private String name;
    private String email;
    private String country;
    private String avatar;

    public UserDTO() {
    }

    public UserDTO(String login, String name, String email, String country, String avatar) {
        this.login = login;
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
