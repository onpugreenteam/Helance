package com.company.Helance.dto.pojo;

public class ResumePOJO {

    private long id;

    private String subject;

    private String dateStart;

    private boolean active;

    private String opportunities;

    private String users;

    private int views;

    private String telephone;

    public ResumePOJO() {
    }

    public ResumePOJO(long id, String subject, String dateStart, boolean active, String opportunities, String userLogin, int views, String telephone) {
        this.id = id;
        this.subject = subject;
        this.dateStart = dateStart;
        this.active = active;
        this.opportunities = opportunities;
        this.users = userLogin;
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

    public String getOpportunities() {
        return opportunities;
    }

    public void setOpportunities(String opportunities) {
        this.opportunities = opportunities;
    }

    public String getDateStart() {
        return dateStart;
    }

    public void setDateStart(String dateStart) {
        this.dateStart = dateStart;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public String getUserLogin() {
        return users;
    }

    public void setUserLogin(String userLogin) {
        this.users = userLogin;
    }

    public int getViews() {
        return views;
    }

    public void setViews(int views) {
        this.views = views;
    }

    public String getUsers() {
        return users;
    }

    public void setUsers(String users) {
        this.users = users;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }
}
