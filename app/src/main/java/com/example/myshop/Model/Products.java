package com.example.myshop.Model;

public class Products {
    private String pid;
    private String car_bublik;
    private String car_kuzov;
    private String car_mark;
    private String car_motor;
    private String car_year;
    private String model;
    private String price;
    private String time;
    private String userId;

    private String contacts;
    private String date;
    private String description;
    private String image;
    private String dop_contacts;

    public Products() {}

    public Products(String pid, String car_bublik, String model, String car_kuzov, String car_mark, String car_motor, String car_year, String price, String time, String userId, String contacts, String date, String description, String image, String dop_contacts) {
        this.pid = pid;
        this.car_bublik = car_bublik;
        this.model = model;
        this.car_kuzov = car_kuzov;
        this.car_mark = car_mark;
        this.car_motor = car_motor;
        this.car_year = car_year;
        this.price = price;
        this.time = time;
        this.userId = userId;
        this.contacts = contacts;
        this.date = date;
        this.description = description;
        this.image = image;
        this.dop_contacts = dop_contacts;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }
    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
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

    public String getDop_contacts() {
        return dop_contacts;
    }

    public void setDop_contacts(String dop_contacts) {
        this.dop_contacts = dop_contacts;
    }
}
