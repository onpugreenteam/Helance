package com.ranpeak.ProjectX.entities;


import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

public class Users implements Serializable {

    @JsonProperty("login")
    private int login;
    @JsonProperty("password")
    private String password;
    @JsonProperty("name")
    private String name;
    @JsonProperty("age")
    private int age;
    @JsonProperty("country")
    private String country;
    @JsonProperty("gender")
    private String gender;

    public Users() {
    }

    public Users(int login, String password, String name, int age, String country, String gender) {
        this.login = login;
        this.password = password;
        this.name = name;
        this.age = age;
        this.country = country;
        this.gender = gender;
    }

    public int getLogin() {
        return login;
    }

    public void setLogin(int login) {
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

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String toString() {
        return  "login =" + this.getLogin() +
                ", password = " + this.getPassword() +
                ", name = " + this.getName() +
                ", age = " + this.getAge() +
                ", country = " + this.getCountry() +
                ", gender = " + this.getGender() +
                '}';
    }
}
