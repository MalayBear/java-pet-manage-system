package com.salvia.service;

import com.salvia.data.PetsTable;
import com.salvia.pojo.Pet;
import com.salvia.pojo.User;

import java.sql.SQLException;
import java.util.List;

import static com.salvia.utils.Input.getIntInput;
import static com.salvia.utils.Print.printUtil;

public class PetsService {
    PetsTable petsTable = new PetsTable();
    Pet pet = new Pet();

    // 打印出 宠物信息 表头
    public static void printPetHeader(){
        printUtil("ID,姓名, 物种, 年龄, 性别, 个性, 体重/kg, 主人编号");
    }

    // 打印出 一个用户所属列表的 宠物信息
    public void printUserAllPets(List<Pet> petList){
        printPetHeader();
        for (Pet pet:petList) {
            printUtil(pet.toString()); // 打印出宠物信息
        }

    }
    //仅打印 新增宠物 的信息
    public void printAddPet(List<Pet> petList) throws SQLException {
        printPetHeader();
        petList.forEach(pet->{
            printUtil(pet.toString());
        });
    }
    // 查找 用户和宠物 是否为 主宠关系
    public boolean isUsersPet(User user,Pet pet) throws SQLException {
        int input = getIntInput();
        pet.setId(input);
        pet = petsTable.selectById(pet); // 根据 ID 查找对应的宠物
        return pet != null && (pet.getOwnerId() == user.getId());
    }

    //当为true 该用户有宠物 false，表示不存在
    public boolean isUserHavePet(User user) throws SQLException {
       return !petsTable.selectUsersPet(user).isEmpty();
    }

    //管理员打印 修改宠物的信息
    public void managerPrintPetById(Pet pet) throws SQLException {
        printPetHeader();
        pet = petsTable.selectById(pet);
        printUtil(pet.toString());
    }

    //用户打印搜索后
}
