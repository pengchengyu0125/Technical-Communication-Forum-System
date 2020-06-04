package com.luntan.model;

public class User {
    private int id;
    private String name;
    private String accountId;
    private String token;
    private double gmtCreate;
    private double gmtModified;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public double getGmtCreate() {
        return gmtCreate;
    }

    public void setGmtCreate(double gmtCreate) {
        this.gmtCreate = gmtCreate;
    }

    public double getGmtModified() {
        return gmtModified;
    }

    public void setGmtModified(double gmtModified) {
        this.gmtModified = gmtModified;
    }
}
