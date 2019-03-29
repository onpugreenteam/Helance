package com.ranpeak.ProjectX.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

@JsonIgnoreProperties(ignoreUnknown = true)
public class TaskDTO implements Serializable {

    @JsonProperty("id")
    private long id;
    @JsonProperty("headLine")
    private String headLine;
    @JsonProperty("text")
    private String text;
    @JsonProperty("dateStart")
    private String dateStart;
    @JsonProperty("dateEnd")
    private String dateEnd;
    @JsonProperty("customer")
    private UserDTO customer;
    @JsonProperty("employee")
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
    @JsonProperty("correspondence")
    private String correspondence;

//    private Correspondence correspondence;

    public TaskDTO() {
    }

    public TaskDTO(long id, String headLine, String text, String dateStart, String dateEnd, UserDTO customer, String employee, String subject, float price, String status, String type, String filePath, String correspondence) {
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
        this.correspondence = correspondence;
    }

    public String getCorrespondence() {
        return correspondence;
    }

    public void setCorrespondence(String correspondence) {
        this.correspondence = correspondence;
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

    public UserDTO getCustomer() {
        return customer;
    }

    public void setCustomer(UserDTO customer) {
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
