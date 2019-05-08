package com.ranpeak.ProjectX.dto.pojo;

public class TaskPOJO {

    private long id;

    private String subject;

    private String headLine;

    private String description;

    private String dateStart;

    private float price;

    private String deadline;

    private String status;

    private String user;

    private String views;

    public TaskPOJO(long id, String subject, String headLine, String description, String dateStart, float price, String deadline, String status, String userLogin, String views) {
        this.id = id;
        this.subject = subject;
        this.headLine = headLine;
        this.description = description;
        this.dateStart = dateStart;
        this.price = price;
        this.deadline = deadline;
        this.status = status;
        this.user = userLogin;
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

    public String getHeadLine() {
        return headLine;
    }

    public void setHeadLine(String headLine) {
        this.headLine = headLine;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDateStart() {
        return dateStart;
    }

    public void setDateStart(String dateStart) {
        this.dateStart = dateStart;
    }

    public String getDeadline() {
        return deadline;
    }

    public void setDeadline(String deadline) {
        this.deadline = deadline;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getUserLogin() {
        return user;
    }

    public void setUserLogin(String userLogin) {
        this.user = userLogin;
    }

    public String getViews() {
        return views;
    }

    public void setViews(String views) {
        this.views = views;
    }
}

