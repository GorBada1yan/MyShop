package com.example.myshop.Model;

public class Products {
    private String pid;
    private String pname;
    private String price;
    private String time;
    private String userId;
    private String categoryRent;
    private String categorySubTypeofMachine;
    private String categoryType;
    private String categoryTypeofMachine;
    private String contacts;
    private String date;
    private String description;
    private String image;
    public Products(){}

    public Products (String pid, String pname, String price, String time, String userId, String categoryRent, String categorySubTypeofMachine, String categoryType, String categoryTypeofMachine, String contacts, String date, String description, String image){
        this.pid = pid;
        this.pname = pname;
        this.price = price;
        this.time = time;
        this.userId = userId;
        this.categoryRent = categoryRent;
        this.categorySubTypeofMachine = categorySubTypeofMachine;
        this.categoryType = categoryType;
        this.categoryTypeofMachine = categoryTypeofMachine;
        this.contacts = contacts;
        this.date = date;
        this.description = description;
        this.image = image;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getPname() {
        return pname;
    }

    public void setPname(String pname) {
        this.pname = pname;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getCategoryRent() {
        return categoryRent;
    }

    public void setCategoryRent(String categoryRent) {
        this.categoryRent = categoryRent;
    }

    public String getCategorySubTypeofMachine() {
        return categorySubTypeofMachine;
    }

    public void setCategorySubTypeofMachine(String categorySubTypeofMachine) {
        this.categorySubTypeofMachine = categorySubTypeofMachine;
    }

    public String getCategoryType() {
        return categoryType;
    }

    public void setCategoryType(String categoryType) {
        this.categoryType = categoryType;
    }

    public String getCategoryTypeofMachine() {
        return categoryTypeofMachine;
    }

    public void setCategoryTypeofMachine(String categoryTypeofMachine) {
        this.categoryTypeofMachine = categoryTypeofMachine;
    }

    public String getContacts() {
        return contacts;
    }

    public void setContacts(String contacts) {
        this.contacts = contacts;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}