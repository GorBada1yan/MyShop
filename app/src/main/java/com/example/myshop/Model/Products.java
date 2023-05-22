package com.example.myshop.Model;

public class Products {
    private String pid;
    private String car_bublik;
    private String car_kuzov;
    private String car_mark;
    private String car_motor;
    private String car_year;
    private String car_name;
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

    public Products (String pid,String car_bublik,String car_name, String car_kuzov,String car_mark,String car_motor, String car_year,  String price, String time, String userId, String categoryRent, String categorySubTypeofMachine, String categoryType, String categoryTypeofMachine, String contacts, String date, String description, String image){
        this.pid = pid;
        this.car_bublik = car_bublik;
        this.car_name = car_name;
        this.car_kuzov = car_kuzov;
        this.car_mark = car_mark;
        this.car_motor = car_motor;
        this.car_year = car_year;
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

    public String getCar_name() {
        return car_name;
    }

    public void setCar_name(String car_name) {
        this.car_name = car_name;
    }

    public String getCar_bublik() {
        return car_bublik;
    }

    public void setCar_bublik(String car_bublik) {
        this.car_bublik = car_bublik;
    }

    public String getCar_kuzov() {
        return car_kuzov;
    }

    public void setCar_kuzov(String car_kuzov) {
        this.car_kuzov = car_kuzov;
    }

    public String getCar_mark() {
        return car_mark;
    }

    public void setCar_mark(String car_mark) {
        this.car_mark = car_mark;
    }

    public String getCar_motor() {
        return car_motor;
    }

    public void setCar_motor(String car_motor) {
        this.car_motor = car_motor;
    }

    public String getCar_year() {
        return car_year;
    }

    public void setCar_year(String car_year) {
        this.car_year = car_year;
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