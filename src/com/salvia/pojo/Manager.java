package com.salvia.pojo;

import java.sql.Timestamp;

public class Manager {
    private int id;
    private String name;
    private String password;
    private String email;
    private Timestamp createTime;
    private Timestamp updateTime;

    // 构造方法
    public Manager(int id, String name, String password, String email,Timestamp createTime, Timestamp updateTime) {
        this.id = id;
        this.name = name;
        this.password = password;
        this.email = email;
        this.createTime = createTime;
        this.updateTime = updateTime;
    }

    public Manager() {
    }

    public Manager(String name, String email) {
        this.name = name;
        this.email = email;
    }

    public Manager(String name, String email, String password) {
        this.name = name;
        this.password = password;
        this.email = email;
    }

    @Override
    public String toString() {
        return id +","+
                name +","+
                email +","+
                createTime +","+
                updateTime;
    }

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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }


    public Timestamp getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }

    public Timestamp getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Timestamp updateTime) {
        this.updateTime = updateTime;
    }
}

