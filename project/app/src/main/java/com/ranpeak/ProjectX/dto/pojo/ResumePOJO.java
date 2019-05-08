package com.ranpeak.ProjectX.dto.pojo;

public class ResumePOJO {

    private long id;

    private String subject;

    private String dateStart;

    private String status;

    private String opportunities;

    private String users;

    private String views;

    public ResumePOJO() {
    }

    public ResumePOJO(long id, String subject, String dateStart, String status, String opportunities, String userLogin, String views) {
        this.id = id;
        this.subject = subject;
        this.dateStart = dateStart;
        this.status = status;
        this.opportunities = opportunities;
        this.users = userLogin;
        this.views = views;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getUserLogin() {
        return users;
    }

    public void setUserLogin(String userLogin) {
        this.users = userLogin;
    }

    public String getViews() {
        return views;
    }

    public void setViews(String views) {
        this.views = views;
    }
}
