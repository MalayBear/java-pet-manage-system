package com.salvia.pojo;

public class SuppliesType {
    private int id;
    private String name;
    private String category;
    private double price;
    private String description;
    private String imageUrl;

    public SuppliesType() {
    }

    public SuppliesType(int id, String name, String category, double price, String description, String imageUrl) {
        this.id = id;
        this.name = name;
        this.category = category;
        this.price = price;
        this.description = description;
        this.imageUrl = imageUrl;
    }

    public int getId() {
        return this.id;
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

    public String toString() {
        // printUtil是按照 ”,“ 来进行分割的，所以，需要重写 SuppliesType 实体的 toString
        // 只需要把 字段 用 "," 连接起来就可以了
        return  id + "," + name + "," + category + ", " + price + ", " + description + "," + imageUrl;
    }
}

