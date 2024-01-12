package com.salvia.pojo;

import java.sql.Timestamp;

public class PetSupply {
    private int id;
    private int user_id;
    //    petSuppliesType_id INT COMMENT '宠物用品ID'
    private int petSuppliesType_id;
    private String name;
    //    num INT COMMENT '购买数量',
    private int num;
    private int status;
    private Timestamp create_time;
    private Timestamp update_time;

    private String category;
    private double price;
    private String description;
    private String imageUrl;


    public PetSupply() {
    }
    // 数据库表 中的字段对应的 属性
    public PetSupply(int id, int num) {
        this.id = id;
        this.num = num;
    }
    // 加上商品信息
    public PetSupply(int id, int user_id, int petSuppliesType_id,String name, int num,int status, Timestamp create_time,Timestamp update_time) {
        this.id = id;
        this.user_id = user_id;
        this.petSuppliesType_id =petSuppliesType_id;
        this.name = name;
        this.num = num;
        this.status = status;
        this.create_time = create_time;
        this.update_time = update_time;

    }

    public void setSuppliesType(SuppliesType suppliesType){
        this.petSuppliesType_id = suppliesType.getId();
        this.name = suppliesType.getName();
        this.category = suppliesType.getCategory();
        this.price = suppliesType.getPrice();
        this.description = suppliesType.getDescription();
        this.imageUrl = suppliesType.getImageUrl();
    }
    // 全参构造

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public int getPetSuppliesType_id() {
        return petSuppliesType_id;
    }

    public void setPetSuppliesType_id(int petSuppliesType_id) {
        this.petSuppliesType_id = petSuppliesType_id;
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

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }


    public Timestamp getCreate_time() {
        return create_time;
    }

    public void setCreate_time(Timestamp create_time) {
        this.create_time = create_time;
    }

    public Timestamp getUpdate_time() {
        return update_time;
    }

    public void setUpdate_time(Timestamp update_time) {
        this.update_time = update_time;
    }
    public String toString() {
        return id + "," + user_id +","+ petSuppliesType_id + ","+ name + "," + num + "件,"+(status==1?"已支付":"待支付") + ","+  create_time + ","+update_time ;
    }


    public int getStatus() {
        return status;
    }


    public void setStatus(int status) {
        this.status = status;
    }
}





