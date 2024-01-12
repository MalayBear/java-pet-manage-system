package com.salvia.pojo;

public class Pet {
    private int id;
    private String name;
    private String species;
    private int age;
    private String gender;
    private String personality;
    private double weight;
    private int ownerId;

    public Pet() {
    }

    // 构造方法
    public Pet(int id, String name, String species, int age, String gender, String personality, double weight, int ownerId) {
        this.id = id;
        this.name = name;
        this.species = species;
        this.age = age;
        this.gender = gender;
        this.personality = personality;
        this.weight = weight;
        this.ownerId = ownerId;
    }

    public Pet(String name, String species, int age, String gender, String personality, double weight, int ownerId) {
        this.name = name;
        this.species = species;
        this.age = age;
        this.gender = gender;
        this.personality = personality;
        this.weight = weight;
        this.ownerId = ownerId;
    }

    @Override
    public String toString() {
        return id + "," + name + ", " + species + ", " + age + ", " + gender + ", " + personality +
                ", " + weight + ", " + ownerId;
    }

    // Getter和Setter方法
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

    public String getSpecies() {
        return species;
    }

    public void setSpecies(String species) {
        this.species = species;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getPersonality() {
        return personality;
    }

    public void setPersonality(String personality) {
        this.personality = personality;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public int getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(int ownerId) {
        this.ownerId = ownerId;
    }
}
