package com.ranpeak.ProjectX.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class TaskDTO {

    @JsonProperty("id")
    private long id;
    @JsonProperty("headline")
    private String headLine;
    @JsonProperty("text")
    private String text;
    @JsonProperty("dataStart")
    private String dateStart;
    @JsonProperty("dataEnd")
    private String dateEnd;
    @JsonProperty("user_id")
    private Users customer;
    @JsonProperty("employe")
    private String employee;
    @JsonProperty("subject")
    private String subject;
    @JsonProperty("price")
    private float price;
    @JsonProperty("status")
    private String status;
    @JsonProperty("type")
    private String type;
    @JsonProperty("file_path")
    private String filePath;

//    private Correspondence correspondence;

    public TaskDTO() {
    }

    public TaskDTO(long id, String headLine, String text, String dateStart, String dateEnd, Users customer, String employee, String subject, float price, String status, String type, String filePath) {
        this.id = id;
        this.headLine = headLine;
        this.text = text;
        this.dateStart = dateStart;
        this.dateEnd = dateEnd;
        this.customer = customer;
        this.employee = employee;
        this.subject = subject;
        this.price = price;
        this.status = status;
        this.type = type;
        this.filePath = filePath;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getHeadLine() {
        return headLine;
    }

    public void setHeadLine(String headLine) {
        this.headLine = headLine;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getDateStart() {
        return dateStart;
    }

    public void setDateStart(String dateStart) {
        this.dateStart = dateStart;
    }

    public String getDateEnd() {
        return dateEnd;
    }

    public void setDateEnd(String dateEnd) {
        this.dateEnd = dateEnd;
    }

    public Users getCustomer() {
        return customer;
    }

    public void setCustomer(Users customer) {
        this.customer = customer;
    }

    public String getEmployee() {
        return employee;
    }

    public void setEmployee(String employee) {
        this.employee = employee;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }
}
