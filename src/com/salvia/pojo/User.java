package com.salvia.pojo;

import java.sql.Timestamp;

public class User {
    private int id;
    private String name;
    private String password;
    private String email;
    private String tel;
    private int age;
    private String sex;
    private String address;
    private Timestamp createTime;
    private Timestamp updateTime;

    // 构造方法
    public User(int id, String name, String password, String email, String tel, int age, String sex, String address, Timestamp createTime, Timestamp updateTime) {
        this.id = id;
        this.name = name;
        this.password = password;
        this.email = email;
        this.tel = tel;
        this.age = age;
        this.sex = sex;
        this.address = address;
        this.createTime = createTime;
        this.updateTime = updateTime;
    }

    public User() {
    }



    public User(String name, String email, String password) {
        this.name = name;
        this.password = password;
        this.email = email;
    }

    public User(String name, String password, String email, String tel, int age, String sex, String address) {
        this.name = name;
        this.password = password;
        this.email = email;
        this.tel = tel;
        this.age = age;
        this.sex = sex;
        this.address = address;
    }

    @Override
    public String toString() {
        return id +","+
                name +","+
                email +","+
                tel +","+
                age +","+
                sex +","+
                address +","+
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

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
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
